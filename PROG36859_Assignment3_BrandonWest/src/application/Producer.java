package application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Producer implements Runnable
{
	// variables
	String _message, _producerName;
	//Objects
	DataBaseManagement DBM = new DataBaseManagement();
	private SynchronizedBuffer _sharedLocation;
	Connect _connect;
	
	// default constructor
	protected Producer ()
	{}
	
	/**
	 * non default constructor 
	 * @param newSharedLocation shared location for tracking 
	 * @param newConnect connection info
	 */
	protected Producer ( SynchronizedBuffer newSharedLocation, Connect newConnect )
	{
		this._sharedLocation = newSharedLocation;
		this._connect = newConnect;
	}

	/**
	 * set the name of the object
	 * @param newName 
	 */
	protected void setName ( String newName )
	{
		this._producerName = newName;
	}
	
	/**
	 * get the name of the object
	 * @return producerName 
	 */
	protected String getName ()
	{
		return this._producerName;
	}
	
	/**
	 * set the connection to the database
	 * @param newConnect connect database information 
	 */
	protected void setConnection ( Connect newConnect )
	{
		this._connect = newConnect;
	}
	
	/**
	 * get the connection to the database
	 * @return connect variable that holds the connect info
	 */
	protected Connect getConnection ()
	{
		return this._connect;
	}
	
	/**
	 * get the producer names
	 * @return returns the name of the object
	 */
	protected String getPrducerName()
	{
		return this._producerName;
	}

	/**
	 * Gets the complete message with name and message together
	 * @return the _message the message to be used to update the database
	 */
	public String getMessage()
	{
		return _message;
	}
	/**
	 * sets the message for the database to use
	 * @param _message the _message to set
	 */
	public void setMessage( String newMessage ) 
	{
		this._message = newMessage;
	}

	/**
	 * shared synchronized buffer
	 * @return shared location is returned for the object
	 */
	public SynchronizedBuffer getSharedLocation()
	{
		return _sharedLocation;
	}

	/**
	 * Run method to control the producer thread weather its creating messages
	 * or its receiving messages
	 */
	public void run()                             
    {
		// variable names
		String resultString;
		// result set object to hold the result from querying the server
		ResultSet result;
		
		// Insert for loop for producer to database		
		System.out.println( "Inserting from Producer..." );
		for (int index = 0; index < 10; index++)
		{
			try
			{
				// sleep for 1000
				Thread.sleep( 1000 );
				// sets the name to producer
				this.setName( "producer" );
				// sets the message with the index to track
				this.setMessage ( "The message # is: " + index );
				// insert message into the database
				DBM.insertMessage( getConnection(), this );
				// tracking for the thread, blocking get.
				_sharedLocation.blockingGet();
			} 
		    catch ( InterruptedException | SQLException error  ) 
		    {
		    	 System.out.println( error );
		    	 Thread.currentThread().interrupt(); 
		    } 
		}// insertion for loop end
		
		// remove from the database and reinsert with new name
		System.out.println( "Retrieving from Consumer...\n" );
		for ( int index = 0; index < 10; index++ )
		{
			try
			{
				// thread sleep for 1000
				Thread.sleep( 1000 );
				// set result to the result set received from the database
				result = DBM.getMessage( getConnection(), this);
				// while result has next
				while ( result.next() )
				{
					// set result to resultString for use
					resultString = result.getString( "message" );
					// delet the message out of the database
					DBM.deleteMessage( getConnection(),  this );
					System.out.println( "Producer received message from the consumer: " + resultString );
					// set the name 
					this.setName( "producer insert from consumer" );
					// set the message
					this.setMessage( resultString );
					// insert message into the database
					DBM.insertMessage( getConnection(), this );
				}
				// share the index number for the blockingPut thread
				_sharedLocation.blockingPut( index );
			}catch ( InterruptedException | SQLException exception )
			{
				Thread.currentThread().interrupt();
			}
		}
	   	System.out.println("\nProducer done producing\nTerminating Producer\n");
    }// end of run method
}// end of producer class
