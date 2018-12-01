package application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Consumer implements Runnable
{
	// string variables
	String _message, _consumerName;
	// objects
	DataBaseManagement DBM = new DataBaseManagement();
	private SynchronizedBuffer _sharedLocation;
	Connect _connect;
	
	// default constructor
	protected Consumer ()
	{}
	
	/**
	 * non default constructor 
	 * @param newSharedLocation shared location for tracking 
	 * @param newConnect connection info
	 */
	protected Consumer ( SynchronizedBuffer newSharedLocation, Connect newConnect  )
	{
		this._sharedLocation = newSharedLocation;
		this._connect = newConnect;
	}
	
	/**
	 * get the name of the object
	 * @return consumerName 
	 */
	protected String getName ()
	{
		return this._consumerName;
	}
	
	/**
	 * set the name of the object
	 * @param newName 
	 */
	protected void setName ( String newName )
	{
		this._consumerName = newName;
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
	public void setMessage(String newMessage) 
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
	 * Run method to control the consumer thread weather its creating messages
	 * or its receiving messages
	 */
	public void run()                             
    {
		// variable names
		String resultString;
		// result set object to hold the result from querying the server
		ResultSet result;
		
		// retreiving from database and inserting
		System.out.println( "Consumer receiving from database...");
		for ( int index = 0; index < 10; index++ )
		{
			try
			{
				// sleeps for 1000
				Thread.sleep( 1000 );
				//gets the message from the server
				result = DBM.getMessage( getConnection(), this);
				/**
				 * while loop to contorl what is inserted, removed, 
				 * and reinserted into the database.  
				 */
				while ( result.next() )	
				{
					// save the result set as a string into resultString
					resultString = result.getString( "message" );
					// delete the message from the database
					System.out.println( "Consumer received message from Producer: " + resultString );
					DBM.deleteMessage( getConnection(), this );
					// sets the new name for the consumer
					this.setName( "consumer insert from producer" );
					// sets the new message as the one removed from the database
					this.setMessage( resultString );
					// inserts the message into the database
					DBM.insertMessage( getConnection(), this );
				}
				// used to show where the thread currently is running
				_sharedLocation.blockingPut( index );
			}catch ( InterruptedException | SQLException exception )
			{
				Thread.currentThread().interrupt();
			}
		}
		
		// insertion method
		System.out.println( "\nInserting into database from the Consumer..." );
		for (int index = 0; index < 10; index++ )
		{
			try
			{	
				// sleep for 1000
				Thread.sleep( 1000 );
				// adds consumer name
				this.setName( "consumer" );
				// adds the message to message varaible
				this.setMessage ( "The message # is: " + index );
				// through DBM insert the message into the database
				DBM.insertMessage( getConnection(), this );
				_sharedLocation.blockingGet(); // set value in buffer
		    }catch (InterruptedException | SQLException exception) 
		    {
		    	 System.out.println( "Consumer : " + exception );

		    	Thread.currentThread().interrupt(); 
		    } 
		}
	   	System.out.printf("Consumer done producing%nTerminating Consumer%n");
    }
}
