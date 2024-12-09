package sintactico;

import handlers.Comandos;
import handlers.Expresiones;
import handlers.Funciones;
import handlers.Importar;
import handlers.Variables;
import lexico.Lexico;
import utils.ErrorManager;

import java.util.HashMap;
import java.util.Map;

public class Sintactico {
    public Importar importarHandler;
    public Variables variablesHandler;
    public Funciones funcionesHandler;
    public Expresiones expresionesHandler;
    public Comandos comandosHandler;
    public Lexico lexico;
    // Variables para almacenar el token y lexema actual
    public String tok = "";
    public String lex = "";
    // Tabla de símbolos
    private Map<String, String[]> tabSim = new HashMap<>();

    public Sintactico(Lexico lexico) {
        this.lexico = lexico;
        this.importarHandler = new Importar(this);
        this.variablesHandler = new Variables(this);
        this.funcionesHandler = new Funciones(this);
        this.comandosHandler = new Comandos(this);
        this.expresionesHandler = new Expresiones(this);
    }

    public void erra(String tipE, String desE, String strE) {
        ErrorManager.getInstance().reportError(tipE, desE, strE);
    }

    public void regtabSim(String key, String[] data) {
        tabSim.put(key, data);
    }

    public String[] leetabSim(String key) {
        if (!tabSim.containsKey(key)) {
            erra("Error de Semantica", "Identificador NO declarado y llego", key);
            return new String[0];
        }
        return tabSim.get(key);
    }

    public void analizarFuncionesDeclaradas() {
        // Iniciar el análisis sintáctico y semántico de las funciones
        funcionesHandler.funciones();
        // Verificar si la función principal está declarada
        funcionesHandler.verificarPrincipalDeclarada();
    }
}