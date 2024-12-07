package handlers;

import java.util.Arrays;

import sintactico.Sintactico;

public class Expresiones {
    private Sintactico sintactico;

    public Expresiones(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void expr() {
        opy();
        while (sintactico.lex.equals("o")
                || sintactico.lex.equals("||")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            opy();
        }
    }

    public void opy() {
        opno();
        while (sintactico.lex.equals("y")
                || sintactico.lex.equals("&&")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            opno();
        }
    }

    public void opno() {
        if (sintactico.lex.equals("no")
                || sintactico.lex.equals("!")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
        }
        oprel();
    }

    public void oprel() {
        opadd();
        while (Arrays.asList("==", "!=", "<", ">", "<=", ">=").contains(sintactico.lex)) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            opadd();
        }
    }

    public void opadd() {
        opmul();
        while (Arrays.asList("+", "-").contains(sintactico.lex)) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            opmul();
        }
    }

    public void opmul() {
        termino();
        while (Arrays.asList("*", "/", "%").contains(sintactico.lex)) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            termino();
        }
    }

    public void termino() {
        if (sintactico.lexico.lex.equals("(")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            expr();
            if (!sintactico.lex.equals(")")) {
                sintactico.erra("Error de Sintaxis",
                        "Se esperaba \")\" y llego", sintactico.lex);
            }
        } else if (Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)
                || sintactico.tok.equals("Ide")) {
            // Do nothing
        } else {
            sintactico.lexico.erra("Error de Sintaxis",
                    "Se esperaba un término y llegó", sintactico.lex);
        }
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }
}
