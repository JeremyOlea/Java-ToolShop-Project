package Server;

import java.util.ArrayList;

/**
 * Inventory of the shop that holds all the items
 * @author M. Moshirpour, Michael Jeremy Olea, Oscar Wong
 * @version 1.0
 * @since April 4th, 2019
 */
public class Inventory {
	
	/**
	 * Holds all the items in the shop
	 */
	private ArrayList <Item> itemList;
	/**
	 * An order made from the supplier
	 */
	private Order myOrder;
	
	/**
	 * Constructor for inventory
	 * @param itemList ArrayList of items
	 */
	public Inventory (ArrayList <Item> itemList) {
		this.itemList = itemList;
		myOrder = new Order ();
	}

	/**
	 * getter for itemList
	 * @return the itemList
	 */
	public ArrayList <Item> getItemList() {
		return itemList;
	}

	/**
	 * setter for itemList
	 * @param itemList the ArrayList of items to be set to
	 */
	public void setItemList(ArrayList <Item> itemList) {
		this.itemList = itemList;
	}

	/**
	 * Manages the decreasing and buying of items from supplier
	 * @param name the name of the item
	 * @return the item with the same name as the param
	 */
	synchronized public Item manageItem (String name, DbController db){
		Item theItem = decreaseItem (name);
		
		if (theItem != null){
			db.decreaseQuantity(theItem);
			placeOrder (theItem);
		}
		return theItem;
	}

	/**
	 * places an order to buy from supplier
	 * @param theItem the item to be ordered
	 */
	public void placeOrder (Item theItem){
		OrderLine ol = theItem.placeOrder();
		if (ol !=null){
			myOrder.addOrderLine(ol);
		}
	}
	/**
	 * Decreases the item quantity 
	 * @param name the name of the item
	 * @return the item
	 */
	synchronized private Item decreaseItem (String name) {
		
		Item theItem = searchForItem (name);
		
		if (theItem == null)
			return null;
		
		if (theItem.decreaseItemQuantity() == true){
			
			return theItem;
		}
		return null;
		
	}
	
	/**
	 * Gets the item quantity
	 * @param name name of item
	 * @return the quantity of the item
	 */
	public int getItemQuantity (String name){
		Item theItem = searchForItem (name);
		if (theItem == null)
			return -1;
		else
			return theItem.getItemQuantity();
	}
	public Item searchForItem (String name) {
		for (Item i: itemList) {
			if (i.getItemName().toUpperCase().equals(name))
				return i;
		}
		return null;
	}
	
	/**
	 * Converts the itemList to a string
	 * @return the string of all the items in the inventory
	 */
	public String toString () {
		String str = "";
		for (Item i: itemList) {
			str += i;
		}
		return str;
	}

	/**
	 * searches for an item
	 * @param id id of item to be searched for
	 */
	public Item searchForItem(int id) {
		for (Item i: itemList) {
			if (i.getItemId() == id)
				return i;
		}
		return null;
	}

	/**
	 * prints the order
	 * @return A string of the contents inside the order
	 */
	public String printOrder() {
		return myOrder.toString();
	}

}
