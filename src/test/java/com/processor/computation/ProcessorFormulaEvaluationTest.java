package com.processor.computation;

import com.processor.computation.parser.FormulaParseException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProcessorFormulaEvaluationTest {

    private static final String FORMULA_REFERENCE = "A1";

    private CellBuilder cellBuilder;
    private Processor processor;

    @Before
    public void procInit() {
        cellBuilder = new CellBuilder();

        processor = new Processor(cellBuilder.getCells());
    }

    @Test(expected = FormulaParseException.class)
    public void wrongReferenceTest() throws FormulaParseException {

        cellBuilder.addCell(FORMULA_REFERENCE, "=B1");

        getCellAndEvaluateFormula(FORMULA_REFERENCE);
    }

    @Test
    public void operationOrderTest() throws FormulaParseException {
        cellBuilder.addCell(FORMULA_REFERENCE, "=2+4/3*7");

        int result = getCellAndEvaluateFormula(FORMULA_REFERENCE);

        assertThat(result, equalTo(((2 + 4) / 3) * 7));
    }

    private int getCellAndEvaluateFormula(String FORMULA_REFERENCE) throws FormulaParseException {
        String formula = cellBuilder.getCells().get(FORMULA_REFERENCE).getFormula();
        return processor.evaluateFormula(formula);
    }

    @Test
    public void justConstantsTest() throws FormulaParseException {
        final int firstOperand = 5;
        final int secondOperand = 3;

        cellBuilder.addCell(FORMULA_REFERENCE, "=" + firstOperand + "+" + secondOperand);

        int result = getCellAndEvaluateFormula(FORMULA_REFERENCE);

        assertThat(result, equalTo(firstOperand + secondOperand));
    }

    @Test
    public void lowerCaseReferenceTest() throws FormulaParseException {
        final int addition = 5;
        final int constValue = 3;

        cellBuilder
                .addCell(FORMULA_REFERENCE, "=b1+" + addition)
                .addCell("B1", Integer.toString(constValue));

        int result = getCellAndEvaluateFormula(FORMULA_REFERENCE);

        assertThat(result, equalTo(constValue + addition));
    }

    @Test
    public void divisionCorrectOperandsOrderTest() throws FormulaParseException {
        final int secondOperand = 5;
        final int constValue = 15;


        cellBuilder
                .addCell(FORMULA_REFERENCE, "=b1/" + secondOperand)
                .addCell("B1", Integer.toString(constValue));

        int result = getCellAndEvaluateFormula(FORMULA_REFERENCE);

        assertThat(result, equalTo(constValue / secondOperand));
    }

}
