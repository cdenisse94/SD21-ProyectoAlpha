package server;

import interfaces.DatosJuego;
import java.rmi.RemoteException;
import interfaces.InfoJuego;

public class InfoJuegoServer implements InfoJuego{
    
    public static String ipServerTCP="localhost";
    public static String ipServerMulticast="228.5.6.10";
    public static int puertoServerTCP=7896;
    public static int puertoServerMulticast=6789;
    
    public InfoJuegoServer() throws RemoteException{
        super();
    }
    
    @Override
    public DatosJuego getInfo( ) throws RemoteException {
        return new DatosJuego(ipServerTCP, ipServerMulticast, puertoServerTCP, puertoServerMulticast);
    }
    
}
