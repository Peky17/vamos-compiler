package handlers;

import java.util.Arrays;
import sintactico.Sintactico;
import utils.Token;

public class Variables {
    private Sintactico sintactico;

    public Variables(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void dimen() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba constante entera y llego", sintactico.lex);
        }
        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
        if (!sintactico.lex.equals("]")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ] y llego", sintactico.lex);
        }
        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
    }

    public void gpoctes() {
    }

    public void varsconsts() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
        }
        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
        while (sintactico.lex.equals(",")) {
            token = sintactico.lexico.getTokenManager().nextToken();
            sintactico.tok = token.getTok();
            sintactico.lex = token.getLex();
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
            }
            token = sintactico.lexico.getTokenManager().nextToken();
            sintactico.tok = token.getTok();
            sintactico.lex = token.getLex();
        }
        if (sintactico.lex.equals("[")) {
            sintactico.variablesHandler.arreglo();
            return;
        }
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lex)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", sintactico.lex);
        }
        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
        if (sintactico.lex.equals("=")) {
            token = sintactico.lexico.getTokenManager().nextToken();
            sintactico.tok = token.getTok();
            sintactico.lex = token.getLex();
            if (sintactico.lex.equals("{"))
                sintactico.variablesHandler.gpoctes();
            else {
                if (!Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba CtA, CtL, Ent o Dec y llego", sintactico.lex);
                }
                token = sintactico.lexico.getTokenManager().nextToken();
                sintactico.tok = token.getTok();
                sintactico.lex = token.getLex();
            }
        }
        if (sintactico.lex.equals(",")) {
            sintactico.variablesHandler.varsconsts();
        }
    }

    public void udim() {
    }

    public void arreglo() {
        // Verificar que el siguiente token sea un número entero
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un número entero y llegó", sintactico.lex);
            return;
        }

        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();

        // Verificar que el siguiente token sea ']'
        if (!sintactico.lex.equals("]")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ']' y llegó", sintactico.lex);
            return;
        }

        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();

        // Verificar que el siguiente token sea un tipo de dato
        String tipoDato = sintactico.lex;
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(tipoDato)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un tipo de dato y llegó", sintactico.lex);
            return;
        }

        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();

        // Verificar que el siguiente token sea '{'
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }

        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();

        // Verificar que los elementos dentro de '{}' sean del tipo de dato declarado
        while (!sintactico.lex.equals("}")) {
            boolean tipoValido = false;
            switch (tipoDato) {
                case "alfabetico":
                    tipoValido = sintactico.tok.equals("CtA");
                    break;
                case "decimal":
                    tipoValido = sintactico.tok.equals("Dec");
                    break;
                case "entero":
                    tipoValido = sintactico.tok.equals("Ent");
                    break;
                case "logico":
                    tipoValido = sintactico.tok.equals("CtL");
                    break;
            }

            if (!tipoValido) {
                sintactico.erra("Error de Sintaxis", "Se esperaba un valor del tipo de dato " + tipoDato + " y llegó",
                        sintactico.lex);
                return;
            }

            token = sintactico.lexico.getTokenManager().nextToken();
            sintactico.tok = token.getTok();
            sintactico.lex = token.getLex();

            if (sintactico.lex.equals(",")) {
                token = sintactico.lexico.getTokenManager().nextToken();
                sintactico.tok = token.getTok();
                sintactico.lex = token.getLex();
            } else if (!sintactico.lex.equals("}")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba ',' o '}' y llegó", sintactico.lex);
                return;
            }
        }

        token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
    }
}