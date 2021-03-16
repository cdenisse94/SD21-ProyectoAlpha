package client;

public class ClientLauncher {
    
    public static void main(String[] args) {
        String servidorIP = "localhost";
//        String rutaClientPolicy = "file:C:/Users/lucio/Documents/NetBeansProjects/ProyectoAlpha/src/client/client.policy";
        String rutaClientPolicy = "file:D:/Documents/NetBeans/SD21-ProyectoAlpha/src/client/client.policy";
        ClientThread cliente1 = new ClientThread(rutaClientPolicy, servidorIP);
        cliente1.start();
    }
}
