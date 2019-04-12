import GUI.*;
import Controller.*;
import javax.swing.UIManager;

/**
 * A class to run the Client/Front End.
 * @author Oscar Wong, Jeremy Olea
 * @version 1.0
 * @since April 3rd, 2019
 */
public class App {
    /**
     * Method to run the Client and GUI.
     * @param args Command Line Arguments
     * @throws Exception an exception if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}
        //Client client = new Client("localhost", 5000);
        Client client = new Client("10.13.99.23", 5000);
        Frame gui = new Frame();
        gui.setListener(new Listener(client));
    }
}