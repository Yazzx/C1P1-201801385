/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.io.*;
import java.util.*;
import javax.swing.*;

import Objetos.Conjunto;
import Objetos.ExpresionRegular;
import Objetos.Lexema;

/**
 *
 * @author yasmi
 */
public class AnalizadorLexico2 {

    private LinkedList<Token> Salida;
    private int estado;
    private String auxlex;

    public LinkedList<Token> escanear(String entrada) {
        entrada = entrada + "#";
        Salida = new LinkedList<Token>();
        estado = 0;
        auxlex = "";
        Boolean escadena = false;

        // Banderas
        // {}
        boolean programa_abierto = false;
        boolean conjunto_erabierto = false;

        // tipo de sentencia
        boolean es_conj = false, es_er = false, es_lex = false;
        boolean ya_CONJ = false;

        // tipo de ID
        boolean id_conj = false, id_er = false, id_lex = false;

        // porcentajes
        boolean ya_porcentajes = false;
        int contaporcentajes = 0;

        Character c;
        for (int i = 0; i < entrada.length() - 1; i++) {
            c = entrada.charAt(i);

            switch (estado) {
                case 0:
                    if (Character.isLetter(c)) {
                        estado = 1;
                        auxlex += c;

                    } else if (Character.isDigit(c)) {
                        estado = 2;
                        auxlex += c;
                    } else if (c.compareTo('{') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.llave_abrir);
                        if (programa_abierto == false) {
                            programa_abierto = true;
                        } else if (!conjunto_erabierto && programa_abierto) {
                            conjunto_erabierto = true;
                        }
                    } else if (c.compareTo('}') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.llave_cerrar);

                        if (programa_abierto && conjunto_erabierto) {
                            conjunto_erabierto = false;
                        } else {
                            programa_abierto = false;
                        }
                    } else if (c.compareTo('<') == 0) {
                        auxlex += c;
                        estado = 3;
                    } else if (c.compareTo('!') == 0) {
                        auxlex += c;
                        estado = 4;
                    } else if (c.compareTo('"') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.comillas_dobles);
                        if (escadena == false) {
                            escadena = true;
                            estado = 6;
                        } else if (escadena == true) {
                            escadena = false;
                        }

                    } else if (c.compareTo(';') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.punto_y_coma);
                        es_conj = false;
                        es_er = false;
                        ya_CONJ = false;
                        if (ya_porcentajes) {
                            es_lex = true;
                        }

                    } else if (c.compareTo(':') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.dos_puntos);
                        if (ya_CONJ) {
                            id_conj = true;
                        } else {
                            id_lex = true;
                        }

                    } else if (c.compareTo('\n') == 0) {
                        auxlex = "";
                        estado = 0;
                    } else if (c.compareTo('\t') == 0) {
                        auxlex = "";
                        estado = 0;
                    } else if (c.compareTo(' ') == 0) {
                        auxlex = "";
                        estado = 0;
                    } else if (c.compareTo('!') == 0) {
                        auxlex += c;
                        estado = 5;
                    } else if (c.compareTo('-') == 0) {
                        auxlex += c;
                        estado = 9;
                    } else {
                        if (c.compareTo('#') == 0 && i == entrada.length() - 1) {
                            System.out.println("Hemos concluido :D");
                        } else {
                            //System.out.println("error lexico con: " + c + " :c");
                            JOptionPane.showMessageDialog(null, "Error lexico con: " + c + " :c",
                                    "ERROR :c", JOptionPane.WARNING_MESSAGE);
                            estado = 0;
                            auxlex  = "";
                        }
                    }
                    break;
                case 1:
                    boolean lleva_digitos = false;

                    if (Character.isLetter(c)) {
                        estado = 1;
                        auxlex += c;
                    } else if (Character.isDigit(c)) {
                        estado = 1;
                        auxlex += c;

                        // evaluaciones de que tipo de id_son
                        lleva_digitos = true;

                    } else if (c.compareTo('\t') == 0 || c.compareTo(' ') == 0 || c.compareTo('\n') == 0) {
                        estado = 8;
                        i -= 1;
                    } else if (c.compareTo(':') == 0) {

                        if (!lleva_digitos) {
                            estado = 7;
                        } else {
                            estado = 8;
                        }
                        i -= 1;
                    } else if (c.compareTo('-') == 0) {

                        estado = 8;

                        i -= 1;
                    } else {
                        //Console.WriteLine("error lexico con: " + c + " :c");

                        JOptionPane.showMessageDialog(null, "Error lexico con: " + c + " :c",
                                "ERROR :c", JOptionPane.WARNING_MESSAGE);

                        estado = 0;
                        auxlex = "";
                        // para seguir reconociendo los demás tokens o 
                        //caracteres
                    }
                    break;
                case 2:
                    if (Character.isDigit(c)) {
                        estado = 2;
                        auxlex += c;

                    } else {
                        agregarToken(Token.Tipo.numero);
                        i -= 1;
                    }
                    break;
                case 3:
                    if (c.compareTo('!') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.cmultilinea_abrir);
                        estado = 4;
                    }

                    break;
                case 4:
                    if (c.compareTo('!') == 0) {
                        agregarToken(Token.Tipo.dentro_comentario_multilinea);
                        estado = 5;
                        i -= 1;
                    } else {
                        auxlex += c;
                    }
                    break;
                case 5:
                    if (c.equals('>')) {
                        agregarToken(Token.Tipo.cmultilinea_cerrar);
                    }
                    break;
                case 6:
                    if (c.compareTo('"') == 0) {
                        if (es_lex) {
                            agregarToken(Token.Tipo.cadena_entrada);
                        } else if (es_er) {
                            agregarToken(Token.Tipo.cadena_ER);
                        }
                        i -= 1;
                    } else {
                        estado = 6;
                        auxlex += c;
                    }
                    break;
                case 7:
                    // este solo verifica que palabra es y si está bien
                    if (auxlex.equals("CONJ")) {

                        agregarToken(Token.Tipo.PR_CONJ);
                        // esto en lo general
                        ya_CONJ = true;
                        es_conj = true;

                        i -= 1;
                    } else if (es_lex) {
                        agregarToken(Token.Tipo.nombre_lexema);
                        i -= 1;
                    } else {
                        JOptionPane.showMessageDialog(null, "Error lexico con: " + c + " :c",
                                "ERROR :c, No se reconoce palabra reservada", JOptionPane.WARNING_MESSAGE);
                        estado = 0;
                        auxlex = "";
                        i -= 1;
                    }
                    break;
                case 8:
                    if (es_lex) {
                        agregarToken(Token.Tipo.nombre_lexema);
                        i -= 1;
                    } else if (es_conj) {
                        agregarToken(Token.Tipo.nombre_conjunto);
                    } else if (es_er) {
                        agregarToken(Token.Tipo.nombre_expresion);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error lexico con: " + c + " :c",
                                "ERROR :c, No se reconoce identificador", JOptionPane.WARNING_MESSAGE);
                        estado = 0;
                        auxlex = "";
                        i -= 1;
                    }
                    i -= 1;
                    break;
                case 9:
                    if (c.compareTo('>') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.flecha);
                        
                        
                    }
                    break;

            }
        }

        return Salida;
    }

    public void agregarToken(Token.Tipo tipo) {
        Salida.add(new Token(tipo, auxlex));
        auxlex = "";
        estado = 0;
    }

    public void imprimirListaTokens(LinkedList<Token> lista) {

        for (Token token : lista) {

            System.out.println(token.getValor() +" - " + token.getTipoString());

        }

    }

}
