/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import cliente.Cliente;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Julio
 */
public class RecepcionClientesGrupo implements Runnable, ListDataListener{
    
     private Socket socket;
     private DataInputStream datosEntrada;
     private DataOutputStream datosSalida;
     private Nodo node;
     private DefaultListModel areaChat;
     
    public RecepcionClientesGrupo(Socket socket, Nodo node,DefaultListModel areaChat)
    {
      
        this.node = node;
        this.socket = socket;
         try{
               this.areaChat = areaChat;
            datosEntrada = new DataInputStream(socket.getInputStream());
            datosSalida = new DataOutputStream(socket.getOutputStream());    
            this.areaChat.addListDataListener(this);
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
                    System.out.println("Cliente quiere subir archivo");
                      downloadFileFromClient(mensaje);
                }else{
                    synchronized(areaChat){
                    datosSalida.writeUTF(mensaje);
                    }
                }
                
            }                
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    private void downloadFileFromClient(String info){
        System.out.println(info);
        final String data[] = info.split("\\*");
            
            // tamano del archivo
            final int FILESIZE = 6022386;
            
            //iniciar nuevo hilo para bajar
            new Thread(new Runnable() {

                @Override
                public void run() {
                    int bytesRead;
                    int current = 0;
                    FileOutputStream fos = null;
                    BufferedOutputStream bos = null;
                    
                    try {
                        //conexion a socket para subir 
                        System.out.println("conectando...");
                        
                        System.out.println("Enviando info!!");
                        Socket downloadSocket = new Socket(data[1], Integer.parseInt(data[2]));
                        
                        System.out.println("conectado!!");
                        
                        //DataInputStream DownloadDatoEntrada = new DataInputStream(socket.getInputStream());;
                        //DataOutputStream DownloadDatoSalida = new DataOutputStream(socket.getOutputStream());
                        
                        // receive file
                        byte [] mybytearray  = new byte [FILESIZE];
                        System.out.println("Recibiendo datos de socket...");
                        InputStream is = downloadSocket.getInputStream();
                        String partName[] = data[3].split("/");
                        String fileName = partName[partName.length-1];
                        fos = new FileOutputStream(node.getPin()+"/"+fileName);
                        bos = new BufferedOutputStream(fos);
                        bytesRead = is.read(mybytearray,0,mybytearray.length);
                        current = bytesRead;
                        
                        do {
                           bytesRead =
                              is.read(mybytearray, current, (mybytearray.length-current));
                           if(bytesRead >= 0) current += bytesRead;
                        } while(bytesRead > -1);

                        bos.write(mybytearray, 0 , current);
                        bos.flush();
                        System.out.println("File " + data[3]
                            + " downloaded (" + current + " bytes read)");
                        datosSalida.writeUTF(getFilesFromPath(node.getPathFile()));
                    } catch (IOException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }finally {
                        try {
                            System.out.println("Cerrando puertos de descarga");
                            if (fos != null) fos.close();
                            if (bos != null) bos.close();
                            System.out.println("Cerrados");
                        } catch (IOException ex) {
                            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
    }
    
     private String getFilesFromPath(String path)
        {
            String resultFiles = "filesNames*";
            File directory = new File(path);
            File[] contents = directory.listFiles();

            for ( File f : contents) {
                resultFiles+=f.getPath() + "*";
                //System.out.println(resultFiles);
            }
            return resultFiles;        
        }

    @Override
    public void intervalAdded(ListDataEvent e) {
        String  mensaje = (String) areaChat.getElementAt(e.getIndex0());
         try {
             datosSalida.writeUTF(mensaje);
         } catch (IOException ex) {
             System.out.println("Error : "+ ex.getMessage());
         }
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void contentsChanged(ListDataEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

