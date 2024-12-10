package handlers;

import java.util.Arrays;
import java.util.Stack;
import sintactico.Sintactico;
import utils.Token;
import utils.TipoUtils;

public class Expresiones {
    private Sintactico sintactico;
    private Stack<String> pTipos;

    public Expresiones(Sintactico sintactico) {
        this.sintactico = sintactico;
        this.pTipos = new Stack<>();
    }

    public String expr() {
        opy();
        while (sintactico.lex.equals("o") || sintactico.lex.equals("||")) {
            nextToken();
            opy();
        }
        return getTipoExpresionActual();
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
            String op = sintactico.lex;
            nextToken();
            opmul();
            validarTipos(op);
        }
    }

    public void opmul() {
        termino();
        while (Arrays.asList("*", "/", "%").contains(sintactico.lex)) {
            String op = sintactico.lex;
            nextToken();
            termino();
            validarTipos(op);
        }
    }

    public void termino() {
        if (sintactico.lex.equals("(") || sintactico.lex.equals("[")) {
            String openingBracket = sintactico.lex;
            String closingBracket = openingBracket.equals("(") ? ")" : "]";
            nextToken();
            expr();
            if (!sintactico.lex.equals(closingBracket)) {
                sintactico.erra("Error de Sintaxis", "Se esperaba \"" + closingBracket + "\" y llegó", sintactico.lex);
            }
            nextToken();
        } else if (Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)) {
            // Añadir el tipo correspondiente a la pila de tipos
            switch (sintactico.tok) {
                case "CtA":
                    pTipos.push("A");
                    break;
                case "CtL":
                    pTipos.push("L");
                    break;
                case "Dec":
                    pTipos.push("D");
                    break;
                case "Ent":
                    pTipos.push("E");
                    break;
            }
            nextToken();
        } else if (sintactico.tok.equals("Ide")) {
            String nomIde = sintactico.lex;
            nextToken();
            if (sintactico.lex.equals("(")) {
                // Es una llamada a función
                retrocederToken();
                sintactico.funcionesHandler.llamadaFuncion();
                String tipoRetorno = sintactico.funcionesHandler.getTipoRetornoActual();
                pTipos.push(tipoRetorno);
            } else {
                // Es una variable
                retrocederToken();
                String[] data = sintactico.leetabSim(nomIde);
                if (data.length == 0) {
                    sintactico.erra("Error de Semantica", "Identificador NO declarado y llego", nomIde);
                } else {
                    String tipo = data[1];
                    pTipos.push(tipo);
                    nextToken();
                }
            }
        } else {
            sintactico.erra("Error de Sintaxis", "Se esperaba un término y llegó", sintactico.lex);
            nextToken();
        }
    }

    private void validarTipos(String op) {
        if (pTipos.size() < 2) {
            sintactico.erra("Error de Semantica", "No hay suficientes operandos para la operación", op);
            return;
        }

        String tipoDerecho = pTipos.pop();
        String tipoIzquierdo = pTipos.pop();
        String key = tipoIzquierdo + op + tipoDerecho;

        if (!TipoUtils.tiposTab.containsKey(key)) {
            sintactico.erra("Error de Semantica", "Conflicto en el tipo de operador", key);
            pTipos.push("I"); // Tipo indefinido en caso de error
        } else {
            String tipoResultado = TipoUtils.tiposTab.get(key);
            if (!tipoResultado.isEmpty()) {
                pTipos.push(tipoResultado);
            }
        }
    }

    public String getTipoExpresionActual() {
        if (pTipos.isEmpty()) {
            return "";
        }
        return pTipos.peek();
    }

    private void nextToken() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
    }

    private void retrocederToken() {
        Token token = sintactico.lexico.getTokenManager().previousToken();
        if (token != null) {
            sintactico.tok = token.getTok();
            sintactico.lex = token.getLex();
        }
    }
}