/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Julio
 */
public class RecepcionClientesGrupo implements Runnable{
    
     private Socket socket;
     private DataInputStream datosEntrada;
     private DataOutputStream datosSalida;
     private Nodo node;
     
    public RecepcionClientesGrupo(Socket socket, Nodo node)
    {
        
        this.node = node;
        this.socket = socket;
         try{
            datosEntrada = new DataInputStream(socket.getInputStream());
            datosSalida = new DataOutputStream(socket.getOutputStream());            
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    @Override
    public void run() {
        try {
            while(true)
            {
                String mensaje = datosEntrada.readUTF();
                System.out.println("Al servidor de pin:"+node.getPin()+ " ha llegado: " +mensaje);
                
                if (mensaje.contains("NewUser*")){
                    System.out.println("Ingreso de nuevo usuario, mandando: "+getFilesFromPath(node.getPathFile()));
                    datosSalida.writeUTF(getFilesFromPath(node.getPathFile()));
                }
              
                //FilePath*IP*Puerto*PathAlArchivo
                else if(mensaje.contains("FilePath*")){
                      String[] arrayData = mensaje.split("\\*");
                      Thread SenderThread = new Thread(new FilesSender(arrayData[3], arrayData[1], arrayData[2]));
                      SenderThread.start();
                }
                else if(mensaje.contains("NewFile*")){
                      
                }else
                    datosSalida.writeUTF(mensaje);
            }                
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }    
     private String getFilesFromPath(String path)
        {
            String resultFiles = "filesNames*";
            File directory = new File(path);
            File[] contents = directory.listFiles();

            for ( File f : contents) {
                resultFiles+=f.getPath() + "*";
                System.out.println(resultFiles);
            }
            return resultFiles;        
        }
    
}

