/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

/**
 *
 * @author yasmi
 */
public class Conjunto {
    
    public String identificador, conjunto;

    public Conjunto(String identificador, String conjunto) {
        this.identificador = identificador;
        this.conjunto = conjunto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getConjunto() {
        return conjunto;
    }

    public void setConjunto(String conjunto) {
        this.conjunto = conjunto;
    }
    
    
    
}
