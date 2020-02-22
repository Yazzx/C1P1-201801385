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
public class ErrorLexico
    {
        int id, fila, columna;

        String descripción;
        char caracter;

        public ErrorLexico(int id, int fila, int columna, char caracter, String descripción)
        {
            this.id = id;
            this.fila = fila;
            this.columna = columna;
            this.caracter = caracter;
            this.descripción = descripción;
        }

        public int getColumna()
        {
            return columna;
        }
        public void setColumna(int nuevoo)
        {
            this.columna = nuevoo;
        }
        public int getFila()
        {
            return fila;
        }
        public void setFila(int nuevoo)
        {
            this.fila = nuevoo;
        }
        public int getId()
        {
            return id;
        }
        public void setId(int nuevoo)
        {
            this.id = nuevoo;
        }
        public String getDescripción()
        {
            return descripción;
        }

        public void setDescripción(String newName)
        {
            this.descripción = newName;
        }

        public char getCaracter()
        {
            return caracter;
        }

        public void setCaracter(char newName)
        {
            this.caracter = newName;
        }

        public String toString()
        {
            return "ObjError: { id= " + id + ", fila= " + fila + ", columna= " + columna +
                ", caracter= " + caracter + ", descripción= " + descripción + " }";
        }


    }
