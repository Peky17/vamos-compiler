package handlers;

import java.util.Arrays;
import sintactico.Sintactico;
import utils.Token;

public class Expresiones {
    private Sintactico sintactico;

    public Expresiones(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void expr() {
        opy();
        while (sintactico.lex.equals("o") || sintactico.lex.equals("||")) {
            nextToken();
            opy();
        }
    }

    public void opy() {
        opno();
        while (sintactico.lex.equals("y") || sintactico.lex.equals("&&")) {
            nextToken();
            opno();
        }
    }

    public void opno() {
        if (sintactico.lex.equals("no") || sintactico.lex.equals("!")) {
            nextToken();
        }
        oprel();
    }

    public void oprel() {
        opadd();
        while (Arrays.asList("==", "!=", "<", ">", "<=", ">=").contains(sintactico.lex)) {
            nextToken();
            opadd();
        }
    }

    public void opadd() {
        opmul();
        while (Arrays.asList("+", "-").contains(sintactico.lex)) {
            nextToken();
            opmul();
        }
    }

    public void opmul() {
        termino();
        while (Arrays.asList("*", "/", "%").contains(sintactico.lex)) {
            nextToken();
            termino();
        }
    }

    public void termino() {
        if (sintactico.lex.equals("(")) {
            nextToken();
            expr();
            if (!sintactico.lex.equals(")")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba \")\" y llegó", sintactico.lex);
            }
        } else if (Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok) || sintactico.tok.equals("Ide")) {
            // Do nothing
        } else {
            sintactico.erra("Error de Sintaxis", "Se esperaba un término y llegó", sintactico.lex);
        }
        nextToken();
    }

    private void nextToken() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
    }
}