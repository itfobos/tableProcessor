package com.processor.computation;

import com.processor.common.Cell;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProcessorTest {

    private static final String FORMULA_REFERENCE = "A1";

    private CellBuilder cellBuilder;
    private Processor processor;

    @Before
    public void procInit() {
        cellBuilder = new CellBuilder();

        processor = new Processor(cellBuilder.getCells());
    }

    @Test
    public void textCellTest() {
        final String srcValue = "some text";
        cellBuilder.addCell(FORMULA_REFERENCE, "'" + srcValue);

        Cell cell = cellBuilder.getCells().get(FORMULA_REFERENCE);
        String result = processor.evaluate(cell);

        assertThat(result, equalTo(srcValue));
    }

    @Test
    public void constCellTest() {
        final Integer srcValue = 8;
        cellBuilder.addCell(FORMULA_REFERENCE, srcValue.toString());

        Cell cell = cellBuilder.getCells().get(FORMULA_REFERENCE);
        String result = processor.evaluate(cell);

        assertThat(result, equalTo(srcValue.toString()));
    }
}
