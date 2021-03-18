package client;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class InterfazGrafica extends JFrame implements ItemListener {
    private JFrame frame,frame1;
    private JCheckBox[] checkBox;
    private Socket socketTCP;
    
    public InterfazGrafica (Socket sTCP){
        this.socketTCP = sTCP;
        frame = new JFrame("Número de Jugador: " + sTCP.getLocalPort());
        frame.setLayout(new GridLayout(3,3));
        checkBox = new JCheckBox[9];
        for (int i = 0; i<9; i++){
            checkBox[i] = new JCheckBox("hoyo " + (i+1));
            checkBox[i].setName(Integer.toString(i+1));
            frame.add(checkBox[i]);
            checkBox[i].addItemListener(this);
        }
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    @Override
    public void itemStateChanged(ItemEvent e){
        try {
            if(((JCheckBox)e.getItem()).isSelected()){
                String name =  ((JCheckBox) e.getItem()).getName()   ;
                DataOutputStream out = new DataOutputStream(socketTCP.getOutputStream());
                out.writeUTF(name);
                this.desactivaTodo();
            }
        } catch (IOException ex) {
            Logger.getLogger(InterfazGrafica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void desactivaTodo(){
        for (int i = 0; i<9; i++){
            checkBox[i].setEnabled(false);
        }
    }
    
    public void activaTodo(){
        for (int i = 0; i<9; i++){
            checkBox[i].setEnabled(true);
            checkBox[i].setSelected(false);
        }
    }

    public void marcaCasilla(int numero){
        this.activaTodo();
        for (int i = 0; i<9; i++){
                checkBox[i].setForeground(Color.black);
        } 
        checkBox[numero-1].setForeground(Color.red);
    } 
    
    public void victoria(String message){
        this.desactivaTodo();
        JOptionPane.showMessageDialog(null, "El ganador es: " + message);
    }
    
}
