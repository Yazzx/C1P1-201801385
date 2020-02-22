/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

/**
 *
 * @author yasmi
 */
public class Nodo_Posicion {
    
    int id;
    Nodo_Posicion derecho, izquierdo, abajo;
    Circulo circulo;

    public Nodo_Posicion(int id, Circulo circulo) {
        this.id = id;
        this.circulo = circulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Nodo_Posicion getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo_Posicion derecho) {
        this.derecho = derecho;
    }

    public Nodo_Posicion getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo_Posicion izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo_Posicion getAbajo() {
        return abajo;
    }

    public void setAbajo(Nodo_Posicion abajo) {
        this.abajo = abajo;
    }

    public Circulo getCirculo() {
        return circulo;
    }

    public void setCirculo(Circulo circulo) {
        this.circulo = circulo;
    }
    
    
    
}
