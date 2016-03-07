package com.processor.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class InDataReader {

    static final int MAX_WIDTH = 26;// alphabet size
    static final int MAX_HEIGHT = 10; // [A-Za-z][0-9]
    static final int SIZE_PARAMS_AMOUNT = 2;
    static final String DELIM = "\t";
    public static final int HEIGHT_POSITION = 0;
    public static final int WIDTH_POSITION = 1;

    int height;
    int width;

    /**
     * The table keeps mapping "cell refs(A1, B4, so on)" <--> "Cell object"
     */
    private HashMap<String, Cell> tableCells = new LinkedHashMap<>();

    /**
     * Input format is:
     * <p>
     * <pre>
     * <i>height width
     * Cell  values  separated   by  tabs
     * ...
     * ...</i>
     * </pre>
     */
    public void readTableData(BufferedReader bufferedReader) throws IOException, InDataFormatException {
        readTableSize(bufferedReader.readLine());

        readTableBody(bufferedReader);

        if (bufferedReader.readLine() != null) {
            throw new InDataFormatException("Actual table has more lines than defined in header.");
        }
    }

    void readTableBody(BufferedReader bufferedReader) throws IOException, InDataFormatException {
        for (int lineNumber = 1; lineNumber <= height; lineNumber++) {
            String currLine = bufferedReader.readLine();

            checkSizeRestrictions(currLine);

            String[] tokens = currLine.split(DELIM);
            for (int columnNumber = 0; columnNumber < width; columnNumber++) {
                char columnChar = (char) ('A' + columnNumber);
                String cellReference = String.valueOf(columnChar) + lineNumber;

                tableCells.put(cellReference, new Cell(tokens[columnNumber]));
            }
        }
    }

    private static StringTokenizer createTokenizer(String currLine) {
        return new StringTokenizer(currLine, DELIM);
    }

    /**
     * Checks restrictions :
     * <ul>
     * <li>Table height: if table has less lines than defined, <i>currLine</i>
     * will be nullable.</li>
     * <li>Width check: amount of cells in each line should be equals to table
     * width.</li>
     * </ul>
     *
     * @throws InDataFormatException if some restriction is violated
     */
    private void checkSizeRestrictions(String currLine) throws InDataFormatException {
        if (currLine == null) {
            throw new InDataFormatException("Actual table has less lines, than defined in header");
        }

        if (currLine.trim().isEmpty()) {
            throw new InDataFormatException("Empty line has been read");
        }

        String[] tokens = currLine.split(DELIM);
        if (tokens.length != width) {
            throw new InDataFormatException("Wrong amount of cells in the line: " + currLine);
        }
    }

    void readTableSize(String line) throws InDataFormatException {
        String[] tokens = line.split(DELIM);

        if (tokens.length != SIZE_PARAMS_AMOUNT) {
            throw new InDataFormatException("Wrong amount of input params. First line should contains only height an width: " + line);
        }

        height = Integer.valueOf(tokens[HEIGHT_POSITION]);
        if (height <= 0) {
            throw new InDataFormatException("Height should be grater than zero.");
        }

        width = Integer.valueOf(tokens[WIDTH_POSITION]);
        if (width <= 0) {
            throw new InDataFormatException("Width should be grater than zero.");
        }

        if (width > MAX_WIDTH) {
            throw new InDataFormatException("Width should be less than " + MAX_WIDTH);
        }

        if (height > MAX_HEIGHT) {
            throw new InDataFormatException("Width should be less than " + MAX_WIDTH);
        }
    }

    public HashMap<String, Cell> getTableCells() {
        return tableCells;
    }
}
