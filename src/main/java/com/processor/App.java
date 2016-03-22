package com.processor;

import com.processor.computation.Processor;
import com.processor.reader.InDataFormatException;
import com.processor.reader.InDataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class App {
    public static void main(String[] args) throws IOException, InDataFormatException {

        InDataReader inDataReader = new InDataReader();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            inDataReader.readTableData(reader);
        }

        Processor processor = new Processor(inDataReader.getTableCells());
        Map<String, String> results = processor.processCells();

        printResultTable(inDataReader.getTableCells().keySet(), results, inDataReader.getWidth());
    }

    private static void printResultTable(Set<String> cellNames, Map<String, String> results, int tableWidth) {
        StringJoiner stringJoiner = new StringJoiner(InDataReader.DELIM, "\n", "");
        int counter = 0;
        for (String name : cellNames) {
            if (counter++ % tableWidth == 0) {
                System.out.print(stringJoiner.toString());
                stringJoiner = new StringJoiner(InDataReader.DELIM, "\n", "");
            }
            stringJoiner.add(results.get(name));
        }

        System.out.print(stringJoiner.toString());
    }
}
