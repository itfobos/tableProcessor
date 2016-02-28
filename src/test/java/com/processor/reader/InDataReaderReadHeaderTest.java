package com.processor.reader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class InDataReaderReadHeaderTest {
    InDataReader dataReader;

    @Before
    public void init() {
        dataReader = new InDataReader();
    }

    @Test
    public void readTableSizeTest() throws InDataFormatException {
        final int height = 4;
        final int width = 7;
        String testLine = height + InDataReader.DELIM + width;

        dataReader.readTableSize(testLine);

        assertThat(dataReader.getHeight(), equalTo(height));
        assertThat(dataReader.getWidth(), equalTo(width));
    }

    @Test(expected = InDataFormatException.class)
    public void positiveHeightTest() throws InDataFormatException {
        final int height = -4;
        final int width = 7;

        String testLine = height + InDataReader.DELIM + width;

        dataReader.readTableSize(testLine);
    }

    @Test(expected = InDataFormatException.class)
    public void positiveWidthTest() throws InDataFormatException {
        final int height = 4;
        final int width = 0;

        String testLine = height + InDataReader.DELIM + width;

        dataReader.readTableSize(testLine);
    }
}
