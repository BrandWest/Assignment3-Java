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
	
	// default constructor
	protected Connect()
	{}
	
	/**
	 * non default constrcutor for connect object
	 * @param newDbURL
	 * @param newUser
	 * @param newPassword
	 */
	protected Connect( String newDbURL, String newUser, String newPassword )
	{
		this.setDbURL( newDbURL );
		this.setUser ( newUser );
		this.setPassword( newPassword );
	}
	
	public void connect()
	{
		try
		{
			System.out.println( "conn = " + getConn() );
			setConn( DriverManager.getConnection( getDbURL(), getUser(), getPassword() ) );
			if ( getConn() != null )
				System.out.println( "Connected to database." );
			else
				System.out.println( "Not conected to database." );
		}catch ( SQLException error )
		{
			System.out.println( "SQL Error" );
			error.printStackTrace();
		}
	}
	
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
	 * @return the _conn
	 */
	protected Connection getConn() 
	{
		return _conn;
	}

	/**
	 * @param _conn the _conn to set
	 */
	protected void setConn( Connection newConnection ) 
	{
		this._conn = newConnection;
	}

	/**
	 * @return the _dbURL
	 */
	protected String getDbURL() 
	{
		return _dbURL;
	}

	/**
	 * @param _dbURL the _dbURL to set
	 */
	protected void setDbURL(String newDbURL)
	{
		this._dbURL =  newDbURL;
	}

	/**
	 * @return the _user
	 */
	protected String getUser() 
	{
		return _user;
	}

	/**
	 * @param _user the _user to set
	 */
	protected void setUser(String newUser) 
	{
		this._user = newUser;
	}

	/**
	 * @return the _password
	 */
	protected String getPassword() 
	{
		return _password;
	}

	/**
	 * @param _password the _password to set
	 */
	protected void setPassword(String newPassword) 
	{
		this._password = newPassword;
	}
}
