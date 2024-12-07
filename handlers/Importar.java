package handlers;

import sintactico.Sintactico;

public class Importar {
    private Sintactico sintactico;

    public Importar(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void importar() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.tok.equals("CtA") && !sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba nombre de libreria o grupo libs y llego", sintactico.lex);
        }
        if (sintactico.lex.equals("(")) {
            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            while (sintactico.tok.equals("CtA")) {
                result = sintactico.lexico.lexico();
                sintactico.tok = result[0];
                sintactico.lex = result[1];
            }
            if (!sintactico.lex.equals(")")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba \")\" y llego", sintactico.lex);
            }
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }
}
