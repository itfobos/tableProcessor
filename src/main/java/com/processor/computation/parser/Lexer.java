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
    public String getLexeme() throws FormulaParseException {
        String result = null;

        char symbol;
        if ((symbol = buffer.read()) != FormulaBuffer.EOF) {
            if (isEnLetter(symbol)) {
                result = parseReference(symbol);
            } else if (Character.isDigit(symbol)) {
                result = parseNumber(symbol);
            } else if (isArithmeticOperation(symbol)) {
                result = String.valueOf(symbol);
            } else {
                throw new FormulaParseException("Unknown symbol: '" + symbol + "'");
            }
        }

        return result;
    }

    private String parseReference(char symbol) throws FormulaParseException {
        StringBuilder builder = new StringBuilder().append(Character.toUpperCase(symbol));
        while (!isArithmeticOperation(buffer.lookAhead()) && (symbol = buffer.read()) != FormulaBuffer.EOF) {
            builder.append(symbol);
        }

        if (isArithmeticOperation(buffer.lookAhead())) {
            builder.append(buffer.read());
        }

        if (!isNumeric(builder.substring(1/*except first letter*/))) {
            fireError(builder);
        }

        return builder.toString();
    }

    private String parseNumber(char symbol) throws FormulaParseException {
        StringBuilder builder = new StringBuilder().append(Character.toUpperCase(symbol));
        while (!isArithmeticOperation(buffer.lookAhead()) && (symbol = buffer.read()) != FormulaBuffer.EOF) {
            builder.append(symbol);
        }

        if (isArithmeticOperation(buffer.lookAhead())) {
            builder.append(buffer.read());
        }

        String result = builder.toString();
        if (!isNumeric(result)) {
            fireError(builder);
        }

        return result;
    }

    private static void fireError(Object message) throws FormulaParseException {
        throw new FormulaParseException("Lexeme is not available: '" + message + "'");
    }

    private static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isEnLetter(char symbol) {
        int diff = 'a' - Character.toLowerCase(symbol);
        return Math.abs(diff) < ALPHABET_SIZE;
    }

    static boolean isArithmeticOperation(char symbol) {
        return symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/';
    }

    static boolean isDelimiter(char symbol) {
        return symbol == FormulaBuffer.EOF || isArithmeticOperation(symbol);
    }
}
