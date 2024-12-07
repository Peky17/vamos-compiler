/*
 * Autor: Enrique Bernardo González Vázquez 
*/

import lexico.Lexico;
import semantico.Semantico;
import sintactico.Sintactico;

public class Main {
    public static void main(String[] args) {
        Lexico lexico = new Lexico();
        Sintactico sintactico = new Sintactico(lexico);
        Semantico semantico = new Semantico(sintactico);
        semantico.prgm();
    }
}