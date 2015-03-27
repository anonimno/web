package model.test;

public class DBClientApp {

    public static void main(String[] args) {
        Controller controller = new Controller();
        IOInterface ioInterface = new IOInterface();
        controller.setIoInterface(ioInterface);
        ioInterface.setDelegate(controller);
        controller.start();
        System.exit(0);
    }
}
