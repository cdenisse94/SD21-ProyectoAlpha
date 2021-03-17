package interfaces;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import server.MulticastConnection;

public class ControlesJuego {
    
    private ArrayList<Jugador> arregloJugadores;
    private MulticastConnection multicast;
    private int puntajeJugador;
    private Jugador jugador;
    private boolean inicio;
    private static int PUNTOS = 5;
    
    private int monstActual;
    private boolean fin;
    private double tiempoInicio;
    private double startTime;
    
    public ControlesJuego(){
        startTime = System.currentTimeMillis();
        arregloJugadores = new ArrayList<Jugador>();
        puntajeJugador=0;
        inicio = true;
        fin = false;     
        
    }

    public boolean getFin(){
        return fin;
    }
    
    public ArrayList<Jugador> getArregloJugadores(){
        return arregloJugadores;
    }
     
    public void setArregloJugadores(ArrayList<Jugador> a){
        this.arregloJugadores = a;
    }
        
    public void agrega(Jugador jugador){
        System.out.println(jugador);
        arregloJugadores.add(jugador);
    }

    @Override
    public String toString() {
        return "ControlesJuego" + "arregloJugadores=" + arregloJugadores + '}';
    }
    
    public int random(){
        this.monstActual = 1 + new Random().nextInt(9);
        return this.monstActual;
    }
    
    public void enviaMonstruo() throws InterruptedException{
        this.random();
        System.out.println(arregloJugadores.toString());
        multicast.enviaMonstruo(monstActual);
    }

    public int getMonstActual() {
        return monstActual;
    }
    
    public void setMulticast(MulticastConnection multicast){
        this.multicast = multicast;
    }
    
    public boolean start() throws InterruptedException, IOException{
        FileWriter archivo = new FileWriter("prueba.txt",true);
        if (inicio){
            if(puntajeJugador<PUNTOS){
                enviaMonstruo();
                inicio=false;
                tiempoInicio = System.currentTimeMillis()-startTime;
                archivo.write(String.valueOf(tiempoInicio)+" "+"\n");
                archivo.write("\n");
                archivo.close();
                startTime = System.currentTimeMillis();
                return true;
            }
            else {
                int max = 0;
                boolean empate = false;
                System.out.println(jugador.getSocket().getPort());
                multicast.enviaFin(String.valueOf(jugador.getSocket().getPort()), this);
                puntajeJugador=0;
                this.setZeros();
                inicio = false;
                fin = true;
                return false;
            }
        }
        if(System.currentTimeMillis()-tiempoInicio>10000){
            inicio=true;
        }
        return false;
    }
    
    public void setZeros(){
        for (int i=0;i<arregloJugadores.size();i++){
            arregloJugadores.get(i).setPuntaje(0);
        }
    }
        
    public void puntajeJugador(Socket socketAddress){
        int jugadorAuxiliar = arregloJugadores.indexOf(new Jugador(socketAddress));
        if (jugadorAuxiliar!=-1){
            int auxPoints = arregloJugadores.get(jugadorAuxiliar).punto();
            if (auxPoints>puntajeJugador){
                puntajeJugador=auxPoints;
                jugador=arregloJugadores.get(jugadorAuxiliar);
            }
        }
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }
    
}
