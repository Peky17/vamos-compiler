package handlers;

import java.util.Arrays;
import java.util.List;

import sintactico.Sintactico;
import utils.Token;

public class Comandos {
    private Sintactico sintactico;

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
            avanzarToken();
            sintactico.expresionesHandler.expr();
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
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un identificador para la inicialización y llegó",
                    sintactico.lex);
            return;
        }
        avanzarToken();
        if (!sintactico.lex.equals("=")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '=' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        if (!sintactico.tok.equals("Ent")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba un valor entero para la inicialización y llegó",
                    sintactico.lex);
            return;
        }
        avanzarToken();
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
            sintactico.erra("Error de Sintaxis", "Se esperaba un identificador y llegó", sintactico.lex);
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
                    avanzarToken();
                }
                if (sintactico.lex.equals("interrumpe")) {
                    interrumpe();
                    avanzarToken();
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
            avanzarToken();
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