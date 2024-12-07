package handlers;

import java.util.Arrays;

import sintactico.Sintactico;

public class SintacticoExpresiones {
    private Sintactico sintactico;

    public SintacticoExpresiones(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void expr() {
        opy();
        while (sintactico.lexico.lex.equals("o")
                || sintactico.lexico.lex.equals("||")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
            opy();
        }
    }

    public void opy() {
        opno();
        while (sintactico.lexico.lex.equals("y")
                || sintactico.lexico.lex.equals("&&")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
            opno();
        }
    }

    public void opno() {
        if (sintactico.lexico.lex.equals("no")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
        }
        oprel();
    }

    public void oprel() {
        opadd();
        while (Arrays.asList("==", "!=", "<", ">", "<=", ">=").contains(sintactico.lexico.lex)) {
            String[] result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
            opadd();
        }
    }

    public void opadd() {
        opmul();
        while (Arrays.asList("+", "-").contains(sintactico.lexico.lex)) {
            String[] result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
            opmul();
        }
    }

    public void opmul() {
        termino();
        while (Arrays.asList("*", "/", "%").contains(sintactico.lexico.lex)) {
            String[] result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
            termino();
        }
    }

    public void termino() {
        if (sintactico.lexico.lex.equals("(")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
            expr();
            if (!sintactico.lexico.lex.equals(")")) {
                sintactico.lexico.erra("Error de Sintaxis", "Se esperaba \")\" y llego", sintactico.lexico.lex);
            }
        } else if (Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.lexico.tok)
                || sintactico.lexico.tok.equals("Ide")) {
            // Do nothing
        } else {
            sintactico.lexico.erra("Error de Sintaxis", "Se esperaba un término y llegó", sintactico.lexico.lex);
        }
        String[] result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];
    }
}
