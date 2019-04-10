package Server;

/**
 * A single order for an item
 * @author M. Moshirpour, Michael Jeremy Olea, Oscar Wong
 * @version 1.0
 * @since April 4th, 2019
 */
public class OrderLine {
	/**
	 * Item being ordered
	 */
	private Item theItem;
	/**
	 * Quantity of item being ordered
	 */
	private int orderQuantity;
	
	/**
	 * Constructor for OrderLine
	 * @param item the item being ordered
	 * @param quantity the quanitity of the item being ordered
	 */
	public OrderLine (Item item, int quantity) {
		theItem = item;
		setOrderQuantity(quantity); 
		
	}

	/**
	 * getter for theItem
	 * @return theItem
	 */
	public Item getTheItem() {
		return theItem;
	}

	/**
	 * setter for theItem
	 * @param theItem the new value for theItem
	 */
	public void setTheItem(Item theItem) {
		this.theItem = theItem;
	}

	/**
	 * getter for order quantity
	 * @return the orderQuantity
	 */
	public int getOrderQuantity() {
		return orderQuantity;
	}

	/**
	 * setter for order quantity
	 * @param orderQuantity the new value for orderQuantity
	 */
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	/**
	 * Creates a string that holds the data of order
	 * @return string of the order
	 */
	public String toString (){
		return  "Item Name: " + theItem.getItemName() +
				", Item ID: " + theItem.getItemId()+ "\n" + 
				"Order Quantity: " + orderQuantity + "\n";
	}

}
