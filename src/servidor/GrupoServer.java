/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

/**
 *
 * @author Julio
 */
public class GrupoServer implements Runnable {
    private Nodo node;
    private int sockerPort;
    private DefaultListModel x  = new DefaultListModel<Object>();
   
   public GrupoServer(int sockerPort, Nodo node)
    {
       // this.txtArea = txtArea;
        this.node = node;
        this.sockerPort = sockerPort;
      
    }

    @Override
    public void run() {
          try {
            ServerSocket socketServidor = new ServerSocket(sockerPort);
            while(true)
            {
                Socket socket = socketServidor.accept();
                Thread hilo = new Thread(new RecepcionClientesGrupo( socket, node,x));
                hilo.start();
            }
            
        } catch (Exception e) {
        }
    }
    
    
    
}
