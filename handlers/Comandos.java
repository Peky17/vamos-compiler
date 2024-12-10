package handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import sintactico.Sintactico;
import utils.TipoUtils;
import utils.Token;

public class Comandos {
    private Sintactico sintactico;
    private Stack<String> controlStructures;

    private boolean regresaPresente = false;

    public Comandos(Sintactico sintactico) {
        this.sintactico = sintactico;
        this.controlStructures = new Stack<>();
    }

    private void avanzarToken() {
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

    public void comando() {
        if (sintactico.lex.equals("fmt")) {
            avanzarToken();
            if (!sintactico.lex.equals(".")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba '.' y llegó", sintactico.lex);
                return;
            }
            avanzarToken();
            if (sintactico.lex.equals("Imprime") || sintactico.lex.equals("Imprimenl")) {
                fmtimprime();
            } else if (sintactico.lex.equals("Leer")) {
                fmtleer();
            } else {
                sintactico.erra("Error de Sintaxis",
                        "Se esperaba una función de la biblioteca 'fmt' [Leer, Imprime o Imprimenl] y llegó",
                        sintactico.lex);
            }
        } else if (sintactico.lex.equals("regresa")) {
            regresaPresente = true;
            avanzarToken();
            String tipoRetorno = sintactico.funcionesHandler.getTipoRetornoActual();
            String tipoExpresion = sintactico.expresionesHandler.expr();
            if (!tipoRetorno.isEmpty()) {
                String key = tipoRetorno + "=" + tipoExpresion;
                if (!TipoUtils.tiposTab.containsKey(key)) {
                    sintactico.erra("Error de Semantica", "Tipo de retorno no coincide con tipo de la función", key);
                }
            }
        } else if (sintactico.lex.equals("si")) {
            siSino();
        } else if (sintactico.lex.equals("desde")) {
            controlStructures.push("desde");
            desde();
            controlStructures.pop();
        } else if (sintactico.lex.equals("segun")) {
            controlStructures.push("segun");
            segun();
            controlStructures.pop();
        } else if (sintactico.lex.equals("interrumpe")) {
            interrumpe();
        } else if (sintactico.lex.equals("predeterminado")) {
            predeterminado();
        } else if (sintactico.tok.equals("Ide")) {
            String varName = sintactico.lex;
            avanzarToken();
            if (sintactico.lex.equals(",")) {
                retrocederToken();
                if (isArreglo(varName)) {
                    intercambioArreglos();
                } else {
                    intercambioVariables();
                }
            } else if (sintactico.lex.equals("(")) {
                retrocederToken();
                sintactico.funcionesHandler.llamadaFuncion();
            } else if (sintactico.lex.equals("=") || sintactico.lex.equals("[")) {
                retrocederToken();
                asignacion(varName);
            } else {
                retrocederToken();
                sintactico.erra("Error de Sintaxis", "Se esperaba asignación '=' o llamada de función '('",
                        sintactico.lex);
            }
        } else {
            sintactico.erra("Error de Sintaxis", "Comando no reconocido", sintactico.lex);
        }
    }

    public void estatutos() {
        while (!sintactico.lex.equals("}")) {
            comando();
        }
    }

    public void bloque() {
        avanzarToken();
        if (!sintactico.lex.equals("}")) {
            estatutos();
        }
    }

    public void siSino() {
        avanzarToken();
        sintactico.expresionesHandler.expr();
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }
        bloque();
        avanzarToken();
        if (sintactico.lex.equals("sino")) {
            avanzarToken();
            if (!sintactico.lex.equals("{")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
                return;
            }
            bloque();
            avanzarToken();
        }
    }

