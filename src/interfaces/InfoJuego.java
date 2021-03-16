package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InfoJuego extends Remote {

	public DatosJuego getInfo ( ) throws RemoteException;

}

