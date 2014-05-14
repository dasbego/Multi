/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Julio
 */
public class RecepcionClientes implements Runnable {
    private Socket socket;
   
    private DataInputStream datosEntrada;
    private DataOutputStream datosSalida;
    private ArrayList<Nodo> grupos;
    public RecepcionClientes (Socket socket, ArrayList<Nodo> grupos)
    {
        this.socket = socket;
        this.grupos = grupos;
       
        try{
            datosEntrada = new DataInputStream(socket.getInputStream());
            datosSalida = new DataOutputStream(socket.getOutputStream());
         
        }catch(Exception e)
        {
            
        }
    }
    @Override
    public void run() {
        try {
            while(true)
            {
                String mensaje = datosEntrada.readUTF();
                synchronized (grupos){
                   if(grupos.isEmpty())
                       continue;
                    for (Nodo nodo : grupos) {
                        if(nodo.getPin().equals(mensaje))
                        {
                            System.out.println("Cliente solicita entrada al grupo: "+nodo.getGrupoNombre());                            
                            String numSocket = nodo.getNumSocket()+ "";
                            System.out.println("Se devuelve socket: "+numSocket);
                            datosSalida.writeUTF(numSocket);
                        }
                    }
                }
            }
                
        } catch (Exception e) {
        }
    }

    
    
}
