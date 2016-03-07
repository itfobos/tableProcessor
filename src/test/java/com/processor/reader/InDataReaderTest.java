package com.processor.reader;

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
    public void readTableBodyEmptyCellTest() throws IOException, InDataFormatException {
        dataReader.height = 1;
        dataReader.width = 3;

        final String a1CellValue = "2";
        String b1CellValue = "";
        String c1CellValue = "=A1";
        final String sourceLine = a1CellValue + DELIM + b1CellValue + DELIM + c1CellValue;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(sourceLine.getBytes())))) {
            dataReader.readTableBody(bufferedReader);
        }

        HashMap<String, Cell> tableCells = dataReader.getTableCells();
        assertThat(tableCells.size(), equalTo(3)/* Cells amount in source line */);

        assertThat(tableCells.get("A1").getValue(), equalTo(a1CellValue));
        assertThat(tableCells.get("B1").getValue(), equalTo(b1CellValue));
        assertThat(tableCells.get("C1").getValue(), equalTo(c1CellValue));
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
