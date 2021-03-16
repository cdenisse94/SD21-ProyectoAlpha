package interfaces;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatosJuego implements Serializable{
    
    private InetAddress ipTCP;
    private InetAddress ipMulticast;
    private int puertoTCP;
    private int puertoMulticast;
    
    public DatosJuego(String ipTCP, String ipMulticast, int puertoTCP, int puertoMulticast) {
        try {
            this.ipTCP = InetAddress.getByName(ipTCP);
            this.ipMulticast = InetAddress.getByName(ipMulticast);
            this.puertoMulticast = puertoMulticast;
            this.puertoTCP = puertoTCP;
        } catch (UnknownHostException ex) {
            Logger.getLogger(DatosJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InetAddress getIpTCP() {
        return ipTCP;
    }
    
    public InetAddress getIpMulticast() {
        return ipMulticast;
    }

    public int getPuertoMulticast() {
        return puertoMulticast;
    }
    
    public int getPuertoTCP() {
        return puertoTCP;
    }
    
}