    public void desde() {
        avanzarToken();
        if (sintactico.lex.equals("{")) {
            desdeSimple();
            return;
        }
        // El identificador debe estar registrado en la tabla de símbolos
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un identificador para la inicialización y llegó",
                    sintactico.lex);
            return;
        }
        String iterador = sintactico.lex;
        if (sintactico.leetabSim(iterador).length == 0) {
            sintactico.erra("Error de Semantica", "Identificador no declarado", iterador);
            return;
        }
        avanzarToken();
        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        // Se debe permitir asignacion de un entero, una variable o constante entera, o
        // una expresión aritmética
        String tipoExpresion = sintactico.expresionesHandler.expr();
        if (!tipoExpresion.equals("E")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un valor entero para la inicialización y llegó",
                    sintactico.lex);
            return;
        }
        if (!sintactico.lex.equals(";")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ';' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        sintactico.expresionesHandler.expr();
        if (!sintactico.lex.equals(";")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ';' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un identificador para el incremento y llegó",
                    sintactico.lex);
            return;
        }
        avanzarToken();
        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        sintactico.expresionesHandler.expr();
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }
        bloque();
        avanzarToken();
    }

    public void desdeSimple() {
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }
        bloque();
        avanzarToken();
    }

    public void segun() {
        avanzarToken();
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba identificador y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        while (!sintactico.lex.equals("}")) {
            if (sintactico.lex.equals("caso")) {
                avanzarToken();
                if (!Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba una constante válida y llegó", sintactico.lex);
                    return;
                }
                avanzarToken();
                if (!sintactico.lex.equals(":")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ':' y llegó", sintactico.lex);
                    return;
                }
                avanzarToken();
                while (!sintactico.lex.equals("}") && !sintactico.lex.equals("caso")
                        && !sintactico.lex.equals("predeterminado") && !sintactico.lex.equals("interrumpe")) {
                    comando();
                    if (!sintactico.lex.equals("interrumpe")) {
                        interrumpe();
                    }
                }
                if (sintactico.lex.equals("interrumpe")) {
                    interrumpe();
                }
            } else if (sintactico.lex.equals("predeterminado")) {
                predeterminado();
            } else {
                sintactico.erra("Error de Sintaxis", "Se esperaba 'caso' o 'predeterminado' y llegó", sintactico.lex);
                return;
            }
        }
        if (!sintactico.lex.equals("}")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '}' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
    }

    public void predeterminado() {
        avanzarToken();
        if (!sintactico.lex.equals(":")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ':' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        while (!sintactico.lex.equals("}") && !sintactico.lex.equals("caso")
                && !sintactico.lex.equals("predeterminado")) {
            comando();
        }
    }

    public void interrumpe() {
        if (controlStructures.isEmpty()) {
            sintactico.erra("Error de Sintaxis", 
            "La sentencia 'interrumpe' debe estar dentro de 'segun' o 'desde'",
                    sintactico.lex);
        }
        avanzarToken();
    }

    public void fmtleer() {
        avanzarToken();
        if (!sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '(' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        if (sintactico.lex.equals("[")) {
            sintactico.variablesHandler.udim();
            avanzarToken();
        }
        if (!sintactico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ')' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
    }

    public void fmtimprime() {
        avanzarToken();
        if (!sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '(' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        String sp = ",";
        while (sp.equals(",")) {
            sp = "";
            sintactico.expresionesHandler.expr();
            sp = sintactico.lex;
            if (sp.equals(",")) {
                avanzarToken();
            }
        }
        if (!sintactico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba ')' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
    }

    private void asignacion(String varName) {
        avanzarToken();
        if (sintactico.lex.equals("[")) {
            // Es una asignación a un arreglo
            avanzarToken();
            sintactico.expresionesHandler.expr(); // Validar índice del arreglo
            if (!sintactico.lex.equals("]")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba ']' y llegó", sintactico.lex);
                return;
            }
            avanzarToken();
            if (!sintactico.lex.equals("=") && !sintactico.lex.equals(",")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba '=' y llegó", sintactico.lex);
                return;
            }

            if (sintactico.lex.equals(",")) {
                // intercambio de arreglos
                intercambioArreglos();
            }

            avanzarToken();
            sintactico.expresionesHandler.expr(); // Validar expresión de asignación
        } else if (sintactico.lex.equals("=")) {
            // Es una asignación a una variable no dimensionada
            avanzarToken();
            sintactico.expresionesHandler.expr(); // Validar expresión de asignación
        } else {
            sintactico.erra("Error de Sintaxis", "Se esperaba '=' o '[' y llegó", sintactico.lex);
            return;
        }
    }

    private void intercambioVariables() {
        List<String> variablesIzquierda = new ArrayList<>();

        // Leer las variables en el lado izquierdo del '='
        while (true) {
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
                return;
            }
            String varName = sintactico.lex;
            variablesIzquierda.add(varName);
            avanzarToken();
            if (!sintactico.lex.equals(",")) {
                break; // Salimos si no hay más variables
            }
            avanzarToken(); // Consumir la coma
        }

        // Validar el signo '='
        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();

        // Leer las variables en el lado derecho del '='
        List<String> variablesDerecha = new ArrayList<>();
        for (int i = 0; i < variablesIzquierda.size(); i++) {
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
                return;
            }
            String varName = sintactico.lex;
            variablesDerecha.add(varName);
            avanzarToken();
            if (i < variablesIzquierda.size() - 1) {
                if (!sintactico.lex.equals(",")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ',' y llegó", sintactico.lex);
                    return;
                }
                avanzarToken(); // Consumir la coma
            }
        }

        // Realizar el intercambio sin validación
        for (int i = 0; i < variablesIzquierda.size(); i++) {
            String temp = variablesIzquierda.get(i);
            variablesIzquierda.set(i, variablesDerecha.get(i));
            variablesDerecha.set(i, temp);
        }
    }

    private void intercambioArreglos() {
        avanzarToken();
        List<String> variablesIzquierda = new ArrayList<>();
        List<String> indicesIzquierda = new ArrayList<>();

        // Leer las variables en el lado izquierdo del '='
        while (true) {
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
                return;
            }
            String varName = sintactico.lex;
            avanzarToken();
            if (!sintactico.lex.equals("[")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba '[' y llegó", sintactico.lex);
                return;
            }
            avanzarToken();
            String indice = sintactico.lex;
            sintactico.expresionesHandler.expr(); // Validar índice del arreglo
            if (!sintactico.lex.equals("]")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba ']' y llegó", sintactico.lex);
                return;
            }
            indicesIzquierda.add(indice);
            variablesIzquierda.add(varName);
            avanzarToken();
            if (!sintactico.lex.equals(",")) {
                break; // Salimos si no hay más variables
            }
            avanzarToken(); // Consumir la coma
        }

        // Validar el signo '='
        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis intercambio array", "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();

        // Leer las variables en el lado derecho del '='
        List<String> variablesDerecha = new ArrayList<>();
        List<String> indicesDerecha = new ArrayList<>();
        for (int i = 0; i < variablesIzquierda.size(); i++) {
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
                return;
            }
            String varName = sintactico.lex;
            avanzarToken();
            if (!sintactico.lex.equals("[")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba '[' y llegó", sintactico.lex);
                return;
            }
            avanzarToken();
            String indice = sintactico.lex;
            sintactico.expresionesHandler.expr();
            if (!sintactico.lex.equals("]")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba ']' y llegó", sintactico.lex);
                return;
            }
            indicesDerecha.add(indice);
            variablesDerecha.add(varName);
            avanzarToken();
            if (i < variablesIzquierda.size() - 1) {
                if (!sintactico.lex.equals(",")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ',' y llegó", sintactico.lex);
                    return;
                }
                avanzarToken(); // Consumir la coma
            }
        }

        // Realizar el intercambio sin validación
        for (int i = 0; i < variablesIzquierda.size(); i++) {
            String temp = variablesIzquierda.get(i);
            variablesIzquierda.set(i, variablesDerecha.get(i));
            variablesDerecha.set(i, temp);
        }
    }

    public boolean isRegresaPresente() {
        return regresaPresente;
    }

    private boolean isArreglo(String varName) {
        avanzarToken();
        boolean result = sintactico.lex.equals("[");
        retrocederToken();
        return result;
    }

    public List<Token> getTokens() {
        return sintactico.lexico.getTokenManager().getTokens();
    }

    public void imprimirTokens() {
        List<Token> tokens = getTokens();
        for (Token token : tokens) {
            System.out.println("ID: " + token.getId() + " Token: " + token.getTok() + ", Lexema: " + token.getLex());
        }
    }
}