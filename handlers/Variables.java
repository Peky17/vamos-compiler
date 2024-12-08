package handlers;

import java.util.Arrays;
import sintactico.Sintactico;
import utils.Token;

public class Variables {
    private Sintactico sintactico;

    public Variables(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    private void avanzarToken() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();
    }

    public void dimen() {
        avanzarToken();
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba constante entera y llego", sintactico.lex);
        }
        avanzarToken();
        if (!sintactico.lex.equals("]")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ] y llego", sintactico.lex);
        }
        avanzarToken();
    }

    public void gpoctes() {
    }

    public void varsconsts() {
        avanzarToken();
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
        }
        avanzarToken();
        while (sintactico.lex.equals(",")) {
            avanzarToken();
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
            }
            avanzarToken();
        }
        if (sintactico.lex.equals("[")) {
            sintactico.variablesHandler.arreglo();
            return;
        }
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lex)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", sintactico.lex);
        }
        avanzarToken();
        if (sintactico.lex.equals("=")) {
            avanzarToken();
            if (sintactico.lex.equals("{"))
                sintactico.variablesHandler.gpoctes();
            else {
                if (!Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba CtA, CtL, Ent o Dec y llego", sintactico.lex);
                }
                avanzarToken();
            }
        }
        if (sintactico.lex.equals(",")) {
            sintactico.variablesHandler.varsconsts();
        }
    }

    public void udim() {
    }

    public void arreglo() {
        avanzarToken();
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un número entero y llegó", sintactico.lex);
            return;
        }

        avanzarToken();
        if (!sintactico.lex.equals("]")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ']' y llegó", sintactico.lex);
            return;
        }

        avanzarToken();
        String tipoDato = sintactico.lex;
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(tipoDato)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un tipo de dato y llegó", sintactico.lex);
            return;
        }

        avanzarToken();
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }

        avanzarToken();
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

            avanzarToken();
            if (sintactico.lex.equals(",")) {
                avanzarToken();
            } else if (!sintactico.lex.equals("}")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba ',' o '}' y llegó", sintactico.lex);
                return;
            }
        }

        avanzarToken();
    }
}