import java.util.Arrays;

public class Sintactico {
    public Lexico lexico;
    public String tok = "";
    public String lex = "";
    public boolean errB = false;

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
        if (lex.equals("[")) {
            arreglo();
            return;
        }
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

    public void expr() {
        opy();
        while (lex.equals("o") || lex.equals("||")) {
            String[] result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            opy();
        }
    }

    public void opy() {
        opno();
        while (lex.equals("y") || lex.equals("&&")) {
            String[] result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            opno();
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

    public void oprel() {
        termino();
        while (Arrays.asList("==", "!=", "<", ">", "<=", ">=").contains(lex)) {
            String[] result = lexico.lexico();
            tok = result[0];
            lex = result[1];
            termino();
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
        } else if (lex.equals("si")) {
            siSino();
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

    public void siSino() {
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Procesar la condición
        expr();

        // Verificar apertura de bloque
        if (!lex.equals("{")) {
            erra("Error de Sintaxis", "Se esperaba '{' y llegó", lex);
            return;
        }

        // Procesar el bloque de código
        bloque();

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar si hay un "sino"
        if (lex.equals("sino")) {
            result = lexico.lexico();
            tok = result[0];
            lex = result[1];

            // Verificar apertura de bloque
            if (!lex.equals("{")) {
                erra("Error de Sintaxis", "Se esperaba '{' y llegó", lex);
                return;
            }

            // Procesar el bloque de código del "sino"
            bloque();

            result = lexico.lexico();
            tok = result[0];
            lex = result[1];
        }
    }

    public void arreglo() {
        // Verificar que el siguiente token sea un número entero
        String[] result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!tok.equals("Ent")) {
            erra("Error de Sintaxis", "Se esperaba un número entero y llegó", lex);
            return;
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar que el siguiente token sea ']'
        if (!lex.equals("]")) {
            erra("Error de Sintaxis", "Se esperaba ']' y llegó", lex);
            return;
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar que el siguiente token sea un tipo de dato
        String tipoDato = lex;
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(tipoDato)) {
            erra("Error de Sintaxis", "Se esperaba un tipo de dato y llegó", lex);
            return;
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar que el siguiente token sea '{'
        if (!lex.equals("{")) {
            erra("Error de Sintaxis", "Se esperaba '{' y llegó", lex);
            return;
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];

        // Verificar que los elementos dentro de '{}' sean del tipo de dato declarado
        while (!lex.equals("}")) {
            boolean tipoValido = false;
            switch (tipoDato) {
                case "alfabetico":
                    tipoValido = tok.equals("CtA");
                    break;
                case "decimal":
                    tipoValido = tok.equals("Dec");
                    break;
                case "entero":
                    tipoValido = tok.equals("Ent");
                    break;
                case "logico":
                    tipoValido = tok.equals("CtL");
                    break;
            }

            if (!tipoValido) {
                erra("Error de Sintaxis", "Se esperaba un valor del tipo de dato " + tipoDato + " y llegó", lex);
                return;
            }

            result = lexico.lexico();
            tok = result[0];
            lex = result[1];

            if (lex.equals(",")) {
                result = lexico.lexico();
                tok = result[0];
                lex = result[1];
            } else if (!lex.equals("}")) {
                erra("Error de Sintaxis", "Se esperaba ',' o '}' y llegó", lex);
                return;
            }
        }

        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
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