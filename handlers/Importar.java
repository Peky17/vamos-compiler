package handlers;

import sintactico.Sintactico;
import utils.Token;

public class Importar {
    private Sintactico sintactico;

    public Importar(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    private void avanzarToken() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
    }

    public void importar() {
        avanzarToken();
        if (!sintactico.tok.equals("CtA") && !sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba nombre de libreria o grupo libs y llego", sintactico.lex);
        }
        if (sintactico.lex.equals("(")) {
            avanzarToken();
            while (sintactico.tok.equals("CtA")) {
                avanzarToken();
            }
            if (!sintactico.lex.equals(")")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba \")\" y llego", sintactico.lex);
            }
        }
        avanzarToken();
    }
}