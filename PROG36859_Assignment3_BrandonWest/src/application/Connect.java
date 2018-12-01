package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect 
{
	protected Connection _conn = null;
	protected String _dbURL = null;
	protected String _user = null;
	protected String _password = null;
	
	/**
	 *  default constructor
	 */
	protected Connect()
	{}
	
	/**
	 * non default constrcutor for connect object
	 * @param newDbURL database url
	 * @param newUser user name
	 * @param newPassword user password
	 */
	protected Connect( String newDbURL, String newUser, String newPassword )
	{
		this.setDbURL( newDbURL );
		this.setUser ( newUser );
		this.setPassword( newPassword );
	}
	
	/**
	 * connect to server method
	 */
	public void connect()
	{
		try
		{
			// set the connection with provided variables
			setConnection( DriverManager.getConnection( getDbURL(), getUser(), getPassword() ) );
			// if getConnection returns with not null, connected to database
			if ( getConnection() != null )
				System.out.println( "Connected to database. " + getConnection() );
			// else not connected
			else
				System.out.println( "Not conected to database." );
		}catch ( SQLException error )
		{
			System.out.println( "SQL Error" );
			error.printStackTrace();
		}
	}//end of connect method
	
	/**
	 * disonnect from server
	 */
	protected void disconnect ()
	{
		try 
		{
			_conn.close();
		}catch ( SQLException error )
		{
			error.printStackTrace();
		}
	}

	/**
	 * returnt he connection info
	 * @return the _conn holds the connection info
	 */
	protected Connection getConnection() 
	{
		return _conn;
	}

	/**
	 * set the connection info
	 * @param _conn the connection info is set
	 */
	protected void setConnection( Connection newConnection ) 
	{
		this._conn = newConnection;
	}

	/**
	 * return the database url
	 * @return the database url
	 */
	protected String getDbURL() 
	{
		return _dbURL;
	}

	/**
	 * sets the database name
	 * @param _dbURL gets the database url set
	 */
	protected void setDbURL(String newDbURL)
	{
		this._dbURL =  newDbURL;
	}

	/**
	 * return the username
	 * @return the _user
	 */
	protected String getUser() 
	{
		return _user;
	}

	/**
	 * set the username from newUser
	 * @param _user the _user to set
	 */
	protected void setUser(String newUser) 
	{
		this._user = newUser;
	}

	/**
	 * returns the password from the user
	 * @return the _password 
	 */
	protected String getPassword() 
	{
		return _password;
	}

	/**
	 * sets the password
	 * @param _password to user input password
	 */
	protected void setPassword(String newPassword) 
	{
		this._password = newPassword;
	}
}// end of connect method