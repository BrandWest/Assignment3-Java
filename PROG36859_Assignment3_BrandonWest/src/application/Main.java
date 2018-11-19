package application;

import java.util.Scanner;

public class Main
{
	public static void main ( String[] args )
	{
		/**
		 * Must take in the option of choosing a database.
		 * 
		 */
		
		//variables
		String databaseURL = null, userName = null, password = null;
		int choice = -1;
		
		//objects
		Scanner input = new Scanner (System.in);
		Connect connect = null;
		
		
		//While loop to get input from user for credentials.
		while ( choice == -1 && connect == null || choice == 0 )
		{
			System.out.print( "Would you default credentails (1) or input your own (2)? " );
			choice = input.nextInt();
			if ( choice == 1 )
			{				
				databaseURL = "jdbc:mysql://localhost/JavaII";
				userName = "root";
				password = "1111";
				connect = new Connect ( databaseURL, userName, password );
				connect.connect();
				connect.getConn().toString();

			}
			else if ( choice == 2 )
			{
				System.out.print( "Please enter the database URL: " );
				databaseURL = input.next();
				System.out.print( "Please enter the user name: " );
				userName = input.next();
				System.out.print( "Please enter the password: " );
				password = input.next();
			}
			else if ( choice == 0 )
			{
				System.out.println( "Exiting... " );
				break;
			}
			else 
				System.out.println( "Invalid input, please try again.");
		}
		
		
		
		input.close();
		connect.disconnect();
		
		// get information from the text file and put into an array
		
		
		
		
		
		
	}
}