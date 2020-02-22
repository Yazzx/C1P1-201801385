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
public class Token {
    
    enum Tipo{
       
       llave_abrir,
       llave_cerrar,
       
       comentario_unalinea,
       dentro_comentario_unalinea,
       cmultilinea_abrir,
       dentro_comentario_multilinea,
       cmultilinea_cerrar,
       
       PR_CONJ,
       dos_puntos,
       
       nombre_conjunto,       
       flecha,
       letra,
       letra_mayuscula,
       limite_primer_conjunto,
       limite_ultimo_conjunto,
       numero,
       virgulilla,
       coma,
       caracter_ASCII,
       punto_y_coma, 
       
      nombre_expresion,
      punto,
      barra_vertical,
      interrogacion_cerrar,
      asterisco,
      signo_mas,
      comillas_dobles,
      cadena_ER,
      
      doble_porcentaje,
      nombre_lexema,
      cadena_entrada, 
      
      operador_unario,
      operador_binario, 
      terminal
       
      
    }
    
    private Tipo tipoToken;
    private String valor;
    
    public Token(Tipo tipoDelToken, String val){
        
        this.tipoToken = tipoDelToken;
        this.valor = val;
        
    }

    public Tipo getTipoToken() {
        return tipoToken;
        
    }

    public String getValor() {
        return valor;
    }
    
    public String getTipoString(){
        
        if (tipoToken == Tipo.asterisco) {
            return "asterisco";
        } else if (tipoToken == Tipo.barra_vertical) {
            return "barra vertical";
        } else if (tipoToken == Tipo.cadena_ER) {
            return "cadena de ER";
        } else if (tipoToken == Tipo.cadena_entrada) {
            return "cadena de entrada";
        } else if (tipoToken == Tipo.caracter_ASCII) {
            return "caracter ASCII";
        } else if (tipoToken == Tipo.cmultilinea_abrir) {
            return "comentario multilinea abrir";
        } else if (tipoToken == Tipo.cmultilinea_cerrar) {
            return "comentario multilinea cerrar";
        } else if (tipoToken == Tipo.coma) {
            return "coma";
        } else if (tipoToken == Tipo.comentario_unalinea) {
            return "comentario de una linea";
        } else if (tipoToken == Tipo.comillas_dobles) {
            return "comillas dobles";
        } else if (tipoToken == Tipo.dentro_comentario_multilinea) {
            return "cadena comentatio multilinea";
        } else if (tipoToken == Tipo.dentro_comentario_unalinea) {
            return "dentro comentario de una linea";
        } else if (tipoToken == Tipo.doble_porcentaje) {
            return "doble porcentaje";
        } else if (tipoToken == Tipo.dos_puntos) {
            return "dos puntos";
        } else if (tipoToken == Tipo.flecha) {
            return "flecha";
        } else if (tipoToken == Tipo.interrogacion_cerrar) {
            return "Signo interrogacion cerrar";
        } else if (tipoToken == Tipo.letra_mayuscula) {
            return "letra mayúscula de conjunto ";
        } else if (tipoToken == Tipo.letra) {
            return "letra de conjunto";
        } else if (tipoToken == Tipo.llave_abrir) {
            return "llave abrir";
        } else if (tipoToken == Tipo.llave_cerrar) {
            return "llave cerrar";
        } else if (tipoToken == Tipo.nombre_conjunto) {
            return "nombre de conjunto";
        } else if (tipoToken == Tipo.nombre_expresion) {
            return "nombre de expresion";
        } else if (tipoToken == Tipo.numero) {
            return "numero";
        } else if (tipoToken == Tipo.punto) {
            return "punto";
        } else if (tipoToken == Tipo.punto_y_coma) {
            return "punto y coma";
        } else if (tipoToken == Tipo.signo_mas) {
            return "signo mas";
        } else if (tipoToken == Tipo.virgulilla) {
            return "virgulilla";
        } else if (tipoToken == Tipo.PR_CONJ) {
            return "Palabra reservada: CONJ";
        }else if (tipoToken == Tipo.nombre_lexema) {
            return "nombre de lexema";
        }else if (tipoToken == Tipo.limite_primer_conjunto) {
            return "limite inicial conjunto";
        }else if (tipoToken == Tipo.limite_ultimo_conjunto) {
            return "limite final conjunto";
        } else if (tipoToken == Tipo.operador_unario) {
            return "operador unario";
        }else if (tipoToken == Tipo.operador_binario) {
            return "operador binario";
        } else if (tipoToken == Tipo.terminal) {
            return "terminal";
        } else{
            return "desconocido";
        }
        
    }
}
