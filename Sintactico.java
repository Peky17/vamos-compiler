import java.util.Arrays;

public class Sintactico {
    public Lexico lexico;
    public String tok = "";
    public String lex = "";
    public boolean errB = false;
    // Variable para almacenar el tipo de retorno actual
    public String tipoRetornoActual = "";

    public Sintactico(Lexico lexico) {
        this.lexico = lexico;
    }

    public void erra(String tipE, String desE, String strE) {
        errB = true;
        System.out.println(tipE + " " + desE + " " + strE);
    }

    public void importar() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!tok.equals("CtA") && !lex.equals("(")) {
            erra("Error de Sintaxis", "Se esperaba nombre de libreria o grupo libs y llego", lex);
        }
        if (lex.equals("(")) {
            result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            while (tok.equals("CtA")) {
                result = lexico.lexico();
                tok = result[0];
                lex = result[1];
            }
            if (!lex.equals(")")) {
                erra("Error de Sintaxis", "Se esperaba \")\" y llego", lex);
            }
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
    }

    public void dimen() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!tok.equals("Ent")) {
            erra("Error de Sintaxis", "Se esperaba constante entera y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!lex.equals("]")) {
            erra("Error de Sintaxis", "Se esperaba ] y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
    }

    public void gpoctes() {
    }

    public void varsconsts() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!tok.equals("Ide")) {
            erra("Error de Sintaxis", "Se esperaba Ide y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (lex.equals("["))
            dimen();
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(lex)) {
            erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (lex.equals("=")) {
            result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            if (lex.equals("{"))
                gpoctes();
            else {
                if (!Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(tok)) {
                    erra("Error de Sintaxis", "Se esperaba CtA, CtL, Ent o Dec y llego", lex);
                }
                result = lexico.lexico();
                tok = result[0];
                lex = result[1];
            }
        }
        if (lex.equals(","))
            varsconsts();
    }

    public void pars() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!tok.equals("Ide")) {
            erra("Error de Sintaxis", "Se esperaba identificador y llego", lex);
            return;
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(lex)) {
            erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", lex);
            return;
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (lex.equals(",")) {
            pars();
        }
    }

    public void udim() {
    }

    public void fmtleer() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!lex.equals("(")) {
            erra("Error de Sintaxis", "Se esperaba ( y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!tok.equals("Ide")) {
            erra("Error de Sintaxis", "Se esperaba Identificador y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (lex.equals("["))
            udim();
        if (!lex.equals(")")) {
            erra("Error de Sintaxis", "Se esperaba Identificador y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
    }

    public void termino() {
        if (lex.equals("(")) {
            String[] result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            expr();
            if (!lex.equals(")")) {
                erra("Error de Sintaxis", "Se esperaba \")\" y llego", lex);
            }
        } else if (Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(tok)) {
            // Do nothing
        }
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
    }

    public void oprel() {
        termino();
    }

    public void opy() {
        String op = "y";
        while (op.equals("y")) {
            op = "";
            opno();
            if (lex.equals("y")) {
                op = lex;
                String[] result = lexico.lexico();
                tok = result[0];
                lex = result[1];
            }
        }
    }

    public void opno() {
        if (lex.equals("no")) {
            String[] result = lexico.lexico();
            tok = result[0];
            lex = result[1];
        }
        oprel();
    }

    public void expr() {
        String op = "o";
        while (op.equals("o")) {
            op = "";
            opy();
            if (lex.equals("o")) {
                op = lex;
                String[] result = lexico.lexico();
                tok = result[0];
                lex = result[1];
            }
        }
    }

    public void fmtimprime() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!lex.equals("(")) {
            erra("Error de Sintaxis", "Se esperaba ( y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        String sp = ",";
        while (sp.equals(",")) {
            sp = "";
            expr();
            sp = lex;
            if (sp.equals(",")) {
                result = lexico.lexico();
                tok = result[0];
                lex = result[1];
            }
        }
        if (!lex.equals(")")) {
            erra("Error de Sintaxis", "Se esperaba ) y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
    }

    public void comando() {
        if (lex.equals("fmt")) {
            String[] result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            if (!lex.equals(".")) {
                erra("Error de Sintaxis", "Se esperaba . y llego", lex);
            }
            result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            if (lex.equals("Imprime"))
                fmtimprime();
            else if (lex.equals("Imprimenl")) {
                fmtimprime();
            } else if (lex.equals("Leer"))
                fmtleer();
            else {
                erra("Error de Sintaxis", "Se esperaba funcion de lib fmt [Leer, Imprime o Imprimnl] y llego", lex);
            }
        } else if (lex.equals("regresa")) {
            String[] result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            expr(); // Procesar la expresión de retorno
        } else {
            erra("Error de Sintaxis", "Comando no reconocido", lex);
        }
    }

    public void estatutos() {
        while (!lex.equals("}")) {
            comando();
        }
    }

    public void bloque() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!lex.equals("}")) {
            estatutos();
        }
    }

    public void funciones() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar nombre de la función (excepto para 'principal')
        if (!lex.equals("principal") && !tok.equals("Ide")) {
            erra("Error de Sintaxis", "Se esperaba nombre de función y llegó", lex);
            return;
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar apertura de paréntesis para los parámetros
        if (!lex.equals("(")) {
            erra("Error de Sintaxis", "Se esperaba '(' y llegó", lex);
            return;
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Procesar parámetros de la función, si los hay
        if (!lex.equals(")")) {
            while (true) {
                // Verificar que el parámetro tenga un identificador
                if (!tok.equals("Ide")) {
                    erra("Error de Sintaxis", "Se esperaba identificador y llegó", lex);
                    return;
                }

                result = lexico.lexico();
                tok = result[0];
                lex = result[1];

                // Verificar que el identificador vaya seguido de un tipo
                if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(lex)) {
                    erra("Error de Sintaxis", "Se esperaba un tipo de dato y llegó", lex);
                    return;
                }

                // Avanzar al siguiente token
                result = lexico.lexico();
                tok = result[0];
                lex = result[1];

                // Comprobar si hay más parámetros o cerrar paréntesis
                if (lex.equals(")")) {
                    break; // Fin de la lista de parámetros
                } else if (!lex.equals(",")) {
                    erra("Error de Sintaxis", "Se esperaba ',' o ')' y llegó", lex);
                    return;
                }

                // Avanzar al siguiente parámetro después de la coma
                result = lexico.lexico();
                tok = result[0];
                lex = result[1];
            }
        }

        // Verificar cierre de paréntesis de parámetros
        if (!lex.equals(")")) {
            erra("Error de Sintaxis", "Se esperaba ')' y llegó", lex);
            return;
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar tipo de retorno opcional
        if (Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(lex)) {
            result = lexico.lexico();
            tok = result[0];
            lex = result[1];
        }

        // Verificar apertura de bloque de la función
        if (!lex.equals("{")) {
            erra("Error de Sintaxis", "Se esperaba '{' y llegó", lex);
            return;
        } else {
            bloque(); // Llamada para procesar el bloque de la función
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar si hay más funciones
        if (lex.equals("funcion")) {
            funciones();
        }
    }

}