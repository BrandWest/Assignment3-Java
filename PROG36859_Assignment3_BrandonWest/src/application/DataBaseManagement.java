package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseManagement 
{
	// prepared statement object
	PreparedStatement _preparedStatement;
	/**
	 * insert message method, inserts messages from the producer into the database
	 * @param connect connection info
	 * @param producer producer object
	 * @param message message information
	 * @throws SQLException 
	 */
	protected void insertMessage ( Connect connect, Producer producer ) throws SQLException
	{
		// prepared insert string
		String preparedInsert = "Insert into messages (name, message) values (?,?);";
		//set the prepared statement, with connection and the prepared insert
		setPreparedStatement( connect.getConnection().prepareStatement( preparedInsert ) );
		// get the prepared statement and set the first ? to name
		getPreparedStatement().setString( 1, producer.getName() );
		//get the prepared statement and set the second ? to message
		getPreparedStatement().setString( 2, producer.getMessage() );
		// execute the update to the database
		getPreparedStatement().executeUpdate();
	}// end of insertmessage consumer
	
	/**
	 * insert messages from the consumer into the database
	 * @param connect connection information into the server
	 * @param consumer object
	 * @throws SQLException
	 */
	protected void insertMessage ( Connect connect, Consumer consumer ) throws SQLException
	{
		// prepared statement that takes 2 values
		String preparedInsert = "Insert into messages (name, message) values (?,?);";
		// set prepared satement with connection info and the prepared insert argument
		setPreparedStatement( connect.getConnection().prepareStatement( preparedInsert ) );
		//get the preparedstatement and sets the first variable to name
		getPreparedStatement().setString( 1, consumer.getName() );
		// gets the preparedstatement and sets the second variable to the message
		getPreparedStatement().setString( 2, consumer.getMessage() );
		// execute the update to the database
		getPreparedStatement().executeUpdate();
	}// end of inesrt message consumer
	
	/**
	 * get the message from the server and return a result set
	 * @param connect connection informatino to the server
	 * @param producer object
	 * @return return the result set 
	 * @throws SQLException
	 */
	protected ResultSet getMessage ( Connect connect, Producer producer ) throws SQLException
	{
		// result set for returning
		ResultSet result;
		// prepared statement, limit the return to 1
		String preparedQuery = "select * from messages where name = 'consumer' limit 1;";
		
		// set prepared statement with connection info and the preparedquery
		setPreparedStatement ( connect.getConnection().prepareCall( preparedQuery ) );
		// save the query resultset into result
		result = getPreparedStatement().executeQuery();
		
		// return result
		return result;
	}// end of get message producer
	
	/**
	 * get the message from the server returning a resultset in the variable result
	 * @param connect connection information to the database
	 * @param consumer object
	 * @return resultset 
	 * @throws SQLException
	 */
	protected ResultSet getMessage( Connect connect, Consumer consumer ) throws SQLException
	{
		// prepared query limited to 1
		String preparedQuery = "select message from messages where name = 'producer' LIMIT 1;";
		// resultset
		ResultSet result;
		
		// set the prepared statement with connection information and the query
		setPreparedStatement ( connect.getConnection().prepareCall( preparedQuery ) );
		// save the resultset into result
		result = getPreparedStatement().executeQuery();

		//return the resultset result
		return result;
	}// end of the consumer get message method
	
	/**
	 * delete a message from the server
	 * @param connect connection information
	 * @param producer object
	 * @throws SQLException
	 */
	protected void deleteMessage ( Connect connect, Producer producer ) throws SQLException
	{
		// prepared statement with limit of 1 deletion at a time
		String preparedQuery = "delete from messages where name = 'consumer' Limit 1 ";
		// set the prepared statement with connection infromatino and the query
		setPreparedStatement ( connect.getConnection().prepareCall( preparedQuery ) );
		// execute update to the database
		getPreparedStatement().executeUpdate();
	}// end of producer deletemessage 
	
	/**
	 * delete message for consumer
	 * @param connect connection info for database
	 * @param consumer object
	 * @throws SQLException
	 */
	protected void deleteMessage ( Connect connect, Consumer consumer ) throws SQLException
	{
		// prepared statement with limit of 1 deletion at a time
		String preparedQuery = "delete from messages where name = 'producer' Limit 1 ";
		// set the prepared statement with connection infromatino and the query
		setPreparedStatement ( connect.getConnection().prepareCall( preparedQuery ) );
		// execute update to the database
		getPreparedStatement().executeUpdate();
	}// end of the deletemessage for consumer
	/**
	 * set the prepared statement 
	 * @param newPreparedStatement
	 */
	protected void setPreparedStatement ( PreparedStatement newPreparedStatement )
	{
		this._preparedStatement = newPreparedStatement;
	}
	
	/**
	 * get the prepared statment
	 * @return
	 */
	protected PreparedStatement getPreparedStatement ()
	{
		return this._preparedStatement;
	}
	
	/**
	 * drops the table already on the server
	 * @param connect connection info
	 */
	protected void dropTable ( Connect connect )
	{
		// drop table command for server
		String dropTable = "drop table if exists messages;";

		try 
		{
			// set the statment with connection and droptable arguements
			setPreparedStatement ( connect.getConnection().prepareStatement( dropTable ) );
			// exuecute the update to the server
			getPreparedStatement().executeUpdate();
		}catch ( SQLException error ) 
		{
			error.printStackTrace();
		}
	}// end of drop table method
	
	/**
	 * creates the table
	 * @param connect connection information
	 */
	protected void createTable( Connect connect ) 
	{
		// create table command names messages, with name and messages
		String createTable = "create Table messages(name VARCHAR(30) NOT NULL, message VARCHAR(30) NOT NULL );";
		try 
		{
			// sets the prepared statement with connection and createTable arguments
			setPreparedStatement ( connect.getConnection().prepareStatement( createTable ) );
			// execute the prepared statement
			getPreparedStatement().executeUpdate();
		} catch ( SQLException error ) 
		{
			error.printStackTrace();
		}
	}// end of the create table
	
	/**
	 * Disconnect method to disconnect from all open ports
	 */
	protected void disconnect()
	{
		try
		{
			_preparedStatement.close();
		}catch ( SQLException error )
		{
			error.printStackTrace();
		}
	}// end of the disconnect method
	
	/**
	 * get all messages from the database
	 * @param connect the connection to the database
	 */
	protected void getAllMessages( Connect connect )
	{
		// resultset to hold for the database
		ResultSet result;
		try
		{
			// connection info to the database
			connect.getConnection().createStatement();
			// prepared statement to save into the resultset
			result = getPreparedStatement().executeQuery( "Select * from messages" );
			
			System.out.println( "+---------------------------------------+------------------------+" );
			System.out.println( "|\t\tName\t\t        |\tmessages\t |");
			System.out.println( "+---------------------------------------+------------------------+" );
			
			// while result has next
			while ( result.next() ) 
				System.out.printf( "|%-30s\t\t|%-18s\t |\n", result.getString( "name"), result.getString( "message") );
			System.out.println( "+---------------------------------------+------------------------+" );
		}catch (SQLException error )
		{
			error.printStackTrace();
		}
	}//end the getall messages method
}//end of the DataBaseManagement class
