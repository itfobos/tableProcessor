package com.processor.computation;

import com.processor.common.Cell;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;

public class Processor {

    private Map<String, ArithmeticOperation> operations = new HashMap<>(4);


    private HashMap<String, Cell> tableCells;

    public Processor(HashMap<String, Cell> tableCells) {
        this.tableCells = tableCells;

        ArithmeticOperation addition = (firstArg, secondArg) -> firstArg.getAsInt() + secondArg.getAsInt();
        ArithmeticOperation subtraction = (firstArg, secondArg) -> firstArg.getAsInt() - secondArg.getAsInt();
        ArithmeticOperation multiplication = (firstArg, secondArg) -> firstArg.getAsInt() * secondArg.getAsInt();
        ArithmeticOperation division = (firstArg, secondArg) -> firstArg.getAsInt() / secondArg.getAsInt();

        operations.put("+", addition);
        operations.put("-", subtraction);
        operations.put("*", multiplication);
        operations.put("/", division);
    }

    public String evaluate(Cell cell) {
        String result = null;
        if (cell.isText()) {
            result = cell.getTextValue();
        } else if (cell.isIntValue()) {
            result = cell.getValue();//no any transformations are required
        } else if (cell.isFormula()) {
            result = String.valueOf(evaluateToInt(cell));
        } else {
            throw new IllegalArgumentException("Strange cell: " + cell);
        }
        
        return result;
    }

    public int evaluateToInt(Cell cell) {
        //TODO:
        return 0;
    }

    @FunctionalInterface
    private interface ArithmeticOperation {
        int apply(IntSupplier firstArg, IntSupplier secondArg);
    }
}
