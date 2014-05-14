/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.awt.TextArea;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

/**
 *
 * @author Julio
 */
public class Servidor implements Runnable {
       
    
  //  private JTextArea txtChat;
    private ArrayList<Nodo> grupos;
   public Servidor(ArrayList<Nodo> grupos)
   {
        this.grupos = grupos;
   }
 
   /*public static void main(String[] args)
    {
        new Servidor();
    }
    */

    @Override
    public void run() {
           // this.txtArea = txtArea;
        try {
            ServerSocket socketServidor = new ServerSocket(9997);
            while(true)
            {
                Socket socket = socketServidor.accept();
                Thread hilo = new Thread(new RecepcionClientes(socket, grupos));
                hilo.start();
                
            }
            
        } catch (Exception e) {
        }
    }
}
