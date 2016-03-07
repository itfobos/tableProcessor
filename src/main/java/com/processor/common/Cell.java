package com.processor.common;

//TODO:
public class Cell {


    public static final char FORMULA_PREFIX = '=';
    public static final char TEXT_PREFIX = '\'';

    private String value;

    public Cell(String value) {
        this.value = value;
    }

    public int getIntValue() {
        if (isIntValue()) {
            return Integer.parseInt(value);
        }

        throw new UnsupportedOperationException("The cell value: '" + value + "' cant be interpret like int.");
    }

    public String getTextValue() {
        if (isText()) {
            return value.substring(1);//skip TEXT_PREFIX symbol
        }

        throw new UnsupportedOperationException("The cell value: '" + value + "' isn't text. ");
    }

    public boolean isFormula() {
        return value.length() > 0 && value.charAt(0) == FORMULA_PREFIX;
    }

    public boolean isIntValue() {
        return value.length() > 0 && Character.isDigit(value.charAt(0));
    }

    public boolean isText() {
        return value.isEmpty() || value.charAt(0) == TEXT_PREFIX;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Cell{" + "value='" + value + '\'' + '}';
    }
}
