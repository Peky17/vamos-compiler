package sintactico;

import handlers.Comandos;
import handlers.Expresiones;
import handlers.Funciones;
import handlers.Importar;
import handlers.Variables;
import lexico.Lexico;
import utils.ErrorManager;

public class Sintactico {
    public Lexico lexico;
    public String tok = "";
    public String lex = "";

    public Importar importarHandler;
    public Variables variablesHandler;
    public Funciones funcionesHandler;
    public Expresiones expresionesHandler;
    public Comandos comandosHandler;

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