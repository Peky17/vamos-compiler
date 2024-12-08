package utils;

import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    private List<Token> tokens = new ArrayList<>();
    private int currentTokenIndex = -1;
    private int nextId = 1;

    public void addToken(String tok, String lex) {
        tokens.add(new Token(nextId++, tok, lex));
    }

    public boolean hasNextToken() {
        return currentTokenIndex < tokens.size() - 1;
    }

    public boolean hasPreviousToken() {
        return currentTokenIndex > 0;
    }

    public Token nextToken() {
        if (hasNextToken()) {
            currentTokenIndex++;
            return tokens.get(currentTokenIndex);
        }
        return new Token(-1, null, null); // Retorna un token inválido en lugar de null
    }

    public Token previousToken() {
        if (hasPreviousToken()) {
            currentTokenIndex--;
            return tokens.get(currentTokenIndex);
        }
        return new Token(-1, null, null); // Retorna un token inválido en lugar de null
    }

    public void resetTokenIndex() {
        currentTokenIndex = -1;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public String[] getTokenByIndex(int index) {
        if (index >= 0 && index < tokens.size()) {
            Token token = tokens.get(index);
            return new String[] { token.getTok(), token.getLex() };
        }
        return null;
    }

    public String[] getTokenById(int id) {
        for (Token token : tokens) {
            if (token.getId() == id) {
                return new String[] { token.getTok(), token.getLex() };
            }
        }
        return null;
    }
}