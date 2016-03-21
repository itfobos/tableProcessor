package com.processor.computation.parser;

public class FormulaBuffer {
    public static final char EOF = 0;
    private String formula;
    private int position;


    public FormulaBuffer(String formula) {
        this.formula = formula;
    }

    public char read() {
        if (position < formula.length()) {
            return formula.charAt(position++);
        } else {
            return EOF;
        }
    }

    public char lookAhead() {
        if (position < formula.length()) {
            return formula.charAt(position);
        } else {
            return EOF;
        }
    }

    @Override
    public String toString() {
        return this.formula;
    }
}


