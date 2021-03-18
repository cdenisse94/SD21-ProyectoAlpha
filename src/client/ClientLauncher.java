package client;

public class ClientLauncher {
    
    private static final int nClientes = 5;
    private static final int nRequest = 2;
    private static ClientThread CTarreglo [] = new ClientThread[nClientes];
    
    public static void main(String[] args) {
        String servidorIP = "localhost";
//        String rutaClientPolicy = "file:C:/Users/lucio/Documents/NetBeansProjects/ProyectoAlpha/src/client/client.policy";
        String rutaClientPolicy = "file:C:/Users/agnar/Documents/dis/SD21-ProyectoAlpha/src/client/client.policy";
        //ClientThread cliente1 = new ClientThread(rutaClientPolicy, servidorIP);
        //cliente1.start();
        
        for(int i = 0; i< nClientes; i++){
            ClientThread clientThread = new ClientThread(rutaClientPolicy,servidorIP,nRequest);
            CTarreglo[i] = clientThread;
            clientThread.start();
        }
        
        
    }
}
