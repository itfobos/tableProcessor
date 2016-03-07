package com.processor;

import com.processor.reader.InDataFormatException;
import com.processor.reader.InDataReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) throws IOException, InDataFormatException {

        System.out.println("Hi man");
        InDataReader inDataReader = new InDataReader();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            inDataReader.readTableData(reader);
        }

        inDataReader.debugPrintCells();
        System.out.print("Done");//TODO: remove
    }
}
