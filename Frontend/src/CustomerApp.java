import GUI.*;
import Controller.*;
import javax.swing.UIManager;

/**
 * A class to run the Client/Front End for customer.
 * @author Oscar Wong, Jeremy Olea
 * @version 1.0
 * @since April 3rd, 2019
 */
public class CustomerApp {
    /**
     * Method to run the Client and GUI for customer.
     * @param args Command Line Arguments
     * @throws Exception an exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
        //Client client = new Client("localhost", 5000);
        Client client = new Client("10.13.99.23", 5000);
        CustomerFrame gui = new CustomerFrame();
        gui.setListener(new Listener(client));
    }
}