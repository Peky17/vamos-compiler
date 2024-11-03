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

    public void udim() {
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
        if (!lex.equals("principal") && !tok.equals("Ide")) {
            erra("Error de Sintaxis", "Se esperaba nombre de funcion y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!lex.equals("(")) {
            erra("Error de Sintaxis", "Se esperaba ( y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (!lex.equals(")"))
            pars();
        if (!lex.equals(")")) {
            erra("Error de Sintaxis", "Se esperaba ) y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(lex)) {
            result = lexico.lexico();
            tok = result[0];
            lex = result[1];
        }
        if (lex.equals("{"))
            bloque();
        else {
            erra("Error de Sintaxis", "Se esperaba { y llego", lex);
        }
        result = lexico.lexico();
        tok = result[0];
        lex = result[1];
        if (lex.equals("func"))
            funciones();
    }
}