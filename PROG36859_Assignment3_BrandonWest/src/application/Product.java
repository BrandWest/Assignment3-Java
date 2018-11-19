package application;

public class Product
{
	int _quantity = 0, _productNumber = 0;
	String _description;
	double _cost = 0;
	
	//default constructor
	protected Product ()
	{}
	
	/**
	 * Non-Default Constructor to create products to be used from the customer and Prodcuer
	 * @param newQuantity the number of items left
	 * @param newProductNumber the product number
	 * @param newDescription the prodcut description
	 * @param newCost the cost of the product
	 */
	protected Product ( int newQuantity, int newProductNumber, String newDescription, double newCost )
	{
		this._quantity = newQuantity;
		this._productNumber = newProductNumber;
		this._description = newDescription;
		this._cost = newCost;
	}

	/**
	 * @return the _quantity
	 */
	protected int getQuantity() 
	{
		return _quantity;
	}

	/**
	 * @param _quantity the _quantity to set
	 */
	protected void setQuantity( int newQuantity) 
	{
		this._quantity = newQuantity;
	}

	/**
	 * @return the _productNumber
	 */
	protected int getProductNumber() 
	{
		return _productNumber;
	}

	/**
	 * @param _productNumber the _productNumber to set
	 */
	protected void setProductNumber( int newProductNumber) 
	{
		this._productNumber = newProductNumber;
	}

	/**
	 * @return the _description
	 */
	protected String getDescription() 
	{
		return _description;
	}

	/**
	 * @param _description the _description to set
	 */
	protected void setDescription(String newDescription) 
	{
		this._description = newDescription;
	}

	/**
	 * @return the _cost
	 */
	protected double getCost() 
	{
		return _cost;
	}

	/**
	 * @param _cost the _cost to set
	 */
	protected void setCost( double newCost )
	{
		this._cost = newCost;
	}
}
