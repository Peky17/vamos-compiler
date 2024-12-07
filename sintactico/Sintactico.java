package sintactico;

import handlers.Comandos;
import handlers.Expresiones;
import handlers.Funciones;
import handlers.Importar;
import handlers.Variables;
import lexico.Lexico;
import utils.ErrorManager;

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
}