import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println(sintactico.lexico.entrada + "\n"); // Muestra el contenido cargado

        sintactico.lexico.idx = 0;
        sintactico.errB = false;

        // Inicia el análisis
        String[] result = sintactico.lexico.lexico();
        sintactico.tok = result[0];
        sintactico.lex = result[1];

        // Proceso de importaciones y variables
        while (sintactico.lex.equals("importar")) {
            sintactico.importar();
        }
        while (sintactico.lex.equals("variable")
                || sintactico.lex.equals("constante")) {
            sintactico.varsconsts();
        }

        // Análisis de funciones y validación de errores
        sintactico.funciones();

        // Verifica y muestra el mensaje de compilación
        if (!sintactico.errB) {
            System.out.println("\n" + archE + " Compiló con EXITO!!!");
        } else {
            System.out.println("\n" + archE + " Error en la compilación.");
        }
    }
}
