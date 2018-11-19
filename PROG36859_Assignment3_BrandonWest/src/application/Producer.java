package application;

public class Producer extends Product
{
	String _producerName;
	
	protected Producer ()
	{}
	
	protected Producer ( int newQuantity, int newProductNumber, String newDescription, String newProducerName, double newCost )
	{
		super( newQuantity, newProductNumber, newDescription, newCost );
		this.setProducerName( newProducerName );
	}
	
	protected String getPrducerName()
	{
		return this._producerName;
	}
	
	protected void setProducerName( String newProducerName )
	{
		this._producerName = newProducerName;
	}
}
