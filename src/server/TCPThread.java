package server;

import interfaces.ControlesJuego;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPThread extends Thread {
    
    private static int puertoServer = 7896; 
    private ServerSocket listenSocket;
    private ControlesJuego control;
    private ArrayList<TCPConnection> clientSockets;
    
    public TCPThread (ControlesJuego control) throws IOException{
         this.listenSocket = new ServerSocket(puertoServer);
         this.control = control;
         clientSockets = new ArrayList<TCPConnection>();
    }
    
    @Override
    public void run(){
        while(true) {
            try {
                System.out.println("Esperando por jugadores...");
                Socket clientSocket = listenSocket.accept(); 
                TCPConnection tcpConnection = new TCPConnection(clientSocket, control);
                clientSockets.add(tcpConnection);
                tcpConnection.start();
            } catch (IOException ex) {
                Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setControlesJuego(ControlesJuego control){
        this.control = control;
        for (int i = 0; i<clientSockets.size();i++){
            clientSockets.get(i).setControlesJuego(control);
        }
    }
    
}
