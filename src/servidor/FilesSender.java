/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julio
 */
public class FilesSender implements Runnable {
    String path;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    private ServerSocket socket;
    private File archivo = null;
   private Socket sock = null;
    public FilesSender(String path,String ip,String socketPort) {
          this.path = path;
          System.out.println("Data: path: "+ path + "ip: "+ip+ " puerto: "+ socketPort);
            try{
                
                socket = new ServerSocket(13267);
                sock = socket.accept();
                
                System.out.println("hola");
                archivo = new File(path);
                System.out.println("hola2");
            }catch(Exception e)
            {
                System.out.println(e.toString());
            }
    }
    
    @Override
    public void run() {
        try{
            System.out.println("Realizando prueba");
            byte [] mybytearray  = new byte [(int)archivo.length()];
            System.out.println("Realizando prueba");
            fis = new FileInputStream(archivo);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            os = sock.getOutputStream();
               
            System.out.println("Enviando " + path + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            System.out.println("Terminado...");

        }catch(Exception e)
        {
            System.out.println("Error al enviar archivo: "+e.toString());
        }finally {
            try{
                if (bis != null) 
                    bis.close();
                if (os != null) 
                    os.close();
                if (socket!=null) 
                    socket.close();
            } catch (IOException ex) {
                Logger.getLogger(FilesSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
