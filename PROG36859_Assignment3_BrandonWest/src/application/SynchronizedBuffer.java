package application;

public class SynchronizedBuffer implements Buffer
{
	// variables
	private int buffer = -1; 
	private boolean occupied = false;   

	   /**
	* Method to block  putting into the database
	*/
    public synchronized void blockingPut( int value ) throws InterruptedException
    {
	   // while occupised wait.
	   while ( occupied )
		   wait();
	   
	   // value is equal to buffer
	   buffer = value;
	   // change occupied to true to continue for producer
	   occupied = true;
	   // notifies the sleeping threads
		   notifyAll();
    }// end of the synchronized blockingPut method
	
	/**
	* blockingGet method stops the threads from the getting from the server
	*/
    public synchronized int blockingGet() throws InterruptedException
    {
	   // while occupied does not equal true
	   while (!occupied)
		   wait();
	   // changes occupied to false
	   occupied = false;	   
	   // notifies all threads
	   notifyAll();
	   // returns buffer
	      return buffer;
    }// end of synchronized blockingGet
} // end class UnsynchronizedBuffer