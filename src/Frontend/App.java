package Frontend;

import Frontend.GUI.*;
import Frontend.Controller.*;

public class App {
    public static void main(String[] args) throws Exception {
        Client client = new Client("localhost", 5050);
        Frame gui = new Frame();
        gui.setListener(new Listener(client));
    }
}