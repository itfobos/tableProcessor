package com.processor.computation.parser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class FormulaBufferTest {

    @Test
    public void correctReadingTest() {
        final String sourceText = "some text we're going to read";

        FormulaBuffer buffer = new FormulaBuffer(sourceText);

        char currentChar;
        StringBuilder stringBuilder = new StringBuilder();
        while ((currentChar = buffer.read()) != FormulaBuffer.EOF) {
            stringBuilder.append(currentChar);
        }

        assertThat(stringBuilder.toString(), equalTo(sourceText));
    }

    @Test
    public void lookAheadTest() {
        char someSymbol = 'a';
        char aheadSymbol = 'd';
        FormulaBuffer buffer = new FormulaBuffer(String.valueOf(new char[]{someSymbol, aheadSymbol}));

        assertThat(buffer.lookAhead(), equalTo(someSymbol));
        buffer.read();

        assertThat(buffer.lookAhead(), equalTo(aheadSymbol));
    }
}
