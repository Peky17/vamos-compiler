package semantico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import sintactico.Sintactico;
import utils.ErrorManager;
import utils.Token;

public class Semantico {
    private Sintactico sintactico;

    public Semantico(Sintactico sintactico) {
        this.sintactico = sintactico;
    }

    public void prgm() {
        Scanner scanner = new Scanner(System.in);
        String archE = "";

        while (archE.length() < 3 || !archE.substring(archE.length() - 3).equals("icc")) {
            System.out.print("Archivo a compilar (*.icc) [.]=Salir: ");
            archE = scanner.nextLine();
            if (archE.equals(".")) {
                System.exit(0);
            }
            try (BufferedReader aEnt = new BufferedReader(new FileReader(archE))) {
                String linea;
                while ((linea = aEnt.readLine()) != null) {
                    sintactico.lexico.entrada += linea + "\n"; // Agregamos salto de línea para cada línea leída
                }
                break;
            } catch (IOException e) {
                System.out.println("Error al cargar el código: " + e.getMessage());
            }
        }

        System.out.println(sintactico.lexico.entrada + "\n"); // Muestra el contenido cargado

        sintactico.lexico.idx = 0;

        // Generar todos los tokens antes de iniciar el análisis sintáctico
        while (sintactico.lexico.hasNextToken()) {
            sintactico.lexico.lexico();
        }

        // Inicia el análisis
        sintactico.lexico.getTokenManager().resetTokenIndex();
        Token token = sintactico.lexico.getTokenManager().nextToken();
        sintactico.tok = token.getTok();
        sintactico.lex = token.getLex();

        // Proceso de importaciones y variables
        while (sintactico.lex.equals("importar")) {
            sintactico.importarHandler.importar();
        }
        while (sintactico.lex.equals("variable")
                || sintactico.lex.equals("constante")) {
            sintactico.variablesHandler.varsconsts();
        }

        // Análisis de funciones y validación de errores
        sintactico.funcionesHandler.funciones();
        boolean errEstatus = ErrorManager.getInstance().hasError();

        // Verifica y muestra el mensaje de compilación
        if (!errEstatus) {
            System.out.println("\n" + archE + " Compiló con EXITO!!!");
        } else {
            System.out.println("\n" + archE + " Error en la compilación.");
        }

        // Muestra los tokens generados al compilar
        sintactico.comandosHandler.imprimirTokens();

        scanner.close();
    }
}