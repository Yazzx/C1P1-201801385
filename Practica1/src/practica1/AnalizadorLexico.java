/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.*;
import java.util.*;

import Objetos.Conjunto;
import Objetos.ExpresionRegular;
import Objetos.Lexema;

/**
 *
 * @author yasmi
 */
public class AnalizadorLexico {

    private ArrayList<Token> Salida;
    private int estado;
    private String auxlex, comentario;

    int contador;

    public ArrayList<Lexema> ListaLexemas = new ArrayList<Lexema>();
    public ArrayList<ExpresionRegular> ListaER = new ArrayList<ExpresionRegular>();
    public ArrayList<Conjunto> ListaConjuntos = new ArrayList<Conjunto>();

    // Banderas 
    boolean ya_pasaron_los_porcent = false;

    boolean nconjabierta = false; // nombre conjunto
    boolean nlexabierta = false; // nombre lexico 
    boolean nerabierta = false;  // nombre er

    boolean conjabierto = true; // PR_CONJ abierto

    int ya_punto = 0;

    // cosas de Java    
    public ArrayList<Token> escanear(String entrada) {

        entrada += '#';
        Salida = new ArrayList<Token>();

        estado = 0;
        auxlex = "";

        Character c = ' ';

        for (int i = 0; i < entrada.length(); i++) {

            c = entrada.charAt(i);

            switch (estado) {

                // aca está lo que manda todo a todos lados
                // <editor-fold>
                case 0:

                    if (Character.isLetter(c)) {
                        estado = 1;
                        auxlex += c;
                    } else if (c.compareTo(';') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.punto_y_coma);

                        // banderas del punto y coma
                    } else if (c.compareTo(' ') == 0) {

                    } else if (c.compareTo('.') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.punto);
                    } else if (c.compareTo(':') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.dos_puntos);
                    } else if (c.compareTo('*') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.asterisco);
                    } else if (c.compareTo('<') == 0) {
                        auxlex += c;
                        estado = 2;
                    }

                    break;
                // </editor-fold>

                // en caso de que sea char
                // <editor-fold>
                case 1:

                    if (Character.isLetter(c)) {
                        estado = 1;
                        auxlex += c;
                    } else if (Character.isDigit(c)) {
                        estado = 1;
                        auxlex += c;
                    } else if (c.compareTo('_') == 0) {
                        estado = 1;
                        auxlex += c;
                    } else if (c.compareTo(' ') == 0 || c.compareTo(':') == 0
                            || c.compareTo('-') == 0) {

                        if (c.compareTo(' ') == 0) {
                            estado = 1;
                        } else if (c.compareTo(':') == 0) {
                            // si viene CONJ de primero;
                            if (conjabierto) {
                                conjabierto = false;
                                if (auxlex.equals("CONJ")) {
                                    agregarToken(Token.Tipo.PR_CONJ);
                                    nconjabierta = true;
                                }
                            } else if (nlexabierta) {
                                nlexabierta = false;
                                agregarToken(Token.Tipo.nombre_lexema);
                            }

                        } else if(c.compareTo('-') == 0){
                            if (nconjabierta) {
                                nconjabierta = false;
                                agregarToken(Token.Tipo.nombre_conjunto);
                            }
                            if(nerabierta){
                                nerabierta = false;
                                agregarToken(Token.Tipo.nombre_expresion);
                            }
                        } else {
                            
                        }

                    }

                    i -= 1;

                    break;
                //</editor-fold>

                default:
                    throw new AssertionError();
            }

        }

        return Salida;

    }

    public void agregarToken(Token.Tipo tipo) {
        Salida.add(new Token(tipo, auxlex));
        auxlex = "";
        estado = 0;
    }

    public void imprimirListaTokens(ArrayList<Token> lista) {

        for (Token token : lista) {

            System.out.println(token.getTipoString() + " " + token.getValor());

        }

    }

}
