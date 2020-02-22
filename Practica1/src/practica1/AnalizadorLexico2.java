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
import Objetos.ObjToken;
import Objetos.ErrorLexico;

/**
 *
 * @author yasmi
 */
public class AnalizadorLexico2 {

    private LinkedList<Token> Salida;
    private int estado;
    private String auxlex;

    int contador;
    // contador para el número de dígitos

    // PARA LOS ERRORES
    int contaerror = 1;
    int contafila = 1;
    int contacolumna = 1;
    String descerror = "desconocido";

    // PARA LOS COSOS NORMALES
    int contatoken = 1;

    public LinkedList<ErrorLexico> ListaDeErrores = new LinkedList<ErrorLexico>();
    public LinkedList<ObjToken> ListaTokens = new LinkedList<ObjToken>();

    public LinkedList<ExpresionRegular> lista_er = new LinkedList<ExpresionRegular>();
    public LinkedList<Conjunto> lista_conj = new LinkedList<Conjunto>();
    public LinkedList<Lexema> lista_lex = new LinkedList<Lexema>();

    ExpresionRegular e1;
    boolean e1_usado;

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

        // ERS
        boolean llave_abierta = false;

        // conjuntos
        boolean lf_abierto = false;
        boolean ind_abierto = false;

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

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 1, "llave abrir");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.llave_abrir);

                        if (es_er) {
                            estado = 12;
                        }

                    } else if (c.compareTo('}') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 2, "llave cerrar");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.llave_cerrar);

                        if (!llave_abierta) {
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

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 3, "comillas");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.comillas_dobles);
                        if (escadena == false) {
                            escadena = true;
                            estado = 6;
                        } else if (escadena == true) {
                            escadena = false;
                        }

                    } else if (c.compareTo(';') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 4, "punto y coma");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.punto_y_coma);
                        es_conj = false;
                        es_er = false;
                        ya_CONJ = false;
                        if (ya_porcentajes) {
                            es_lex = true;
                        }

                        // metiendo a lista
                        if (e1_usado) {
                            lista_er.add(e1);
                        }

                    } else if (c.compareTo(':') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 5, "dos puntos");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.dos_puntos);
                        if (ya_CONJ) {
                            id_conj = true;
                        } else {
                            id_lex = true;
                        }

                    } else if (c.compareTo('\n') == 0 || c.compareTo('\r') == 0) {
                        auxlex = "";
                        estado = 0;
                        contafila++;
                        contacolumna = 0;

                    } else if (c.compareTo('\t') == 0) {
                        auxlex = "";
                        estado = 0;
                    } else if (c.compareTo(' ') == 0) {
                        auxlex = "";
                        estado = 0;
                        contacolumna++;
                    } else if (c.compareTo('!') == 0) {
                        auxlex += c;
                        estado = 5;
                    } else if (c.compareTo('-') == 0) {
                        auxlex += c;
                        estado = 9;
                    } else if (c.compareTo('/') == 0) {
                        auxlex += c;
                        estado = 10;
                    } else if (c.compareTo('%') == 0) {
                        auxlex += c;
                        estado = 13;
                    } else if (c.compareTo('~') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 6, "virgulilla");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.virgulilla);

                    } else if (c.compareTo(',') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 7, "coma");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.coma);

                    } else if (c.compareTo('~') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 6, "virgulilla");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.virgulilla);

                    } else if (c.compareTo('.') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 8, "punto");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        Token o1 = new Token(Token.Tipo.operador_binario, auxlex);
                        e1.cosas_preorder.add(o1);
                        agregarToken(Token.Tipo.operador_binario);

                    } else if (c.compareTo('|') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 9, "barra or");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        e1.cosas_preorder.add(new Token(Token.Tipo.operador_binario, auxlex));
                        agregarToken(Token.Tipo.operador_binario);

                    } else if (c.compareTo('?') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 10, "signo interrogación");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        e1.cosas_preorder.add(new Token(Token.Tipo.operador_unario, auxlex));
                        agregarToken(Token.Tipo.operador_unario);

                    } else if (c.compareTo('*') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 11, "asterisco");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        e1.cosas_preorder.add(new Token(Token.Tipo.operador_unario, auxlex));
                        agregarToken(Token.Tipo.operador_unario);

                    } else if (c.compareTo('+') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 12, "signo mas");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        e1.cosas_preorder.add(new Token(Token.Tipo.operador_unario, auxlex));
                        agregarToken(Token.Tipo.operador_unario);

                    } else {
                        if (c.compareTo('#') == 0 && i == entrada.length() - 1) {
                            System.out.println("Hemos concluido :D");
                        } else {
                            //System.out.println("error lexico con: " + c + " :c");
                            JOptionPane.showMessageDialog(null, "Error lexico con: " + c + " :c",
                                    "ERROR :c", JOptionPane.WARNING_MESSAGE);

                            ErrorLexico e1 = new ErrorLexico(contaerror, contafila, contacolumna, c, descerror);
                            ListaDeErrores.add(e1);
                            contacolumna++;
                            estado = 0;
                            auxlex = "";
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

                    } else if (c.compareTo('_') == 0) {
                        estado = 1;
                        auxlex += c;

                        // evaluaciones de que tipo de id_son
                        // da igual, es lo mismo xd
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
                    } else if (c.compareTo(',') == 0) {

                        estado = 14;

                        i -= 1;
                    } else if (c.compareTo('~') == 0) {

                        if (es_conj) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 13, "letra");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;

                            agregarToken(Token.Tipo.limite_primer_conjunto);
                            lf_abierto = true;
                            i -= 1;
                        }
                    } else if (c.compareTo(';') == 0) {

                        if (lf_abierto) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 13, "letra");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;

                            agregarToken(Token.Tipo.limite_ultimo_conjunto);
                            lf_abierto = true;
                            i -= 1;
                        } else if (ind_abierto) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 29, "letra de conjunto");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;
                            agregarToken(Token.Tipo.letra);

                            i -= 1;

                        }

                    } else {
                        //Console.WriteLine("error lexico con: " + c + " :c");

                        JOptionPane.showMessageDialog(null, "Error lexico con: " + c + " :c",
                                "ERROR :c", JOptionPane.WARNING_MESSAGE);
                        ErrorLexico e1 = new ErrorLexico(contaerror, contafila, contacolumna, c, descerror);
                        ListaDeErrores.add(e1);
                        contacolumna++;
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

                    } else if (c.compareTo('~') == 0) {

                        if (es_conj) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 14, "numero");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;

                            agregarToken(Token.Tipo.limite_primer_conjunto);
                            lf_abierto = true;
                            i -= 1;
                        }
                    } else if (c.compareTo(',') == 0) {

                        estado = 15;

                        i -= 1;
                    } else if (c.compareTo(';') == 0) {

                        if (lf_abierto) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 14, "numero");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;

                            agregarToken(Token.Tipo.limite_ultimo_conjunto);
                            lf_abierto = true;
                            i -= 1;
                        } else if (ind_abierto) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 30, "numero de conjunto");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;

                            agregarToken(Token.Tipo.numero);

                            i -= 1;

                        }

                    } else {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 14, "numero");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.numero);
                        i -= 1;
                    }
                    break;
                case 3:
                    if (c.compareTo('!') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 15, "c_multilinea abrir");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.cmultilinea_abrir);
                        estado = 4;
                    }

                    break;
                case 4:
                    if (c.compareTo('!') == 0) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 16, "dentro comentario multilinea");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.dentro_comentario_multilinea);
                        estado = 5;
                        i -= 1;
                    } else {
                        auxlex += c;
                    }
                    break;
                case 5:
                    if (c.equals('>')) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 17, "c_multilinea cerrar");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.cmultilinea_cerrar);
                    }
                    break;
                case 6:
                    if (c.compareTo('"') == 0) {
                        if (es_lex) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 18, "cadena de entrada lexica");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;

                            agregarToken(Token.Tipo.cadena_entrada);
                        } else if (es_er) {

                            ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 19, "cadena en ER");
                            ListaTokens.add(o21);
                            contatoken++;
                            contacolumna++;

                            e1.cosas_preorder.add(new Token(Token.Tipo.terminal, auxlex));
                            agregarToken(Token.Tipo.terminal);
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

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 20, "PR_CONJ");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.PR_CONJ);
                        // esto en lo general
                        ya_CONJ = true;
                        es_conj = true;

                        i -= 1;
                    } else if (es_lex) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 21, "nombre lexema");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

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

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 21, "nombre lexema");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.nombre_lexema);
                        i -= 1;
                    } else if (es_conj) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 22, "nombre conjunto");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.nombre_conjunto);
                    } else {
                        e1_usado = true;
                        e1 = new ExpresionRegular(auxlex);
                        es_er = true;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 23, "nombre ER");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.nombre_expresion);
                    }
                    i -= 1;
                    break;
                case 9:
                    if (c.compareTo('>') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 24, "flecha");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.flecha);
                    }
                    break;
                case 10:
                    if (c.compareTo('/') == 0) {
                        auxlex += c;

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 25, "inicio comentario una linea");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.comentario_unalinea);
                        estado = 11;
                    }
                    break;
                case 11:
                    if (c.compareTo('\n') == 0) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 26, "dentro comentario una linea");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.dentro_comentario_unalinea);
                        estado = 0;
                        i -= 1;
                    } else {
                        auxlex += c;
                    }
                    break;
                case 12:
                    if (c.compareTo('}') == 0) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 27, "nombre conjunto");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        e1.cosas_preorder.add(new Token(Token.Tipo.terminal, auxlex));
                        agregarToken(Token.Tipo.terminal);
                        i -= 1;
                    } else {
                        auxlex += c;
                    }
                    break;
                case 13:
                    if (c.compareTo('%') == 0) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 28, "doble porcentaje");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.doble_porcentaje);
                        contaporcentajes++;
                        if (contaporcentajes >= 2) {
                            ya_porcentajes = true;
                            es_lex = true;
                        }

                    }
                    break;

                case 14:

                    if (c.compareTo(',') == 0) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 29, "letra de conjunto");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.letra);

                        i -= 1;
                    } else if (Character.isLetter(c)) {
                        auxlex += c;
                    }

                    break;
                case 15:

                    if (c.compareTo(',') == 0) {

                        ObjToken o21 = new ObjToken(contatoken, contafila, contacolumna, auxlex, 30, "numero de conjunto");
                        ListaTokens.add(o21);
                        contatoken++;
                        contacolumna++;

                        agregarToken(Token.Tipo.numero);

                        i -= 1;
                    } else if (Character.isLetter(c)) {
                        auxlex += c;
                    }

                    break;
            }
        }

        imprimirListaER(lista_er);

        return Salida;
    }

    public void agregarToken(Token.Tipo tipo) {
        Salida.add(new Token(tipo, auxlex));
        auxlex = "";
        estado = 0;
    }

    public void imprimirListaTokens(LinkedList<Token> lista) {

        for (Token token : lista) {

            System.out.println(token.getValor() + " - " + token.getTipoString());

        }
    }

    public void imprimirListaER(LinkedList<ExpresionRegular> lista) {

        for (ExpresionRegular expresion : lista) {

            System.out.println(expresion.toString() + "\n");

        }
    }

    public void generarArbolGraphviz(LinkedList<ExpresionRegular> lista) {

    }

}
