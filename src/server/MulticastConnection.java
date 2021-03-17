package server;

import interfaces.ControlesJuego;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class MulticastConnection {

    private static int puertoMulticast = 6789;
    private static String ipMulticast = "228.5.6.10";
    InetAddress grupo;
    MulticastSocket s;

    public MulticastConnection() {
        try {
            grupo = InetAddress.getByName(ipMulticast);
            s = new MulticastSocket(puertoMulticast);
            s.joinGroup(grupo);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
        }
    }

    public void enviaFin(String ganador, ControlesJuego juego) throws IOException, InterruptedException {
        String mensaje = "";
        if (ganador.equals("EMPATE")) {
            mensaje = "~EMPATE~";
        } else {
            System.out.println("GANADOR: " + ganador);
            mensaje ="~" + ganador + "~";
        }
        byte[] m = mensaje.getBytes();
        DatagramPacket mensajeSalida = new DatagramPacket(m, m.length, grupo, puertoMulticast);
        s.send(mensajeSalida);
    }

    public void enviaMonstruo(int random) {
        try {
            String mensaje = Integer.toString(random);
            System.out.println("Monstruo a enviar: " + mensaje);
            byte[] m = mensaje.getBytes();
            DatagramPacket messageOut = new DatagramPacket(m, m.length, grupo, puertoMulticast);
            s.send(messageOut);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
        }
    }
    
}
