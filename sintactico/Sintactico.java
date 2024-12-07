package sintactico;

import handlers.SintacticoComandos;
import handlers.SintacticoExpresiones;
import handlers.SintacticoFunciones;
import handlers.SintacticoImportar;
import handlers.SintacticoVariables;
import lexico.Lexico;

public class Sintactico {
    public Lexico lexico;
    public String tok = "";
    public String lex = "";
    public boolean errB = false;

    public SintacticoImportar importarHandler;
    public SintacticoVariables variablesHandler;
    public SintacticoFunciones funcionesHandler;
    public SintacticoExpresiones expresionesHandler;
    public SintacticoComandos comandosHandler;

    public Sintactico(Lexico lexico) {
        this.lexico = lexico;
        this.importarHandler = new SintacticoImportar(this);
        this.variablesHandler = new SintacticoVariables(this);
        this.funcionesHandler = new SintacticoFunciones(this);
        this.comandosHandler = new SintacticoComandos(this);
        this.expresionesHandler = new SintacticoExpresiones(this);
    }

    public void erra(String tipE, String desE, String strE) {
        errB = true;
        System.out.println(tipE + " " + desE + " " + strE);
    }
}