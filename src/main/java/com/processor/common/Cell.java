package com.processor.common;

public class Cell {
    private static final char FORMULA_PREFIX = '=';
    private static final char TEXT_PREFIX = '\'';

    private final String value;
    private final String name;

    public Cell(String name, String value) {
        this.value = value;
        this.name = name;
    }

    public int getIntValue() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException("The cell value: '" + value + "' cant be interpret like int.");
        }
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

    public String getFormula() {
        if (isFormula()) {
            return value.substring(1);//Skip FORMULA_PREFIX
        }
        throw new UnsupportedOperationException("The cell value: '" + value + "' isn't formula. ");
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
