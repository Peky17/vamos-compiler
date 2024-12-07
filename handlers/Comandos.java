package handlers;

import java.util.Arrays;

import sintactico.Sintactico;

public class Comandos {
    private Sintactico sintactico;

    public Comandos(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void comando() {
        if (sintactico.lex.equals("fmt")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];

            if (!sintactico.lex.equals(".")) {
                sintactico.erra("Error de Sintaxis",
                        "Se esperaba '.' y llegó", sintactico.lex);
                return;
            }

            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];

            if (sintactico.lex.equals("Imprime")) {
                fmtimprime();
            } else if (sintactico.lex.equals("Imprimenl")) {
                fmtimprime();
            } else if (sintactico.lex.equals("Leer")) {
                fmtleer();
            } else {
                sintactico.erra("Error de Sintaxis",
                        "Se esperaba una función de la biblioteca 'fmt' [Leer, Imprime o Imprimenl] y llegó",
                        sintactico.lex);
            }
        } else if (sintactico.lex.equals("regresa")) {
            String[] result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
            // Procesar la expresión de retorno
            sintactico.expresionesHandler.expr();
        } else if (sintactico.lex.equals("si")) {
            siSino(); // Procesar el bloque "si"
        } else if (sintactico.lex.equals("desde")) {
            desde(); // Procesar el bloque "desde"
        } else if (sintactico.lex.equals("segun")) {
            segun(); // Procesar el bloque "según"
        } else if (sintactico.lex.equals("interrumpe")) {
            interrumpe(); // Procesar "interrumpe"
        } else if (sintactico.lex.equals("predeterminado")) {
            predeterminado(); // Procesar "predeterminado"
        } else if (sintactico.tok.equals("Ide")) { // Verificar si es una llamada a función
            sintactico.funcionesHandler.llamadaFuncion();
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
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.lex.equals("}")) {
            estatutos();
        }
    }

    public void siSino() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Procesar la condición
        sintactico.expresionesHandler.expr();

        // Verificar apertura de bloque
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '{' y llegó",
                    sintactico.lex);
            return;
        }

        // Procesar el bloque de código
        bloque();

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar si hay un "sino"
        if (sintactico.lex.equals("sino")) {
            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];

            // Verificar apertura de bloque
            if (!sintactico.lex.equals("{")) {
                sintactico.erra("Error de Sintaxis",
                        "Se esperaba '{' y llegó", sintactico.lex);
                return;
            }

            // Procesar el bloque de código del "sino"
            bloque();

            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
        }
    }

    public void desde() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar si es la sintaxis simple
        if (sintactico.lex.equals("{")) {
            desdeSimple();
            return;
        }

        // Inicialización
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba un identificador para la inicialización y llegó",
                    sintactico.lex);
            return;
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba un valor entero para la inicialización y llegó",
                    sintactico.lex);
            return;
        }
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.lex.equals(";")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba ';' y llegó", sintactico.lex);
            return;
        }

        // Condición
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        sintactico.expresionesHandler.expr();
        if (!sintactico.lex.equals(";")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba ';' y llegó", sintactico.lex);
            return;
        }

        // Incremento
        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba un identificador para el incremento y llegó",
                    sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
        sintactico.expresionesHandler.expr();

        // Verificar apertura de bloque
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }

        // Procesar el bloque de código
        bloque();

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }

    public void desdeSimple() {
        // Verificar apertura de bloque
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }

        // Procesar el bloque de código
        bloque();

        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }

    public void segun() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar que el siguiente token sea un identificador
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba un identificador y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar apertura de bloque
        if (!sintactico.lex.equals("{")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba '{' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Procesar los casos dentro del bloque
        while (!sintactico.lex.equals("}")) {
            if (sintactico.lex.equals("caso")) {
                result = sintactico.lexico.lexico();
                sintactico.tok = result[0];
                sintactico.lex = result[1];

                // Verificar que el caso sea una constante válida
                if (!Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)) {
                    sintactico.erra("Error de Sintaxis",
                            "Se esperaba una constante válida y llegó",
                            sintactico.lex);
                    return;
                }

                result = sintactico.lexico.lexico();
                sintactico.tok = result[0];
                sintactico.lex = result[1];

                // Verificar que el siguiente token sea ':'
                if (!sintactico.lex.equals(":")) {
                    sintactico.erra("Error de Sintaxis",
                            "Se esperaba ':' y llegó", sintactico.lex);
                    return;
                }

                result = sintactico.lexico.lexico();
                sintactico.tok = result[0];
                sintactico.lex = result[1];

                // Procesar los comandos dentro del caso
                while (!sintactico.lex.equals("}") &&
                        !sintactico.lex.equals("caso") &&
                        !sintactico.lex.equals("predeterminado") &&
                        !sintactico.lex.equals("interrumpe")) {
                    comando();
                    result = sintactico.lexico.lexico();
                    sintactico.tok = result[0];
                    sintactico.lex = result[1];
                }

                // Verificar si hay un interrumpe
                if (sintactico.lex.equals("interrumpe")) {
                    interrumpe();
                    result = sintactico.lexico.lexico();
                    sintactico.tok = result[0];
                    sintactico.lex = result[1];
                }
            } else if (sintactico.lex.equals("predeterminado")) {
                predeterminado();
            } else {
                sintactico.erra("Error de Sintaxis",
                        "Se esperaba 'caso' o 'predeterminado' y llegó",
                        sintactico.lex);
                return;
            }
        }

        // Verificar cierre de bloque
        if (!sintactico.lexico.lex.equals("}")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '}' y llegó", sintactico.lexico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.lexico.tok = result[0];
        sintactico.lexico.lex = result[1];
    }

    public void predeterminado() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Verificar que el siguiente token sea ':'
        if (!sintactico.lex.equals(":")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba ':' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Procesar los comandos dentro del caso predeterminado
        while (!sintactico.lex.equals("}") &&
                !sintactico.lex.equals("caso") &&
                !sintactico.lex.equals("predeterminado")) {
            comando();
            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
        }
    }

    public void interrumpe() {
        // Simplemente avanzar al siguiente token
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }

    public void fmtleer() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        if (!sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba '(' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba un identificador y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        if (sintactico.lex.equals("[")) {
            sintactico.variablesHandler.udim();
            result = sintactico.lexico.lexico();
            sintactico.tok = result[0];
            sintactico.lex = result[1];
        }

        if (!sintactico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba ')' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }

    public void fmtimprime() {
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        if (!sintactico.lex.equals("(")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba '(' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        String sp = ",";
        while (sp.equals(",")) {
            sp = "";
            sintactico.expresionesHandler.expr();
            sp = sintactico.lex;

            if (sp.equals(",")) {
                result = sintactico.lexico.lexico();
                sintactico.tok = result[0];
                sintactico.lex = result[1];
            }
        }

        if (!sintactico.lex.equals(")")) {
            sintactico.erra("Error de Sintaxis",
                    "Se esperaba ')' y llegó", sintactico.lex);
            return;
        }

        result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];
    }

}