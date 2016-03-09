package com.processor.computation.parser;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertThat(stringBuilder.toString(), CoreMatchers.equalTo(sourceText));
    }
}
