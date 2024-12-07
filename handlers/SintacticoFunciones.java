package handlers;

import java.util.Arrays;
import sintactico.Sintactico;

public class SintacticoFunciones {
    private Sintactico sintactico;

    public SintacticoFunciones(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void pars() {
        String[] result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];
        if (!sintactico.lexico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba identificador y llego", sintactico.lexico.lex);
            return;
        }
        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lexico.lex)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", sintactico.lexico.lex);
            return;
        }
        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];
        if (sintactico.lexico.lex.equals(",")) {
            sintactico.funcionesHandler.pars();
        }
    }

    public void funciones() {
        String[] result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];

        // Verificar nombre de la función (excepto para 'principal')
        if (!sintactico.lexico.lex.equals("principal") && !sintactico.lexico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba nombre de función y llegó", sintactico.lexico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];

        // Verificar apertura de paréntesis para los parámetros
        if (!sintactico.lexico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '(' y llegó", sintactico.lexico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];

        // Procesar parámetros de la función, si los hay
        if (!sintactico.lexico.lex.equals(")")) {
            while (true) {
                // Verificar que el parámetro tenga un identificador
                if (!sintactico.lexico.tok.equals("Ide")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba identificador y llegó", sintactico.lexico.lex);
                    return;
                }

                result = sintactico.lexico.lexico();
                sintactico.lexico.tok = result[0];
                sintactico.lexico.lex = result[1];

                // Verificar que el identificador vaya seguido de un tipo
                if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lexico.lex)) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba un tipo de dato y llegó", sintactico.lexico.lex);
                    return;
                }

                // Avanzar al siguiente token
                result = sintactico.lexico.lexico();
                sintactico.lexico.tok = result[0];
                sintactico.lexico.lex = result[1];

                // Comprobar si hay más parámetros o cerrar paréntesis
                if (sintactico.lexico.lex.equals(")")) {
                    break; // Fin de la lista de parámetros
                } else if (!sintactico.lexico.lex.equals(",")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ',' o ')' y llegó", sintactico.lexico.lex);
                    return;
                }

                // Avanzar al siguiente parámetro después de la coma
                result = sintactico.lexico.lexico();
                sintactico.lexico.tok = result[0];
                sintactico.lexico.lex = result[1];
            }
        }

        // Verificar cierre de paréntesis de parámetros
        if (!sintactico.lexico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ')' y llegó", sintactico.lexico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];

        // Verificar tipo de retorno opcional
        if (Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lexico.lex)) {
            result = sintactico.lexico.lexico();
            sintactico.lexico.tok = result[0];
            sintactico.lexico.lex = result[1];
        }

        // Verificar apertura de bloque de la función
        if (!sintactico.lexico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lexico.lex);
            return;
        } else {
            sintactico.comandosHandler.bloque(); // Llamada para procesar el bloque de la función
        }

        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];

        // Verificar si hay más funciones
        if (sintactico.lexico.lex.equals("funcion")) {
            sintactico.funcionesHandler.funciones();
        }
    }

    public void llamadaFuncion() {
        String[] result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];

        // Verificar apertura de paréntesis
        if (!sintactico.lexico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '(' y llegó", sintactico.lexico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];

        // Procesar parámetros de la función, si los hay
        if (!sintactico.lexico.lex.equals(")")) {
            while (true) {
                sintactico.expresionesHandler.expr(); // Procesar cada parámetro como una expresión

                // Comprobar si hay más parámetros o cerrar paréntesis
                if (sintactico.lexico.lex.equals(")")) {
                    break; // Fin de la lista de parámetros
                } else if (!sintactico.lexico.lex.equals(",")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ',' o ')' y llegó", sintactico.lexico.lex);
                    return;
                }

                // Avanzar al siguiente parámetro después de la coma
                result = sintactico.lexico.lexico();
                sintactico.lexico.tok = result[0];
                sintactico.lexico.lex = result[1];
            }
        }

        // Verificar cierre de paréntesis
        if (!sintactico.lexico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ')' y llegó", sintactico.lexico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];
    }
}