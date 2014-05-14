/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

/**
 *
 * @author Julio
 */
public class Nodo {

    public Nodo(String grupoNombre, String pin, int day, int month, int year, int numSocket) {
        this.grupoNombre = grupoNombre;
        this.pin = pin;
        this.day = day;
        this.month = month;
        this.year = year;
        this.numSocket = numSocket;
    }
    
    private String grupoNombre;

    
    
    public String getGrupoNombre() {
        return grupoNombre;
    }

    public void setGrupoNombre(String grupoNombre) {
        this.grupoNombre = grupoNombre;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    private String pin;
    private int day;
    private int month;
    private int year;
    private String pathFile;
    private int numSocket;

    public int getNumSocket() {
        return numSocket;
    }

    public void setNumSocket(int numSocket) {
        this.numSocket = numSocket;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getPathFile() {
        return pathFile;
    }
    
    
}
