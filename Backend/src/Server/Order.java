package Server;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A Class to manage Orders by the Shop.
 * @author M. Moshirpour, Oscar Wong, Jeremy Olea
 * @version 2.0
 * @since April 3rd, 2019
 */
public class Order {
	/**
	 * Date of Order.
	 */
	private Date today;
	/**
	 * Unique ID of Order.
	 */
	private int orderId;
	/**
	 * List of Orderlines held by this Order.
	 */
	private ArrayList <OrderLine> orderLines;
	
	/**
	 * Constructs a new Order.
	 */
	public Order () {
		today = Calendar.getInstance().getTime();
		orderLines = new ArrayList <OrderLine> ();
	}
	
	/**
	 * Add an OrderLine to this Order.
	 * @param ol OrderLine to add.
	 */
	public void addOrderLine (OrderLine ol) {
		orderLines.add(ol);
	}
	
	/**
	 * Return the OrderLines of this order.
	 * @return ArrayList of OrderLines.
	 */
	public ArrayList <OrderLine> getOrderLines (){
		return orderLines;
	}
	
	/**
	 * Set the OrderLines for this Order.
	 * @param lines ArrayList of OrderLines to set.
	 */
	public void setOrderLines (ArrayList <OrderLine> lines){
		orderLines = lines;
	}

	/**
	 * Get this Order's ID.
	 * @return this Order's ID.
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Set this Order's ID.
	 * @param orderId This Order's incoming ID.
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * Return this Order as a String.
	 * @return This Order as a String.
	 */
	public String toString (){
		String order = "Order Date: " + today.toString() + "\n\n";
		String str = "";
		for (OrderLine ol: orderLines) {
			str += ol;
			str += "------------------------\n";
		}
		if (str == "")
			str = "here are corrently no orderlines";
		
		order += str;
		order += "\n";
		return order;
	}

}
