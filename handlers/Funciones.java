package handlers;

import java.util.Arrays;
import sintactico.Sintactico;
import utils.Token;

public class Funciones {
    private Sintactico sintactico;

    public Funciones(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    private void avanzarToken() {
        Token token = sintactico.lexico.getTokenManager().nextToken();
        if (token != null && token.getTok() != null && token.getLex() != null) {
            sintactico.tok = token.getTok();
            sintactico.lex = token.getLex();
        } else {
            // Obtener la posición y el ID del token nulo
            int id = token != null ? token.getId() : -1;
            sintactico.erra("Error de Sintaxis", "Token o lexema nulo", "ID: " + id);
        }
    }

    public void pars() {
        avanzarToken();
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba identificador y llego", sintactico.lex);
            return;
        }
        String nomIde = sintactico.lex;
        avanzarToken();
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lex)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", sintactico.lex);
            return;
        }
        String tipo = "";
        switch (sintactico.lex) {
            case "alfabetico":
                tipo = "A";
                break;
            case "logico":
                tipo = "L";
                break;
            case "entero":
                tipo = "E";
                break;
            case "decimal":
                tipo = "D";
                break;
        }
        sintactico.regtabSim(nomIde, new String[] { "P", tipo, "0", "0" }); // Registrar parámetro en la tabla de
                                                                            // símbolos
        avanzarToken();
        if (sintactico.lex.equals(",")) {
            sintactico.funcionesHandler.pars();
        }
    }

    public void funciones() {
        avanzarToken();

        // Verificar nombre de la función (excepto para 'principal')
        if (!sintactico.lex.equals("principal") && !sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba nombre de función y llegó", sintactico.lex);
            return;
        }

        avanzarToken();

        // Verificar apertura de paréntesis para los parámetros
        if (!sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '(' y llegó", sintactico.lex);
            return;
        }

        avanzarToken();

        // Procesar parámetros de la función, si los hay
        if (!sintactico.lex.equals(")")) {
            while (true) {
                // Verificar que el parámetro tenga un identificador
                if (!sintactico.tok.equals("Ide")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba identificador y llegó", sintactico.lex);
                    return;
                }

                String nomIde = sintactico.lex;
                avanzarToken();

                // Verificar que el identificador vaya seguido de un tipo
                if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lex)) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba un tipo de dato y llegó", sintactico.lex);
                    return;
                }

                String tipo = "";
                switch (sintactico.lex) {
                    case "alfabetico":
                        tipo = "A";
                        break;
                    case "logico":
                        tipo = "L";
                        break;
                    case "entero":
                        tipo = "E";
                        break;
                    case "decimal":
                        tipo = "D";
                        break;
                }
                sintactico.regtabSim(nomIde, new String[] { "P", tipo, "0", "0" }); // Registrar parámetro en la tabla
                                                                                    // de símbolos

                avanzarToken();

                // Comprobar si hay más parámetros o cerrar paréntesis
                if (sintactico.lex.equals(")")) {
                    break; // Fin de la lista de parámetros
                } else if (!sintactico.lex.equals(",")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ',' o ')' y llegó", sintactico.lex);
                    return;
                }

                avanzarToken();
            }
        }

        // Verificar cierre de paréntesis de parámetros
        if (!sintactico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ')' y llegó", sintactico.lex);
            return;
        }

        avanzarToken();

        // Verificar tipo de retorno opcional
        if (Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lex)) {
            avanzarToken();
        }

        // Verificar apertura de bloque de la función
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        } else {
            // Llamada para procesar el bloque de la función
            sintactico.comandosHandler.bloque();
        }

        avanzarToken();

        // Verificar si hay más funciones
        if (sintactico.lex.equals("funcion")) {
            sintactico.funcionesHandler.funciones();
        }
    }

    public void llamadaFuncion() {
        avanzarToken();

        // Verificar apertura de paréntesis
        if (!sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '(' y llegó", sintactico.lex);
            return;
        }

        avanzarToken();

        // Procesar parámetros de la función, si los hay
        if (!sintactico.lex.equals(")")) {
            while (true) {
                // Procesar cada parámetro como una expresión
                sintactico.expresionesHandler.expr();
                // Comprobar si hay más parámetros o cerrar paréntesis
                if (sintactico.lex.equals(")")) {
                    break; // Fin de la lista de parámetros
                } else if (!sintactico.lex.equals(",")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ',' o ')' y llegó", sintactico.lex);
                    return;
                }

                avanzarToken();
            }
        }

        // Verificar cierre de paréntesis
        if (!sintactico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ')' y llegó", sintactico.lex);
            return;
        }

        avanzarToken();
    }
}