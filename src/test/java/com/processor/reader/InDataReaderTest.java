package com.processor.reader;

import com.processor.common.Cell;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import static com.processor.reader.InDataReader.DELIM;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class InDataReaderTest {
    InDataReader dataReader;

    @Before
    public void init() {
        dataReader = new InDataReader();
    }

    @Test
    public void onlyEmptyCellsLineTest() throws IOException, InDataFormatException {
        createOneLineTableTest("", "", "");
    }

    @Test
    public void readTableBodyLastEmptyCellTest() throws IOException, InDataFormatException {
        createOneLineTableTest("2", "=A1", "");
    }

    @Test
    public void readTableBodyEmptyCellInTheMiddleTest() throws IOException, InDataFormatException {
        createOneLineTableTest("1", "", "=A1");
    }

    @Test
    public void readTableBodyFirstEmptyCellTest() throws IOException, InDataFormatException {
        createOneLineTableTest("", "1", "=A1");
    }

    private void createOneLineTableTest(String a1Value, String b1Value, String c1Value) throws IOException, InDataFormatException {
        dataReader.height = 1;
        dataReader.width = 3;


        final String sourceLine = a1Value + DELIM + b1Value + DELIM + c1Value;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(sourceLine.getBytes())))) {
            dataReader.readTableBody(bufferedReader);
        }

        HashMap<String, Cell> tableCells = dataReader.getTableCells();
        assertThat(tableCells.size(), equalTo(3)/* Cells amount in source line */);

        assertThat(tableCells.get("A1").getValue(), equalTo(a1Value));
        assertThat(tableCells.get("B1").getValue(), equalTo(b1Value));
        assertThat(tableCells.get("C1").getValue(), equalTo(c1Value));
    }

    @Test
    public void readTableSizeTest() throws InDataFormatException {
        final int height = 4;
        final int width = 7;
        String testLine = height + DELIM + width;

        dataReader.readTableSize(testLine);

        assertThat(dataReader.height, equalTo(height));
        assertThat(dataReader.width, equalTo(width));
    }

    @Test(expected = InDataFormatException.class)
    public void positiveHeightTest() throws InDataFormatException {
        final int height = -4;
        final int width = 7;

        String testLine = height + DELIM + width;

        dataReader.readTableSize(testLine);
    }

    @Test(expected = InDataFormatException.class)
    public void positiveWidthTest() throws InDataFormatException {
        final int height = 4;
        final int width = 0;

        String testLine = height + DELIM + width;

        dataReader.readTableSize(testLine);
    }
}
