package com.processor.computation.parser;

import static com.processor.computation.parser.LexemeUtils.isArithmeticOperation;

public class Lexer {
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
            if (LexemeUtils.isEnLetter(symbol)) {
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
        
        if (!LexemeUtils.isNumeric(builder.substring(1/*except first letter*/))) {
            fireError(builder);
        }

        return builder.toString();
    }

    private String parseNumber(char symbol) throws FormulaParseException {
        StringBuilder builder = new StringBuilder().append(symbol);
        while (!isArithmeticOperation(buffer.lookAhead()) && (symbol = buffer.read()) != FormulaBuffer.EOF) {
            builder.append(symbol);
        }

        String result = builder.toString();
        if (!LexemeUtils.isNumeric(result)) {
            fireError(builder);
        }

        return result;
    }

    private static void fireError(Object message) throws FormulaParseException {
        throw new FormulaParseException("Lexeme is not available: '" + message + "'");
    }

}
