package application;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseManagement 
{
	PreparedStatement _preparedStatement;
	
	protected void insertProduct ( Connect connect, Product product ) throws SQLException
	{
		String preparedInsert = "Insert into product (quantity, productNumber, description, cost )" +
								" select * from( select ?,?,?,?) AS temp Where not exists" +
								" (select productNumber from product Where productNumber" +
								" = ?) LIMIT 1";
		setPreparedStatement( connect.getConn().prepareStatement( preparedInsert ) );
		getPreparedStatement().setInt( 1, product.getProductNumber() );
		getPreparedStatement().setString( 2, product.getDescription() );
		getPreparedStatement().setInt( 3, product.getQuantity() );
	}
	
	protected void setPreparedStatement ( PreparedStatement newPreparedStatement )
	{
		this._preparedStatement = newPreparedStatement;
	}
	protected PreparedStatement getPreparedStatement ()
	{
		return this._preparedStatement;
	}
}
