package com.processor.computation;

import com.processor.common.Cell;
import com.processor.computation.parser.FormulaParseException;
import com.processor.computation.parser.Syntax;
import com.processor.computation.parser.Syntax.FormulaPart;

import java.util.*;
import java.util.stream.Collectors;

public class Processor {

    private static final Map<String, ArithmeticOperation> operations = new HashMap<>(4);

    static {
        ArithmeticOperation addition = (firstArg, secondArg) -> firstArg + secondArg;
        ArithmeticOperation subtraction = (firstArg, secondArg) -> firstArg - secondArg;
        ArithmeticOperation multiplication = (firstArg, secondArg) -> firstArg * secondArg;
        ArithmeticOperation division = (firstArg, secondArg) -> firstArg / secondArg;

        operations.put("+", addition);
        operations.put("-", subtraction);
        operations.put("*", multiplication);
        operations.put("/", division);
    }


    private Map<String, Cell> tableCells;

    private Map<String, Integer> resultsCache = new HashMap<>();

    public Processor(Map<String, Cell> tableCells) {
        this.tableCells = Collections.unmodifiableMap(tableCells);
    }

    public Map<String, String> processCells() {
        return tableCells.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> evaluate(e.getValue())));
    }

    String evaluate(Cell cell) {
        String result;
        if (cell.isText()) {
            result = cell.getTextValue();
        } else if (cell.isIntValue()) {
            result = cell.getValue();//no any transformations are required
        } else if (cell.isFormula()) {
            try {
                result = String.valueOf(evaluateToInt(cell));
            } catch (FormulaParseException e) {
                result = e.getMessage();
            }
        } else {
            throw new IllegalArgumentException("Strange cell: " + cell);
        }

        return result;
    }

    private int evaluateToInt(Cell cell) throws FormulaParseException {
        int result;
        if (cell.isFormula()) {
            Integer cachedValue = resultsCache.get(cell.getName());
            if (cachedValue == null) {
                result = evaluateFormula(cell.getFormula());
                resultsCache.put(cell.getName(), result);
            } else {
                result = cachedValue;
            }
        } else if (cell.isIntValue()) {
            result = cell.getIntValue();
        } else {
            //Text cell
            throw new FormulaParseException("Cell " + cell.getValue() + " should be text or formula.");
        }

        return result;
    }

    int evaluateFormula(String formula) throws FormulaParseException {
        Syntax syntax = new Syntax(formula);
        syntax.checkFormulaSyntax();

        List<FormulaPart> formulaParts = syntax.getParts();
        if (formulaParts.isEmpty()) {
            throw new FormulaParseException("Formula should't be empty");
        }


        Iterator<FormulaPart> partsIterator = formulaParts.iterator();
        int result = processFormulaPart(partsIterator.next());

        while (partsIterator.hasNext()) {
            FormulaPart operation = partsIterator.next();
            ArithmeticOperation arithmeticOperation = operations.get(operation.getLexeme());

            FormulaPart nextOperand = partsIterator.next();
            int operandValue = processFormulaPart(nextOperand);

            result = arithmeticOperation.apply(result, operandValue);
        }

        return result;
    }

    private int processFormulaPart(FormulaPart part) throws FormulaParseException {
        int result;
        switch (part.getType()) {
            case CONSTANT:
                result = part.getConstValue();
                break;
            case REFERENCE:
                Cell cell = tableCells.get(part.getLexeme());
                if (cell == null) {
                    throw new FormulaParseException("Wrong reference '" + part.getLexeme() + "'");
                }
                result = evaluateToInt(cell);
                break;
            default:
                throw new FormulaParseException("The part " + part + " can't be processed to int.");
        }

        return result;
    }

    @FunctionalInterface
    private interface ArithmeticOperation {
        int apply(int firstArg, int secondArg);
    }
}
