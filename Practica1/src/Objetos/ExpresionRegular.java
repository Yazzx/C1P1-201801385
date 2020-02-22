/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.util.LinkedList;
import practica1.Token;

/**
 *
 * @author yasmi
 */
public class ExpresionRegular {
    
    public String nombre;
    public LinkedList<Token> cosas_preorder;

    public ExpresionRegular(String nombre) {
        this.nombre = nombre;
        cosas_preorder = new LinkedList<Token>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Token> getCosas_preorder() {
        return cosas_preorder;
    }

    public void setCosas_preorder(LinkedList<Token> cosas_preorder) {
        this.cosas_preorder = cosas_preorder;
    }
    
    public String cosasaString(){
        String hola = "";
        
        for (int i = 0; i < cosas_preorder.size(); i++) {
            hola += cosas_preorder.get(i).getTipoString()+ " - " + cosas_preorder.get(i).getValor() + "\t";
        }
        
        return hola;
    }

    @Override
    public String toString() {
        return "ExpresionRegular{" + "nombre=" + nombre + ", cosas_preorder=" + cosasaString() + '}';
    }
    
    
    
}

