package com.processor.computation.parser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class LexerTest {

    @Test
    public void justConstantsTest() throws FormulaParseException {
        String firstConst = "5";
        String secondConst = "8";
        String operation = "+";

        Lexer lexer = new Lexer(firstConst + operation + secondConst);

        assertThat(lexer.getLexeme(), equalTo(firstConst));
        assertThat(lexer.getLexeme(), equalTo(operation));
        assertThat(lexer.getLexeme(), equalTo(secondConst));
        assertThat(lexer.getLexeme(), nullValue());
    }

    @Test(expected = FormulaParseException.class)
    public void parseDigitsLeadingNotNumberTest() throws FormulaParseException {
        Lexer lexer = new Lexer("123s23");
        lexer.getLexeme();
    }

    @Test
    public void parseNumberWithOperationTest() throws FormulaParseException {
        String sourceNumber = "12";
        String operation = "-";

        Lexer lexer = new Lexer(sourceNumber + operation);

        assertThat(lexer.getLexeme(), equalTo(sourceNumber.toUpperCase()));
        assertThat(lexer.getLexeme(), equalTo(operation));
    }

    @Test
    public void parseNumberOnlyTest() throws FormulaParseException {
        String sourceNumber = "1243343";
        Lexer lexer = new Lexer(sourceNumber);

        assertThat(lexer.getLexeme(), equalTo(sourceNumber));
        assertThat(lexer.getLexeme(), nullValue());
    }

    @Test
    public void parseReferenceOnlyTest() throws FormulaParseException {
        String sourceReference = "A12";
        Lexer lexer = new Lexer(sourceReference);

        assertThat(lexer.getLexeme(), equalTo(sourceReference));
        assertThat(lexer.getLexeme(), nullValue());
    }

    @Test
    public void parseReferenceWithOperationTest() throws FormulaParseException {
        String sourceReference = "a12";
        String operation = "-";

        Lexer lexer = new Lexer(sourceReference + operation);

        assertThat(lexer.getLexeme(), equalTo(sourceReference.toUpperCase()));
        assertThat(lexer.getLexeme(), equalTo(operation));
    }


    @Test(expected = FormulaParseException.class)
    public void parseWrongReferenceTest() throws FormulaParseException {
        Lexer lexer = new Lexer("a12D34");
        lexer.getLexeme();
    }
}
