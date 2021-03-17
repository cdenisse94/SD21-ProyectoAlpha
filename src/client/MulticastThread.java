package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.TCPThread;

public class MulticastThread extends Thread {
    
    private static int puertoServer; 
    private InetAddress direccionIP;
    private ClientThread clientThread;
    
    public MulticastThread (int puertoServer, InetAddress direccionIP, ClientThread socketTCP) throws IOException{
        this.puertoServer = puertoServer;
        this.direccionIP = direccionIP;
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
                    TimeUnit.SECONDS.sleep(2);
                }
                else {
                    if (data.trim().length()==1){
                        String number = String.valueOf(data.charAt(0));
                        
                        Random r = new Random();
                        int num = r.nextInt(9) + 1;
                        int num2 = r.nextInt(10) +1;
                        Socket s = clientThread.getSocket();
                        DataInputStream in = new DataInputStream(s.getInputStream());
                        DataOutputStream out = new DataOutputStream(s.getOutputStream());
                        out.writeUTF(String.valueOf(num));
                        TimeUnit.MILLISECONDS.sleep(num2);
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