package utils;

public class ErrorManager {
    private static ErrorManager instance;
    private String tipoError;
    private String descripcionError;
    private String stringConflicto;
    private boolean errorFlag;

    private ErrorManager() {
        this.tipoError = "";
        this.descripcionError = "";
        this.stringConflicto = "";
        this.errorFlag = false;
    }

    public static synchronized ErrorManager getInstance() {
        if (instance == null) {
            instance = new ErrorManager();
        }
        return instance;
    }

    public void reportError(String tipoError, String descripcionError, String stringConflicto) {
        this.tipoError = tipoError;
        this.descripcionError = descripcionError;
        this.stringConflicto = stringConflicto;
        this.errorFlag = true;
        printAndExit();
    }

    private void printAndExit() {
        System.err.println("Error Tipo: " + tipoError);
        System.err.println("Descripción: " + descripcionError);
        System.err.println("Conflicto: " + stringConflicto);
        System.exit(1);
    }

    public boolean hasError() {
        return errorFlag;
    }
}