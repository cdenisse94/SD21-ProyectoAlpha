package client;

import interfaces.DatosJuego;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.InfoJuego;

public class ClientThread extends Thread{
    
    private String rutaClientPolicy;
    private String ipRMI;
    private Socket socketTCP;
    private int nrequests;
    
    public ClientThread (String pathPolicy, String ipRMI, int nRequest){
        this.rutaClientPolicy = pathPolicy;
        this.ipRMI = ipRMI;
        this.nrequests=nRequest;
        Socket socketTCP = null;
    }
    
    public void run(){
        try {
            System.setProperty("java.security.policy",rutaClientPolicy);
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            String nombre = "Información del juego";
            Registry registro = LocateRegistry.getRegistry(ipRMI);
            InfoJuego juego = (InfoJuego) registro.lookup(nombre);
            DatosJuego datosJuego = juego.getInfo();
            int serverTCPPort = datosJuego.getPuertoTCP();
            
            InetAddress serverTCPIp = datosJuego.getIpTCP();
            socketTCP = new Socket(serverTCPIp, serverTCPPort);
            DataInputStream in = new DataInputStream(socketTCP.getInputStream());
            DataOutputStream out = new DataOutputStream(socketTCP.getOutputStream());
            out.writeUTF("registro");
            
            
            MulticastThread multiThread;
            String data = in.readUTF();
            
            
            if (data.equals("registrado")){
                System.out.println("Recibido: " + data) ;
                multiThread = new MulticastThread(datosJuego.getPuertoMulticast(), datosJuego.getIpMulticast(), this);
                multiThread.start();
            }
            
            socketTCP.close();
        } catch (RemoteException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Socket getSocket(){
        return socketTCP;
    }
    
}
