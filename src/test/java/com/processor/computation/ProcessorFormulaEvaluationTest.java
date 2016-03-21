package com.processor.computation;

import com.processor.common.Cell;
import com.processor.computation.parser.FormulaParseException;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProcessorFormulaEvaluationTest {

    @Test
    public void wrongReferenceTest() {
        Assert.fail("TODO: Unfinished test");
    }

    @Test
    public void operationOrderTest() {
        Assert.fail("TODO: Unfinished test");
    }

    @Test
    public void justConstantsTest() throws FormulaParseException {
        final int firstOperand = 5;
        final int secondOperand = 3;

        String formulaReference = "A1";

        CellBuilder cellBuilder = new CellBuilder()
                .addCell(formulaReference, "=" + firstOperand + "+" + secondOperand);

        Processor processor = new Processor(cellBuilder.getCells());

        String formula = cellBuilder.getCells().get(formulaReference).getFormula();
        int result = processor.evaluateFormula(formula);

        assertThat(result, equalTo(firstOperand + secondOperand));
    }

    @Test
    public void lowerCaseReferenceTest() throws FormulaParseException {
        final int addition = 5;
        final int constValue = 3;

        String formulaReference = "A1";

        CellBuilder cellBuilder = new CellBuilder()
                .addCell(formulaReference, "=b1+" + addition)
                .addCell("B1", Integer.toString(constValue));

        Processor processor = new Processor(cellBuilder.getCells());

        String formula = cellBuilder.getCells().get(formulaReference).getFormula();
        int result = processor.evaluateFormula(formula);

        assertThat(result, equalTo(constValue + addition));
    }

    @Test
    public void divisionCorrectOperandsOrderTest() throws FormulaParseException {
        final int secondOperand = 5;
        final int constValue = 15;

        String formulaReference = "A1";

        CellBuilder cellBuilder = new CellBuilder()
                .addCell(formulaReference, "=b1/" + secondOperand)
                .addCell("B1", Integer.toString(constValue));

        Processor processor = new Processor(cellBuilder.getCells());

        String formula = cellBuilder.getCells().get(formulaReference).getFormula();
        int result = processor.evaluateFormula(formula);

        assertThat(result, equalTo(constValue / secondOperand));
    }

    private static class CellBuilder {
        HashMap<String, Cell> tableCells = new HashMap<>();

        public CellBuilder addCell(String name, String value) {
            Cell cell = new Cell(name, value);
            tableCells.put(cell.getName(), cell);
            return this;
        }

        public HashMap<String, Cell> getCells() {
            return tableCells;
        }
    }
}
