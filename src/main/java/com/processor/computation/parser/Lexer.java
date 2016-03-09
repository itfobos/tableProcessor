package com.processor.computation.parser;

public class Lexer {
    public static final int ALPHABET_SIZE = 26;
    private FormulaBuffer buffer;

    public Lexer(String formula) {
        buffer = new FormulaBuffer(formula);
    }

    /**
     * @return null if no more lexemes available
     */
    public String getLexeme() {
        char currChar;
        while ((currChar = buffer.read()) != FormulaBuffer.EOF) {

        }

        return null;//TODO:
    }

    private static boolean isLetter(char symbol) {
        int diff = 'a' - Character.toLowerCase(symbol);
        return Math.abs(diff) < ALPHABET_SIZE;
    }
}
