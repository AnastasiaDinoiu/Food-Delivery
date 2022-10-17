import view.ClientApp;

public class Main {
    public static void main(String[] args) throws Exception {

        ClientApp clientApp = ClientApp.getInstance();
        clientApp.start();
    }
}
