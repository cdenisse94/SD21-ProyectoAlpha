package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.TCPThread;

public class MulticastThread extends Thread {
    
    private static int puertoServer; 
    private InetAddress direccionIP;
    private InterfazGrafica interfaz;
    private ClientThread clientThread;
    
    public MulticastThread (int puertoServer, InetAddress direccionIP, InterfazGrafica interfaz, ClientThread socketTCP) throws IOException{
        this.puertoServer = puertoServer;
        this.direccionIP = direccionIP;
        this.interfaz = interfaz;
        this.clientThread = clientThread;
    }
    
    public void run(){
        try {
            MulticastSocket listenSocket = new MulticastSocket(puertoServer);
            listenSocket.joinGroup(direccionIP); 
            while(true) {
	    	byte[] buffer = new byte[20];
                System.out.println("Esperando por mensajes MULTICAST");
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                listenSocket.receive(messageIn);
                String data = new String(messageIn.getData());
                System.out.println("Mensaje: " + data);
                if(data.charAt(0)=='*'){
                    interfaz.victoria(data);
                    TimeUnit.SECONDS.sleep(2);
                }
                else {
                    if (data.trim().length()==1){
                        String number = String.valueOf(data.charAt(0));
                        interfaz.marcaCasilla(Integer.valueOf(number));
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MulticastThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}