package com.processor.computation;

import com.processor.common.Cell;

import java.util.HashMap;

class CellBuilder {
    private HashMap<String, Cell> tableCells = new HashMap<>();

    CellBuilder addCell(String name, String value) {
        Cell cell = new Cell(name, value);
        tableCells.put(cell.getName(), cell);
        return this;
    }

    HashMap<String, Cell> getCells() {
        return tableCells;
    }
}
