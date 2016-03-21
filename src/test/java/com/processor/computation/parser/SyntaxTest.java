package com.processor.computation.parser;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SyntaxTest {

    @Test
    public void justConstantsTest() throws FormulaParseException {
        String firstConst = "12";
        String secondConst = "16";
        String operation = "+";

        Syntax syntax = new Syntax(firstConst + operation + secondConst);
        syntax.checkFormulaSyntax();

        List<Syntax.FormulaPart> parts = syntax.getParts();

        assertThat(parts.get(0).getConstValue(), equalTo(Integer.valueOf(firstConst)));
        assertThat(parts.get(1).getLexeme(), equalTo(operation));
        assertThat(parts.get(2).getConstValue(), equalTo(Integer.valueOf(secondConst)));
    }

    @Test(expected = FormulaParseException.class)
    public void operationsOnlyTest() throws FormulaParseException {
        Syntax syntax = new Syntax("+++");
        syntax.checkFormulaSyntax();
    }

    @Test(expected = FormulaParseException.class)
    public void noLastOperandTest() throws FormulaParseException {
        Syntax syntax = new Syntax("A12+");
        syntax.checkFormulaSyntax();
    }

    @Test(expected = FormulaParseException.class)
    public void noFirstOperandTest() throws FormulaParseException {
        Syntax syntax = new Syntax("+A12");
        syntax.checkFormulaSyntax();
    }

    @Test
    public void emptyFormulaTest() throws FormulaParseException {
        Syntax syntax = new Syntax("");
        syntax.checkFormulaSyntax();

        assertTrue(syntax.getParts().isEmpty());
    }

    @Test
    public void correctFormulaTestTest() throws FormulaParseException {
        final String reference = "A12";
        final String operation = "*";
        final int constant = 7;

        Syntax syntax = new Syntax(reference + operation + constant);

        syntax.checkFormulaSyntax();
        List<Syntax.FormulaPart> formulaParts = syntax.getParts();

        assertThat(formulaParts.get(0).getLexeme(), equalTo(reference));
        assertThat(formulaParts.get(1).getLexeme(), equalTo(operation));
        assertThat(formulaParts.get(2).getConstValue(), equalTo(constant));
    }
}
