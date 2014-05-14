package servidor;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gonz
 */
public class Grupos {

    public static void main(String[] args) {

        Nodo n = new Nodo("Gonz", "9999", 11, 11, 1111, 12000, "9999");
        Nodo n2 = new Nodo("Bego", "1234", 12, 12, 2015, 12000, "1234");
        Nodo n3 = new Nodo("Collado", "5321", 11, 11, 2010, 12000, "5321");
        Nodo n4 = new Nodo("Pepe", "2222", 11, 11, 2025, 12000, "2222");
        ArrayList<Nodo> grupos = new ArrayList<>();
        grupos.add(n);
        guardarNodos(grupos);

    }

    public static void guardarNodos(ArrayList<Nodo> grupos) {
        try {
            FileWriter fw = new FileWriter("grupos.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Nodo n : grupos) {
                bw.write(n.getPin() + " ");
                bw.write(n.getGrupoNombre() + " ");
                bw.write(n.getNumSocket() + " ");
                bw.write(n.getDay() + " ");
                bw.write(n.getMonth() + " ");
                bw.write(n.getYear() + " ");
                bw.write(n.getPathFile());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
        }

    }

    public static ArrayList<Nodo> cargarNodos() {
        ArrayList<Nodo> grupos = new ArrayList<>();

        try {
            FileReader fr = new FileReader("grupos.txt");
            BufferedReader br = new BufferedReader(fr);

            String l;

            while ((l = br.readLine()) != null) {
                //String grupoNombre, String pin, int day, int month, int year, int numSocket
                String[] nInfo = l.split(" ");

                if (nInfo.length > 5) {
                    String pin = nInfo[0];
                    String grupoNombre = nInfo[1];
                    int numSocket = Integer.parseInt(nInfo[2]);
                    int day = Integer.parseInt(nInfo[3]);
                    int month = Integer.parseInt(nInfo[4]);
                    int year = Integer.parseInt(nInfo[5]);
                    String pathFile = nInfo[6];

                    System.out.println("Se cargara el nodo con pin " + pin);
                    grupos.add(new Nodo(grupoNombre, pin, day, month, year, numSocket, pathFile));
                }

            }
            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return grupos;
    }

    public static boolean caducidad(Nodo node) {
        try {
            Date fecha = new SimpleDateFormat("MM/dd/yyyy").parse("" + Integer.toString(node.getMonth())
                    + "/" + Integer.toString(node.getDay())
                    + "/" + Integer.toString(node.getYear()) + "");
            Date hoy = new Date();
            return hoy.after(fecha);

        } catch (ParseException ex) {
            Logger.getLogger(Grupos.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

}
