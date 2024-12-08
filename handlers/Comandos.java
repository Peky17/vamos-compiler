package handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sintactico.Sintactico;
import utils.TipoUtils;
import utils.Token;

public class Comandos {
    private Sintactico sintactico;

    private boolean regresaPresente = false;

    public Comandos(Sintactico sintactico) {
        this.sintactico = sintactico;
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
            desde();
        } else if (sintactico.lex.equals("segun")) {
            segun();
        } else if (sintactico.lex.equals("interrumpe")) {
            interrumpe();
        } else if (sintactico.lex.equals("predeterminado")) {
            predeterminado();
        } else if (sintactico.tok.equals("Ide")) {
            String varName = sintactico.lex;
            avanzarToken();
            if (sintactico.lex.equals(",")) {
                retrocederToken();
                intercambio();
            } else if (sintactico.lex.equals("(")) {
                retrocederToken();
                sintactico.funcionesHandler.llamadaFuncion();
            } else if (sintactico.lex.equals("=")) {
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
        sintactico.expresionesHandler.expr();
        String tipoVariable = sintactico.leetabSim(varName)[1];
        String tipoExpresion = sintactico.expresionesHandler.getTipoExpresionActual();
        String key = tipoVariable + "=" + tipoExpresion;
        if (!TipoUtils.tiposTab.containsKey(key)) {
            sintactico.erra("Error de Semantica", "Tipo de la expresión no coincide con el tipo de la variable", key);
        } else {
            // Actualizar el valor de la variable en la tabla de símbolos
            sintactico.regtabSim(varName, new String[] { "V", tipoVariable, "0", "0" });
        }
    }

    private void intercambio() {
        List<String> variables = new ArrayList<>();
        List<String> tipos = new ArrayList<>();

        // Leer las variables antes del '='
        while (true) {
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
                return;
            }
            variables.add(sintactico.lex);
            String[] data = sintactico.leetabSim(sintactico.lex);
            if (data.length == 0) {
                sintactico.erra("Error de Semantica", "Identificador no declarado", sintactico.lex);
                return;
            }
            tipos.add(data[1]);
            avanzarToken();
            if (!sintactico.lex.equals(",")) {
                break;
            }
            avanzarToken();
        }

        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();

        // Leer las variables después del '='
        List<String> valores = new ArrayList<>();
        for (int i = 0; i < variables.size(); i++) {
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
                return;
            }
            valores.add(sintactico.lex);
            String[] data = sintactico.leetabSim(sintactico.lex);
            if (data.length == 0) {
                sintactico.erra("Error de Semantica", "Identificador no declarado", sintactico.lex);
                return;
            }
            if (!data[1].equals(tipos.get(i))) {
                sintactico.erra("Error de Semantica", "Tipos incompatibles en el intercambio", sintactico.lex);
                return;
            }
            avanzarToken();
            if (i < variables.size() - 1) {
                if (!sintactico.lex.equals(",")) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba ',' y llegó", sintactico.lex);
                    return;
                }
                avanzarToken();
            }
        }

        // Realizar el intercambio
        for (int i = 0; i < variables.size(); i++) {
            String temp = valores.get(i);
            valores.set(i, variables.get(i));
            variables.set(i, temp);
        }

        // Actualizar la tabla de símbolos
        for (int i = 0; i < variables.size(); i++) {
            sintactico.regtabSim(variables.get(i), new String[] { "V", tipos.get(i), "0", "0" });
            sintactico.regtabSim(valores.get(i), new String[] { "V", tipos.get(i), "0", "0" });
        }
    }

    public boolean isRegresaPresente() {
        return regresaPresente;
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