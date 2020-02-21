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
public class AnalizadorLexico {

    private ArrayList<Token> Salida;
    private int estado;
    private String auxlex, comentario;

    int contatoken = 0;

    public ArrayList<Lexema> ListaLexemas = new ArrayList<Lexema>();
    public ArrayList<ExpresionRegular> ListaER = new ArrayList<ExpresionRegular>();
    public ArrayList<Conjunto> ListaConjuntos = new ArrayList<Conjunto>();

    // Banderas 
    boolean ya_pasaron_los_porcent = false;

    boolean nconjabierta = false; // nombre conjunto
    boolean nlexabierta = false; // nombre lexico 
    boolean nerabierta = true;  // nombre er

    boolean conjabierto = true; // PR_CONJ abierto

    boolean programa_abierto = false;   // llaves de principio
    boolean conj_enER = false;      // llave para ver conjuntos 

    boolean cadena_lexema = false;
    boolean cadena_ER = false;

    boolean eslexema = false, esconj = false, eser = false;
    boolean escadena = false;

    int porcentaje = 0;
    int contacaracter = 0;

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
                        contacaracter++;
                    } else if (c.compareTo(';') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.punto_y_coma);

                        // banderas del punto y coma
                        conjabierto = true;
                        nerabierta = true;

                        if (ya_pasaron_los_porcent) {
                            nlexabierta = true;
                            nerabierta = false;
                        }

                        eslexema = false;
                        eser = false;
                        esconj = false;

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
                    } else if (c.compareTo('!') == 0) {
                        auxlex += c;
                        estado = 4;
                    } else if (c.compareTo('/') == 0) {
                        auxlex += c;
                        estado = 5;
                    } else if (c.compareTo('{') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.llave_abrir);

                        if (!programa_abierto) {
                            programa_abierto = true;
                        } else {
                            conj_enER = true;
                            estado = 11;
                        }
                    } else if (c.compareTo('}') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.llave_cerrar);

                        if (conj_enER) {
                            conj_enER = false;
                        } else {
                            programa_abierto = false;
                        }
                    } else if (c.compareTo(',') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.coma);
                    } else if (c.compareTo('-') == 0) {
                        auxlex += c;
                        estado = 7;
                    } else if (c.compareTo('%') == 0) {
                        auxlex += c;
                        estado = 8;
                    } else if (c.compareTo('"') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.comillas_dobles);

                        if (escadena == false) {
                            escadena = true;
                            // aqui abro cadena
                            estado = 9;
                        } else if (escadena == true) {
                            escadena = false;
                            //aquí cierro cadena
                        }
                    } else if (c.compareTo('~') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.virgulilla);
                    } else if (c.compareTo('|') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.barra_vertical);
                    } else if (c.compareTo('+') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.signo_mas);
                    } else if (c.compareTo('?') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.interrogacion_cerrar);
                    } else if (c.compareTo(',') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.coma);
                    } else if (c.compareTo('\n') == 0) {
                        auxlex = "";
                    } else if (Character.isDigit(c)) {
                        if (esconj) {
                            estado = 10;
                            auxlex += c;
                        }
                    } else {

                        if (esconj) {
                            auxlex += c;
                            agregarToken(Token.Tipo.caracter_ASCII);
                        } else {

                            JOptionPane.showMessageDialog(null, "El texto ingresado contiene errores, por favor "
                                    + "corrígalos e intente de nuevo :c \n" + "->" + auxlex + "<-\n" + c,
                                    "ERROR :c", JOptionPane.WARNING_MESSAGE);

                        }

                    }

                    break;
                // </editor-fold>

                // en caso de que sea char
                // <editor-fold>
                case 1:

                    if (Character.isLetter(c)) {
                        estado = 1;
                        contacaracter++;
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

                            if (contacaracter == 1 && esconj) {

                                char auxaux = auxlex.charAt(0);
                                if (Character.isUpperCase(auxaux)) {
                                    agregarToken(Token.Tipo.letra_mayuscula);
                                } else if (!Character.isUpperCase(auxaux)) {
                                    agregarToken(Token.Tipo.letra_minuscula);
                                }

                            }

                            estado = 1;
                        } else if (c.compareTo(':') == 0) {
                            // si viene CONJ de primero;
                            if (conjabierto) {
                                conjabierto = false;
                                if (auxlex.equals("CONJ")) {
                                    agregarToken(Token.Tipo.PR_CONJ);
                                    nconjabierta = true;
                                    esconj = true;
                                }
                            } else if (nlexabierta) {
                                nlexabierta = false;
                                agregarToken(Token.Tipo.nombre_lexema);
                                cadena_lexema = true;
                                eslexema = true;
                            }

                        } else if (c.compareTo('-') == 0) {
                            if (nconjabierta) {
                                nconjabierta = false;
                                agregarToken(Token.Tipo.nombre_conjunto);
                            }
                            if (nerabierta) {
                                nerabierta = false;
                                agregarToken(Token.Tipo.nombre_expresion);
                                cadena_ER = true;
                                eser = true;
                            }
                        } else {
                            if (c.compareTo(' ') == 0 || c.compareTo('\t') == 0 || c.compareTo('\n') == 0) {
                                System.out.println("espaico o tab o salto");
                            } else if (c.compareTo('#') == 0 && i == entrada.length() - 1) {
                                System.out.println("Hemos concluido :D");
                            } else {

                                JOptionPane.showMessageDialog(null, "El texto ingresado contiene errores, por favor "
                                        + "corrígalos e intente de nuevo :c \n" + "->" + auxlex + "<-\n \"" + c + "\" <---- :C",
                                        "ERROR :c", JOptionPane.WARNING_MESSAGE);
                            }
                        }

                    }

                    i -= 1;

                    break;
                //</editor-fold>

                // comentarios
                // <editor-fold>
                case 2:
                    if (c.compareTo('!') == 0) {
                        auxlex += c;
                        agregarToken(Token.Tipo.cmultilinea_abrir);
                        estado = 3;
                    }
                    break;
                case 3:
                    if (c.compareTo('!') == 0) {
                        agregarToken(Token.Tipo.dentro_comentario_multilinea);
                        i -= 1;
                    } else {
                        auxlex += c;
                    }
                    break;
                case 4:
                    if (c.equals('>')) {
                        auxlex += c;
                        agregarToken(Token.Tipo.cmultilinea_cerrar);
                    }

                    break;

                // COMENTARIOS DE UNA LINEA
                case 5:
                    if (c.equals('/')) {
                        auxlex += c;
                        agregarToken(Token.Tipo.comentario_unalinea);
                        i = -1;
                        estado = 6;
                    }
                    break;
                case 6:
                    if (c.compareTo('\n') == 0) {
                        agregarToken(Token.Tipo.dentro_comentario_unalinea);
                        i -= 1;
                    } else {
                        estado = 6;
                        auxlex += c;
                    }

                    break;
                // </editor-fold>    

                // Flecha
                // <editor-fold>
                case 7:
                    if (c.equals('>')) {
                        auxlex += c;
                        agregarToken(Token.Tipo.flecha);
                    }

                    break;

                // </editor-fold>
                // %%
                // <editor-fold>
                case 8:
                    if (c.equals('%')) {
                        auxlex += c;
                        agregarToken(Token.Tipo.doble_porcentaje);
                        porcentaje++;

                        if (porcentaje >= 2) {
                            ya_pasaron_los_porcent = true;
                        }
                    }

                    break;
                // </editor-fold>

                // cadenas
                // <editor-fold>
                case 9:

                    if (c.compareTo('"') == 0) {
                        if (eslexema) {
                            agregarToken(Token.Tipo.cadena_entrada);
                        } else if (eser) {
                            agregarToken(Token.Tipo.cadena_ER);
                        }
                        i -= 1;
                    } else {
                        estado = 9;
                        auxlex += c;
                    }
                    break;
                case 11:
                    if (c.compareTo('}') == 0) {
                        if (esconj) {
                            agregarToken(Token.Tipo.nombre_conjunto);
                        }
                        i -= 1;
                    } else {
                        estado = 9;
                        auxlex += c;
                    }

                    break;
                // </editor-fold>

                case 10:

                    if (Character.isDigit(c)) {
                        estado = 10;
                        auxlex += c;
                    } else {
                        agregarToken(Token.Tipo.numero);
                        i -= 1;
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

    public void imprimirListaTokens(ArrayList<Token> lista) {

        for (Token token : lista) {

            System.out.println(token.getTipoString() + " " + token.getValor());

        }

    }

}
