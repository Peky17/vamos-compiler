package handlers;

import sintactico.Sintactico;
import utils.Token;

public class Importar {
    private Sintactico sintactico;

    public Importar(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void importar() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
        if (!sintactico.tok.equals("CtA") && !sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba nombre de libreria o grupo libs y llego", sintactico.lex);
        }
        if (sintactico.lex.equals("(")) {
            token = sintactico.lexico.getTokenManager().nextToken();
            sintactico.tok = token.getTok();
            sintactico.lex = token.getLex();
            while (sintactico.tok.equals("CtA")) {
                token = sintactico.lexico.getTokenManager().nextToken();
                sintactico.tok = token.getTok();
                sintactico.lex = token.getLex();
            }
            if (!sintactico.lex.equals(")")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba \")\" y llego", sintactico.lex);
            }
        }
        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
    }
}