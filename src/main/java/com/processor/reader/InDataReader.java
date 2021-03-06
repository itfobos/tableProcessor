package com.processor.reader;

import com.processor.common.Cell;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class InDataReader {

    private static final int MAX_WIDTH = 26;// alphabet size
    private static final int SIZE_PARAMS_AMOUNT = 2;
    public static final String DELIM = "\t";
    private static final int HEIGHT_POSITION = 0;
    private static final int WIDTH_POSITION = 1;

    int height;
    int width;

    /**
     * The table keeps mapping "cell refs(A1, B4, so on)" <--> "Cell object"
     */
    private LinkedHashMap<String, Cell> tableCells = new LinkedHashMap<>();

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
    }

    void readTableBody(BufferedReader bufferedReader) throws IOException, InDataFormatException {
        for (int lineNumber = 1; lineNumber <= height; lineNumber++) {
            String currLine = bufferedReader.readLine();

            checkSizeRestrictions(currLine);

            List<String> tokens = parseBodyLine(currLine);
            for (int columnNumber = 0; columnNumber < width; columnNumber++) {
                char columnChar = (char) ('A' + columnNumber);
                String cellReference = String.valueOf(columnChar) + lineNumber;

                tableCells.put(cellReference, new Cell(cellReference, tokens.get(columnNumber)));
            }
        }
    }

    private void debugLog(String message) {
        System.out.println("Debug: " + message);
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

        if (currLine.isEmpty() && width > 1/*in line is just one empty cell*/) {
            throw new InDataFormatException("Empty line has been read");
        }

        List<String> tokens = parseBodyLine(currLine);
        if (tokens.size() != width) {
            throw new InDataFormatException("Wrong amount(" + tokens.size() + ") of cells in the line: " + currLine);
        }
    }

    private List<String> parseBodyLine(final String line) {
        List<String> result = new ArrayList<>(width);

        int lastIndex = 0;
        int currentIndex;

        do {
            currentIndex = line.indexOf(DELIM, lastIndex);
            currentIndex = currentIndex >= 0 ? currentIndex : line.length();

            String currentToken = line.substring(lastIndex, currentIndex);
            result.add(currentToken);

            lastIndex = currentIndex + 1;
        } while (currentIndex < line.length());

        return result;
    }

    void readTableSize(String line) throws InDataFormatException {
        List<String> tokens = parseBodyLine(line);

        if (tokens.size() != SIZE_PARAMS_AMOUNT) {
            throw new InDataFormatException("Wrong amount of input params. First line should contains only height an width: " + line);
        }

        height = Integer.valueOf(tokens.get(HEIGHT_POSITION));
        if (height <= 0) {
            throw new InDataFormatException("Height should be grater than zero.");
        }

        width = Integer.valueOf(tokens.get(WIDTH_POSITION));
        if (width <= 0) {
            throw new InDataFormatException("Width should be grater than zero.");
        }

        if (width > MAX_WIDTH) {
            throw new InDataFormatException("Width should be less than " + MAX_WIDTH);
        }
    }

    public LinkedHashMap<String, Cell> getTableCells() {
        return tableCells;
    }

    public void debugPrintCells() {
        tableCells.entrySet().forEach(entry -> System.out.println(entry.getKey() + "  " + entry.getValue()));
    }

    public int getWidth() {
        return width;
    }
}
