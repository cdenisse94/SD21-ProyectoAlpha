package server;

import interfaces.ControlesJuego;
import interfaces.Jugador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class TCPConnection extends Thread {
    
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    ControlesJuego control;
    Jugador jugador;
    
    public TCPConnection (Socket aClientSocket, ControlesJuego control) {
        try {
            clientSocket = aClientSocket;
            this.control = control;
            in = new DataInputStream(clientSocket.getInputStream());
            out =new DataOutputStream(clientSocket.getOutputStream());   
        } catch(IOException e)  {System.out.println("Conexión:"+e.getMessage());}
    }

    @Override
    public void run(){
        try {
            while(true){
                System.out.println("Esperando por mensajes desde: " + clientSocket.getPort());
                String data = in.readUTF();	  
                if(data.equals("registro")){
                    System.out.println("Nuevo cliente en el puerto: " + clientSocket.getPort());
                    out.writeUTF("registrado");
                    control.agrega(new Jugador(clientSocket, 0));
                }else{
                    System.out.println("Mensaje: " + data + " recibido desde: " + clientSocket.getRemoteSocketAddress());
                    System.out.println("Monstruo actual: " + control.getMonstActual());
                    System.out.println("Respuesta obtenida " +  data);
                    if(data.equals(String.valueOf(control.getMonstActual()))){ 
                        control.puntajeJugador(clientSocket);
                        control.setInicio(true);
                    }
                }
            }
        } 
        catch(EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        }catch(IOException e) {
            System.out.println("IO:"+e.getMessage());
        } 
    }
    
    public void setControlesJuego(ControlesJuego control){
        this.control = control;
    }
    
}
