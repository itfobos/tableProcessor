package com.processor.reader;

//TODO:
public class Cell {

    private String value;

    public Cell(String value) {
        this.value = value;
    }

    public int getIntValue() {
        // TODO:
        return 0;
    }

    public String getTextValue() {
        // TODO:
        return "";
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Cell{" + "value='" + value + '\'' + '}';
    }
}
