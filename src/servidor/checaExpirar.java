/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Gonz
 */
public class checaExpirar implements Runnable {

    private ArrayList<Nodo> grupos;

    public checaExpirar(ArrayList<Nodo> grupos) {
        this.grupos = grupos;
    }

    @Override
    public void run() {
        
        try {
            while (true) {
                //System.out.println("empezandoa  cheacar");
                int tamini =grupos.size();
                
                synchronized (grupos) {
                    ListIterator<Nodo> i = grupos.listIterator();
                    while ( i.hasNext()) {
                        Nodo node = i.next();

                        if (Grupos.caducidad(node)) {
                            System.out.println("Removi el nodo con PIN " + node.getPin());
                            i.remove();

                        }
                    }

                }
                
                if(tamini!=grupos.size())
                {
                    System.out.println("como se borró un elemento de la lista rehacemos el archivo de configuración");
                    Grupos.guardarNodos(grupos);
                }
                Thread.sleep(10000);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
