package application;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main
{
	public static void main ( String[] args ) throws InterruptedException, SQLException
	{	
		//variables
		String databaseURL = null, userName = null, password = null, message;
		int choice = -1;
		//objects
		Scanner input = new Scanner (System.in);
		Connect connect = null;
		DataBaseManagement manipulation = new DataBaseManagement();
		
		//While loop to get input from user for credentials.
		while ( choice == -1 && connect == null || choice == 0 )
		{
			try
			{
				System.out.print( "Would you default credentails (1), input your own (2), or 0 to exit: " );
				//get the choice from the user
				choice = input.nextInt();				
			}catch ( InputMismatchException error )
			{
				System.out.println( "You've entered an invalid key, use only numbers." );
				input.nextLine();
			}
			/**
			 * If choice is 1, default hardcoded values are implemented as per prof request
			 */
			if ( choice == 1 )
			{				
				databaseURL = "jdbc:mysql://localhost/JavaII";
				userName = "root";
				password = "1111";
				// create connect object and calls connect()
				connect = new Connect ( databaseURL, userName, password );
				connect.connect();
			}
			/**
			 * If choice 2, user inputs their own values
			 */
			else if ( choice == 2 )
			{
				try
				{
					System.out.print( "Please enter the database URL: " );
					// user entered url
					databaseURL = input.next();
					System.out.print( "Please enter the user name: " );
					// user entered userName
					userName = input.next();
					System.out.print( "Please enter the password: " );
					// user entered password
					password = input.next();
				}catch ( InputMismatchException error )
				{
					System.out.println( "You've entered an invalid key, use only Strings." );					
				}
			}
			/**
			 * If user wants to exit the while loop
			 */
			else if ( choice == 0 )
			{
				System.out.println( "Exiting... " );
				break;
			}
			else 
				System.out.println( "Invalid input, please try again.");
		}// end of while for connecting
	
		// drop tables for producer and consumer, create tables for producer and consumer
		manipulation.dropTable( connect );
		manipulation.createTable( connect );
		
		// executor service that handles the threads
		ExecutorService executorService = Executors.newCachedThreadPool();
		//synchronized buffer to allow threads to work properly.
		SynchronizedBuffer sharedLocation = new SynchronizedBuffer();
		//object names
		Producer producer;
		Consumer consumer;
		
		//executorService to make and use the threads
		executorService.execute( consumer = new Consumer ( sharedLocation, connect ) );
		executorService.execute( producer = new Producer ( sharedLocation, connect ) );
		//executorService shutdown
		executorService.shutdown();
		//await time to terminate thread
		executorService.awaitTermination( 1,  TimeUnit.MINUTES );
		
		manipulation.getAllMessages( connect );
		// close input
		input.close();
		//close the manipulation 
		manipulation.disconnect();
		// disconnect from the server
		connect.disconnect();
	}// end of main
}// end of main class