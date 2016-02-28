package com.processor.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

public class InDataReaderIntegration {
    InDataReader dataReader;

    @Before
    public void init() {
        dataReader = new InDataReader();
    }

    @Test
    public void readTableAndPrint() throws IOException, InDataFormatException {
        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream("test_table_1.txt");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceStream))) {
            dataReader.readTableData(bufferedReader);
        }

        dataReader.getTableCells().entrySet().forEach(entry -> System.out.println(entry.getKey() + "  " + entry.getValue()));
    }
}
