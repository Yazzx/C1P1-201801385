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
public class ObjToken
    {

        int id, idtoken, fila, columna;
        String lexema, token;
        public ObjToken(int id, int fila, int columna, String lexema, int idtoken, String token)
        {
            this.id = id;
            this.fila = fila;
            this.columna = columna;
            this.lexema = lexema;
            this.idtoken = idtoken;
            this.token = token;
        }
        public int getFila()
        {
            return fila;
        }
        public int getColumna()
        {
            return columna;
        }
        public String getToken()
        {
            return token;
        }

        public void setToken(String newName)
        {
            this.token = newName;
        }

        public String getLexema()
        {
            return lexema;
        }

        public void setDescripción(String newName)
        {
            this.lexema = newName;
        }

        public int getIdToken()
        {
            return idtoken;
        }
        public void setIdToken(int nuevoo)
        {
            this.idtoken = nuevoo;
        }

        public int getId()
        {
            return id;
        }
        public void setId(int nuevoo)
        {
            this.id = nuevoo;
        }
    }