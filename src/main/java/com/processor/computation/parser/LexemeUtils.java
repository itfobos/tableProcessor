package com.processor.computation.parser;

class LexemeUtils {
    public static final int ALPHABET_SIZE = 26;

    public static boolean isEnLetter(char symbol) {
        int diff = 'a' - Character.toLowerCase(symbol);
        return Math.abs(diff) < ALPHABET_SIZE;
    }

    public static boolean isArithmeticOperation(char symbol) {
        return symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/';
    }

    public static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
