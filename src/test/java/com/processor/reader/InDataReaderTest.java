package com.processor.reader;

import static com.processor.reader.InDataReader.DELIM;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

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

        final String sourceLine = "2" + DELIM + " " + DELIM + "=A1"; //empty cell in the middle

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(sourceLine.getBytes())))) {
            dataReader.readTableBody(bufferedReader);
        }

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
