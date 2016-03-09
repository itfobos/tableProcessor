package com.processor.computation.parser;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

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

        Assert.assertThat(stringBuilder.toString(), equalTo(sourceText));
    }

    @Test
    public void lookAheadTest() {
        final String sourceText = "some text we're going to look ahead";

        FormulaBuffer buffer = new FormulaBuffer(sourceText);

        char currentChar;
        StringBuilder stringBuilder = new StringBuilder();
        while ((currentChar = buffer.lookAhead()) != FormulaBuffer.EOF) {
            buffer.read();//just shifting reading position
            stringBuilder.append(currentChar);
        }

        Assert.assertThat(stringBuilder.toString(), equalTo(sourceText.substring(1)));
    }
}
