package utils;

public class Token {
    private Integer id;
    private String tok;
    private String lex;

    public Token(Integer id, String tok, String lex) {
        this.id = id;
        this.tok = tok;
        this.lex = lex;
    }

    public Integer getId() {
        return id;
    }

    public String getTok() {
        return tok;
    }

    public String getLex() {
        return lex;
    }
}
