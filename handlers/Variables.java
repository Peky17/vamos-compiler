package handlers;

import java.util.Arrays;
import sintactico.Sintactico;
import utils.Token;
import utils.TipoUtils;

public class Variables {
    private Sintactico sintactico;
    private String nombreArreglo;

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
        boolean isConstant = sintactico.lex.equals("constante");
        avanzarToken();
        if (!sintactico.tok.equals("Ide")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
        }
        StringBuilder nomIdes = new StringBuilder(sintactico.lex);
        avanzarToken();
        while (sintactico.lex.equals(",")) {
            avanzarToken();
            if (!sintactico.tok.equals("Ide")) {
                sintactico.erra("Error de Sintaxis", "Se esperaba Ide y llego", sintactico.lex);
            }
            nomIdes.append(",").append(sintactico.lex);
            avanzarToken();
        }
        if (sintactico.lex.equals("[")) {
            nombreArreglo = nomIdes.toString();
            sintactico.variablesHandler.arreglo();
            return;
        }
        if (!Arrays.asList("alfabetico", "decimal", "entero", "logico").contains(sintactico.lex)) {
            sintactico.erra("Error de Sintaxis", "Se esperaba tipo de dato y llego", sintactico.lex);
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
        // Registrar todas las variables en la tabla de símbolos
        String[] variables = nomIdes.toString().split(",");
        for (String variable : variables) {
            sintactico.regtabSim(variable.trim(),
                    new String[] { isConstant ? "C" : "V", tipo, "0", "0" });
        }
        avanzarToken();
        if (sintactico.lex.equals("=")) {
            avanzarToken();
            if (sintactico.lex.equals("{")) {
                sintactico.erra("Error de Sintaxis", "No se puede asignar como arreglo una variable lineal",
                        sintactico.lex);
            } else {
                if (!Arrays.asList("CtA", "CtL", "Dec", "Ent").contains(sintactico.tok)) {
                    sintactico.erra("Error de Sintaxis", "Se esperaba CtA, CtL, Ent o Dec y llego", sintactico.lex);
                } else {
                    // Validar tipo de la constante asignada
                    String tipoConstante = "";
                    switch (sintactico.tok) {
                        case "CtA":
                            tipoConstante = "A";
                            break;
                        case "CtL":
                            tipoConstante = "L";
                            break;
                        case "Dec":
                            tipoConstante = "D";
                            break;
                        case "Ent":
                            tipoConstante = "E";
                            break;
                    }

                    String key = tipo + "=" + tipoConstante;
                    if (!TipoUtils.tiposTab.containsKey(key)) {
                        sintactico.erra("Error de Semantica", "Tipo de constante no coincide con tipo de variable",
                                key);
                    }
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
        if (!sintactico.lex.equals("[")) {
            sintactico.erra("Error de Sintaxis", "Se esperaba '[' y llegó", sintactico.lex);
            return;
        }
        avanzarToken();
        int tamano = 0;
        if (sintactico.tok.equals("Ent")) {
            // Es una constante entera
            tamano = Integer.parseInt(sintactico.lex);
            avanzarToken();
        } else if (sintactico.tok.equals("Ide")) {
            // Es un identificador, verificar si es una constante entera
            String[] simbolo = sintactico.leetabSim(sintactico.lex);
            if (simbolo.length == 0 || !simbolo[0].equals("C") || !simbolo[1].equals("E")) {
                sintactico.erra("Error de Semantica", "Se esperaba una constante entera y llegó", sintactico.lex);
                return;
            }
            tamano = Integer.parseInt(simbolo[2]);
            avanzarToken();
        } else {
            sintactico.erra("Error de Sintaxis", "Se esperaba un número entero o una constante entera y llegó",
                    sintactico.lex);
            return;
        }

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

        String tipo = "";
        switch (tipoDato) {
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

        // Registrar el arreglo en la tabla de símbolos
        sintactico.regtabSim(nombreArreglo, new String[] { "A", tipo, String.valueOf(tamano), "0" });

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