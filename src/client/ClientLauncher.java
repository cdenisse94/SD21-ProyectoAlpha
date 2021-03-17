package client;

public class ClientLauncher {
    
    private static final int numClientes = 1;
    private static final int numRequests = 1;
    private static ClientThread CtArray[] = new ClientThread[numClientes];
    
    public static void main(String[] args) {
        String servidorIP = "localhost";
        String rutaClientPolicy = "file:C:/Users/lucio/Documents/GitHub/SD21-ProyectoAlpha/src/client/client.policy";
        //String rutaClientPolicy = "file:D:/Documents/NetBeans/SD21-ProyectoAlpha/src/client/client.policy";
        //ClientThread cliente1 = new ClientThread(rutaClientPolicy, servidorIP);
        //cliente1.start();
        for (int i = 0; i<numClientes; i++) {
            ClientThread cliente = new ClientThread(rutaClientPolicy, servidorIP, numRequests);
            CtArray[i] = cliente;
            cliente.start();
        }
    }
}
