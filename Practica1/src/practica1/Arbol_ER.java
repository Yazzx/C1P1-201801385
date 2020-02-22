/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import practica1.MainFrame;

/**
 *
 * @author yasmi
 */
public class Arbol_ER {

    String miDot = "digraph G {\\n\"";

    int contacirculos = 0, numeros_abajo = 1;
    Nodo_Posicion raiz;

    // ESTA ES LA LISTA QUE TENGO EN CADA EXPRESION REGULAR
    LinkedList<Token> Expresion_regular_separada;

    // METO TODO EN UN FOR Y POR CADA EXPRESION REGULAR SALE UN ARCHIVO.DOT
    public Arbol_ER(LinkedList<Token> Expresion_regular_separada) {
        this.Expresion_regular_separada = Expresion_regular_separada;
        this.raiz = otroNodo();
    }

    public Nodo_Posicion otroNodo() {

        // PARA UN OR
        if (this.Expresion_regular_separada.get(0).getTipoToken() == Token.Tipo.operador_binario) {

            // SI ES BINARIA TIENE IZQUIERDA Y DERECHA
            Nodo_Posicion holi = new Nodo_Posicion(++contacirculos, new Circulo(Circulo.Tipo.operador_binario, this.Expresion_regular_separada.get(0).getValor()));
            this.Expresion_regular_separada.remove(0);

            holi.setDerecho(otroNodo());
            holi.setIzquierdo(otroNodo());
            return holi;
        } else if (this.Expresion_regular_separada.get(0).getTipoToken() == Token.Tipo.operador_unario) {

            // SI ES UNARIA SOLO TIENE ABAJO o izquiera, porque no me queda con tres alv
            // y porque es preorder???
            Nodo_Posicion holi = new Nodo_Posicion(++contacirculos, new Circulo(Circulo.Tipo.operador_unario, this.Expresion_regular_separada.get(0).getValor()));
            this.Expresion_regular_separada.remove(0);

            holi.setIzquierdo(otroNodo());
            return holi;
        } else if (this.Expresion_regular_separada.get(0).getTipoToken() == Token.Tipo.terminal) {
            Nodo_Posicion holi = new Nodo_Posicion(++contacirculos, new Circulo(Circulo.Tipo.terminal, this.Expresion_regular_separada.get(0).getValor()));

            return holi;
        } else if (this.Expresion_regular_separada.get(0).getTipoToken() == Token.Tipo.aceptacion) {

            Nodo_Posicion holi = new Nodo_Posicion(++contacirculos, new Circulo(Circulo.Tipo.aceptacion, this.Expresion_regular_separada.get(0).getValor()));
            this.Expresion_regular_separada.remove(0);

            return holi;
        }

        return null;
    }

     public String generarmiDot(Nodo_Posicion holi) {

        // si el nodo en cuestion tiene ezquierda
        if (holi.getIzquierdo() != null) {
            miDot += "nodo" + holi.getId() + "->" + "nodo" + holi.getIzquierdo().getId() + "; \n";
            miDot += generarmiDot(holi.getIzquierdo());
        }
        // si el nodo en cuestion tiene derecha
        if (holi.getDerecho() != null) {
            miDot += "nodo" + holi.getId() + "->" + "nodo" + holi.getDerecho().getId() + "; \n";
            miDot += generarmiDot(holi.getDerecho());
        }

        if (holi == null) {

        }

        return miDot;
    }
    
    
     public void Generardot() throws InterruptedException {
         miDot +=generarmiDot(this.raiz);
            miDot += "}";
           String cosa = "C:\\Users\\yasmi\\OneDrive\\Escritorio\\Arbol" + Integer.toString(Practica1.contavecesER) + ".dot"; 
           String cosa1 = "C:\\Users\\yasmi\\OneDrive\\Escritorio\\Arbol" + Integer.toString(Practica1.contavecesER) + ".png";
         Practica1.contavecesER++;
         
            FileWriter fichero = null;
            try {
                FileWriter fw = new FileWriter(cosa);
            String texto = miDot;
            fw.write(miDot);
            fw.close();
            System.out.println("Tu archivo ha sido creado <3");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fichero) {
                        fichero.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

            sleep(200);

            try {
                String[] cmd = {"dot", "-Tpng", cosa, "-o", cosa1};
                Runtime.getRuntime().exec(cmd);
                System.out.println("Generando.png!!");
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
            sleep(300);
            

        } 
}
