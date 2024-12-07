package handlers;

import java.util.Arrays;

import sintactico.Sintactico;

public class SintacticoVariables {
    private Sintactico sintactico;

    public SintacticoVariables(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void dimen() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba constante entera y llego", sintactico.lex);
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.lex.equals("]")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ] y llego", sintactico.lex);
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }

    public void gpoctes() {
    }

    public void varsconsts() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        while (sintactico.lex.equals(",")) {
            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
            }
            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
        }
        if (sintactico.lex.equals("[")) {
            sintactico.variablesHandler.arreglo();
            return;
        }
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lex)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", sintactico.lex);
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (sintactico.lex.equals("=")) {
            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            if (sintactico.lex.equals("{"))
                sintactico.variablesHandler.gpoctes();
            else {
                if (!Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba CtA, CtL, Ent o Dec y llego", sintactico.lex);
                }
                result = sintactico.lexico.lexico();
                sintactico.tok = result[0];
                sintactico.lex = result[1];
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
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un número entero y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar que el siguiente token sea ']'
        if (!sintactico.lex.equals("]")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ']' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar que el siguiente token sea un tipo de dato
        String tipoDato = sintactico.lex;
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(tipoDato)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un tipo de dato y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar que el siguiente token sea '{'
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

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

            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];

            if (sintactico.lex.equals(",")) {
                result = sintactico.lexico.lexico();
                sintactico.tok = result[0];
                sintactico.lex = result[1];
            } else if (!sintactico.lex.equals("}")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba ',' o '}' y llegó", sintactico.lex);
                return;
            }
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }
}
