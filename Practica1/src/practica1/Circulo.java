/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.util.LinkedList;

/**
 *
 * @author yasmi
 */
public class Circulo {
    
     enum Tipo {
        operador_unario,
        operador_binario,
        terminal, 
        aceptacion
    }

    // esto es todo lo que lleva la hoja
    String lexema;
    Tipo tipo;
    boolean anulable;
    public LinkedList<Circulo> primeros;
    public LinkedList<Circulo> ultimos;
    int id;
    
    Circulo(Tipo tipo, String lexema) {

        this.tipo = tipo;
        this.lexema = lexema;
        primeros = new LinkedList<Circulo>();
        ultimos = new LinkedList<Circulo>();

    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean isAnulable() {
        return anulable;
    }

    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }

    public LinkedList<Circulo> getPrimeros() {
        return primeros;
    }

    public void setPrimeros(LinkedList<Circulo> primeros) {
        this.primeros = primeros;
    }

    public LinkedList<Circulo> getUltimos() {
        return ultimos;
    }

    public void setUltimos(LinkedList<Circulo> ultimos) {
        this.ultimos = ultimos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
