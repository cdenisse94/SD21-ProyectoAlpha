package interfaces;

import java.net.Socket;
import java.util.Objects;

public class Jugador {
    
    private int puntaje;
    private Socket socket;

    public Jugador(Socket socket, int puntaje) {
        this.puntaje = puntaje;
        this.socket = socket;
    }
        
    public Jugador(Socket socket) {
        this.socket = socket;
    }

    public Jugador() {
    }
        
    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    public int punto(){
        puntaje++; 
        return puntaje;
    }
    
    @Override
    public String toString() {
        return "Jugador{" + "puntaje=" + puntaje + ", socket=" + socket + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugador other = (Jugador) obj;
        if (!Objects.equals(this.socket, other.socket)) {
            return false;
        }
        return true;
    } 
    
}
