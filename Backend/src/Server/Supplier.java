package Server;

import java.util.ArrayList;

/**
 * A class that contains info about an item's supplier.
 * @author M. Moshirpour, Oscar Wong, Jeremy Olea.
 * @version 1.0
 * @since April 3rd, 2019
 */
public class Supplier {
	/**
	 * ID for this Supplier.
	 */
	private int supId;
	/**
	 * Name of this Supplier.
	 */
	private String supName;
	/**
	 * Address of this Supplier.
	 */
	private String supAddress;
	/**
	 * Contact name for this Supplier.
	 */
	private String supContactName;
	/**
	 * List of Items this Supplier supplies.
	 */
	private ArrayList <Item> itemList;
	
	/**
	 * Constructs a new Supplier.
	 * @param id ID for this Supplier.
	 * @param name Name of this Supplier.
	 * @param address Address of this Supplier.
	 * @param contactName Contact name for this Supplier.
	 */
	public Supplier (int id, String name, String address, String contactName) {
		
		supId = id;
		supName = name;
		supAddress = address;
		supContactName = contactName;
		itemList = new ArrayList <Item>();
	}

	/**
	 * Get supplier ID.
	 * @return This Supplier's ID.
	 */
	public int getSupId() {
		return supId;
	}

	/**
	 * Set the Supplier's ID.
	 * @param supId This Supplier's ID.
	 */
	public void setSupId(int supId) {
		this.supId = supId;
	}

	/**
	 * Get the Supplier's name.
	 * @return This Supplier's name.
	 */
	public String getSupName() {
		return supName;
	}

	/**
	 * Set the Supplier's name.
	 * @param supName This Supplier's name.
	 */
	public void setSupName(String supName) {
		this.supName = supName;
	}

	/**
	 * Get the Supplier's address.
	 * @return This Supplier's address.
	 */
	public String getSupAddress() {
		return supAddress;
	}

	/**
	 * Set the Supplier's address.
	 * @param supAddress This Supplier's address.
	 */
	public void setSupAddress(String supAddress) {
		this.supAddress = supAddress;
	}

	/**
	 * Get the Supplier contact's name.
	 * @return This Supplier's contact's name.
	 */
	public String getSupContactName() {
		return supContactName;
	}

	/**
	 * Set the Supplier contact's name.
	 * @param supContactName This Supplier's contact's name.
	 */
	public void setSupContactName(String supContactName) {
		this.supContactName = supContactName;
	}

	/**
	 * Returns this Supplier as a String.
	 * @return This Supplier as a String.
	 */
	public String toString () {
		return supName + " Supplier ID: " + supId+ "\n";
		
	}

	/**
	 * Get the Supplier's list of items.
	 * @return This Supplier's list of items.
	 */
	public ArrayList <Item> getItemList() {
		return itemList;
	}

	/**
	 * Set the Supplier's list of items.
	 * @param itemList This Supplier's list of items.
	 */
	public void setItemList(ArrayList <Item> itemList) {
		this.itemList = itemList;
	}	
}
