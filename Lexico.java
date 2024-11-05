
import java.util.Arrays;
import java.util.List;

public class Lexico {
    private final int ERR = -1;
    private final int ACP = 999;
    public int idx = 0;
    public String entrada = "";
    public String tok = "";
    public String lex = "";
    private final List<String> cteLog = Arrays.asList("verdadero", "falso");
    private final List<String> palRes = Arrays.asList("interrumpe", "otro", "funcion", "interface", "selecciona",
            "caso", "difiere", "ir", "mapa", "estructura", "fmt", "Leer", "Imprime", "canal", "sino", "ir_a",
            "paquete", "segun", "principal", "Imprimenl", "constante", "si", "rango", "tipo", "entero", "decimal",
            "logico", "alfabetico", "continua", "desde", "importar", "regresa", "variable");

    private final int[][] matran = {
            { 1, 2, 14, 5, 6, 0, 8, 12, 10, 14, 15, 16 }, // 00 Estado inicial
            { 1, 1, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 01 Estado para identificadores
            { ACP, 2, 3, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 02 Estado para constantes enteras
            { ERR, 4, ERR, ERR, ERR, ERR, ERR, ERR, ERR, ERR, ERR, ERR }, // 03 Estado para constantes decimales
            { ACP, 4, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 04 Estado para constantes decimales
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 05 Estado para operadores aritméticos
            { ACP, ACP, ACP, ACP, 7, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 06 Estado para /
            { 7, 7, 7, 5, 6, ACP, 7, 7, 7, ACP, 7, 7 }, // 07 Estado para comentarios de línea
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, 9, ACP, ACP, ACP, ACP }, // 08 Estado para "
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 09 Estado para constantes alfabéticas
            { 10, 10, 10, 10, 10, ERR, 10, 10, 11, 10, 10, 10 }, // 10 Estado para operadores relacionales
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 11 Estado para operadores relacionales
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, 13, ACP, ACP, ACP, ACP }, // 12 Estado para [
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 13 Estado para ]
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 14 Estado para .
            { 15, 15, 15, 15, 15, ERR, 15, 15, 15, 15, 17, 15 }, // 15 Estado para operadores lógicos
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP }, // 16 Estado para símbolos especiales
            { ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP, ACP } // 17 Estado para operadores lógicos
    };

    public void erra(String tipE, String desE, String strE) {
        System.out.println(tipE + " " + desE + " " + strE);
    }

    public int colCar(char s) {
        if (Character.isAlphabetic(s))
            return 0;
        if (Character.isDigit(s))
            return 1;
        if (s == '.')
            return 2;
        if ("+-*%".indexOf(s) != -1)
            return 3;
        if (s == '/')
            return 4;
        if (s == '\n')
            return 5;
        if ("<>".indexOf(s) != -1)
            return 6;
        if (s == '!' || s == '=')
            return 7;
        if (s == '"')
            return 8;
        if ("{}()[],:;".indexOf(s) != -1)
            return 9;
        if ("|&!".indexOf(s) != -1)
            return 10; // Nueva columna para operadores lógicos
        if ("#$^¿¡?".indexOf(s) != -1)
            return 11; // Nueva columna para símbolos especiales
        return ERR;
    }

    public String[] scanner() {
        int estado = 0;
        StringBuilder lexema = new StringBuilder();
        int estAnt = 0;

        while (idx < entrada.length() && estado != ERR && estado != ACP) {
            char c = entrada.charAt(idx);
            idx++;
            while (estado == 0 && "\n\t ".indexOf(c) != -1) {
                if (idx >= entrada.length())
                    break; // Check bounds
                c = entrada.charAt(idx);
                idx++;
            }
            if (estado == 0 && "\n\t ".indexOf(c) == -1) {
                idx--;
            }
            while (estado == 7 && c != '\n') {
                if (idx >= entrada.length())
                    break; // Check bounds
                c = entrada.charAt(idx);
                idx++;
            }
            if (estado == 7 && c == '\n') {
                idx--;
            }
            if (estado == 11) {
                idx--;
            }
            if (estado == 7 || estado == 0 || estado == 11) {
                if (idx >= entrada.length())
                    break; // Check bounds
                c = entrada.charAt(idx);
                idx++;
            }
            int col = colCar(c);
            if (estado == 10 && "\n\"".indexOf(c) == -1) {
                col = 1;
            }
            if (col >= 0 && col <= 9) {
                estAnt = estado;
                estado = matran[estado][col];
                if (estado == ERR || estado == ACP) {
                    idx--;
                    break;
                } else {
                    lexema.append(c);
                }
            } else {
                break;
            }
        }

        if (estado != ERR && estado != ACP) {
            estAnt = estado;
        }
        switch (estAnt) {
            case 1:
                lex = lexema.toString();
                tok = "Ide";
                if (palRes.contains(lexema.toString())) {
                    tok = "Res";
                } else if (cteLog.contains(lexema.toString())) {
                    tok = "CtL";
                }
                break;
            case 2:
                lex = lexema.toString();
                tok = "Ent";
                break;
            case 4:
                lex = lexema.toString();
                tok = "Dec";
                break;
            case 5:
            case 6:
                lex = lexema.toString();
                tok = "OpA";
                break;
            case 7:
                lex = "";
                tok = "Com";
                break;
            case 11:
                lex = lexema.toString();
                tok = "CtA";
                break;
            case 12:
                lex = lexema.toString();
                tok = "OpS";
                break;
            case 8:
            case 9:
            case 13:
                lex = lexema.toString();
                tok = "OpR";
                break;
            case 14:
                tok = "Del";
                lex = lexema.toString();
                break;
            case 3:
                erra("Error Lexico", "Constante decimal incompleta", lexema.toString());
                tok = "Dec";
                lex = lexema.toString();
                break;
            case 10:
                erra("Error Lexico", "Constante Alfabetica SIN cerrar", lexema.toString());
                tok = "CtA";
                lex = lexema.toString();
                break;
            case 15:
                lex = lexema.toString();
                tok = "OpL";
                break;
            case 16:
                lex = lexema.toString();
                tok = "Sym";
                break;
            case 17:
                lex = lexema.toString();
                tok = "OpL";
                break;
        }

        return new String[] { tok, lex };
    }

    public String[] lexico() {
        String[] result = scanner();
        while (result[0].equals("Com")) {
            result = scanner();
        }
        return result;
    }
}
