package Server;
/**
 * An item inside the shop
 * @author M. Moshirpour, Michael Jeremy Olea, Oscar Wong
 * @version 1.0
 * @since April 4th, 2019
 */
public class Item {
	
	/**
	 * id of the item
	 */
	private int itemId;
	/**
	 * name of the item
	 */
	private String itemName;
	/**
	 * quantity of the item
	 */
	private int itemQuantity;
	/**
	 * price of the item
	 */
	private double itemPrice;
	/**
	 * boolean stating if the item has been ordered
	 */
	private boolean alreadyOrdered;
	/**
	 * supplier for the item
	 */
	private Supplier theSupplier;
	/**
	 * Quantity to be ordered
	 */
	private static final int ORDERQUANTITY = 40;
	/**
	 * Minimum quantity of items allowed
	 */
	private static final int MINIMUMUMBER = 40; 	
	
	/**
	 * Constructor for item
	 * @param id id of item
	 * @param name name of item
	 * @param quantity quantity of item
	 * @param price price of item
	 * @param sup supplier of item
	 */
	public Item (int id, String name, int quantity, double price, Supplier sup) {
		
		itemId = id;
		itemName = name;
		itemQuantity = quantity;
		itemPrice = price;
		theSupplier = sup;
		setAlreadyOrdered(false);
	}
	
	/**
	 * Decreases the item quantity
	 * @return it the item was returned or not
	 */
	synchronized public boolean decreaseItemQuantity () {
		if (itemQuantity > 0) {
			itemQuantity--;
		    return true;	
		}
		else
			return false;
			
	}

	/**
	 * places and order for the item
	 * @return the orderline of the order placed
	 */
	public OrderLine placeOrder (){
		OrderLine ol;
		if (getItemQuantity() < MINIMUMUMBER && alreadyOrdered == false){
			ol = new OrderLine (this, ORDERQUANTITY);
			alreadyOrdered = true;
			return ol;
		}
	    return null;
	}

	/**
	 * getter for item id
	 * @return id of the item
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * setter for item id
	 * @param itemId id of the item
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * getter for item name
	 * @return name of the item
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * setter for item name
	 * @param itemName  name of the item
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * getter for item quantity
	 * @return quantity of the item
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}

	/**
	 * setter for item quantity
	 * @param itemQuantity Quantity of the item
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	/**
	 * getter for item price
	 * @return price of the item
	 */
	public double getItemPrice() {
		return itemPrice;
	}

	/**
	 * setter for item price
	 * @param itemPrice price of the item
	 */
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * setter for item supplier
	 * @param sup supplier of the item
	 */
	public void setTheSupplier (Supplier sup) {
		theSupplier = sup;
	}

	/**
	 * getter for item supplier
	 * @return supplier of the item
	 */
	public Supplier getTheSupplier () {
		return theSupplier;
	}
	
	/**
	 * turns data of the item in the form of a string
	 * @return the item in the form of a string
	 */
	public String toString () {
		return "Item ID: " + itemId + "\tItem Name: " + itemName + "\tItem Quantity: " + 
	           itemQuantity + "\n";
	}

	/**
	 * Returns the data of the item in the form of a string without using \t
	 * @return The item as a String.
	 */
	public String toStringNoTabs() {
		return "Item ID: " + itemId + " Item Name: " + itemName + " Item Quantity: " + 
	           itemQuantity;
	}

	/**
	 * creates a string with the headers of the data in the item
	 * @return the headers in the form of a string
	 */
	public String toStringWithNoLabels() {
		return itemId + "\t" + itemName + "\t\t" + itemQuantity + "\n";
	}

	/**
	 * getter for alreadyOrdered
	 * @return alreadyOrdered attribute
	 */
	public boolean isAlreadyOrdered() {
		return alreadyOrdered;
	}

	/**
	 * setter for alreadyOrdered
	 * @param alreadyOrdered the new value
	 */
	public void setAlreadyOrdered(boolean alreadyOrdered) {
		this.alreadyOrdered = alreadyOrdered;
	}

}