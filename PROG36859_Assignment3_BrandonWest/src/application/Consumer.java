package application;

public class Consumer extends Product
{
	String _customerName, _deliveryAddress;
	int _orderNumber = 0;
	double _totalPrice = 0;
	
	
	protected Consumer ()
	{}
	
	protected Consumer ( int newQuantity, int newProductNumber, String newDescription, 
						 double newCost, String newCustomerName, String newDeliveryAddress,
					   	 int newOrderNumber )
	{
		super( newQuantity, newProductNumber, newDescription, newCost );
		this._customerName = newCustomerName;
		this._deliveryAddress = newDeliveryAddress;
		this._orderNumber = newOrderNumber;
	}

	/**
	 * @return the _customerName
	 */
	protected String getCustomerName() 
	{
		return _customerName;
	}

	/**
	 * @param _customerName the _customerName to set
	 */
	protected void setCustomerName(String newCustomerName)
	{
		this._customerName = newCustomerName;
	}

	/**
	 * @return the _deliveryAddress
	 */
	protected String getDeliveryAddress() 
	{
		return _deliveryAddress;
	}

	/**
	 * @param _deliveryAddress the _deliveryAddress to set
	 */
	protected void setDeliveryAddress( String newDeliveryAddress) 
	{
		this._deliveryAddress = newDeliveryAddress;
	}

	/**
	 * @return the _orderNumber
	 */
	protected int getOrderNumber() 
	{
		return _orderNumber;
	}

	/**
	 * @param _orderNumber the _orderNumber to set
	 */
	protected void setOrderNumber( int newOrderNumber) 
	{
		this._orderNumber = newOrderNumber;
	}

	/**
	 * @return the _totalPrice
	 */
	protected double getTotalPrice() 
	{
		return _totalPrice;
	}

	/**
	 * @param _totalPrice the _totalPrice to set
	 */
	protected void setTotalPrice( double newTotalPrice) 
	{
		this._totalPrice = newTotalPrice;
	}
}
