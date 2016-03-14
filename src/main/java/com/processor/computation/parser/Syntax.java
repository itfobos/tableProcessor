package com.processor.computation.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Syntax {
    private Lexer lexer;
    private List<FormulaPart> parts = new LinkedList<>();

    public Syntax(String formula) {
        this.lexer = new Lexer(formula);
    }

    public void checkFormulaSyntax() throws FormulaParseException {
        parseRefOrConst();
    }

    boolean parseRefOrConst() throws FormulaParseException {
        String lexeme = lexer.getLexeme();
        if (lexeme == null) {
            return false;
        }

        FormulaPart constOrRefPart;
        if (LexemeUtils.isEnLetter(lexeme.charAt(0))) {
            constOrRefPart = FormulaPart.reference(lexeme);
        } else if (LexemeUtils.isNumeric(lexeme)) {
            constOrRefPart = FormulaPart.constant(Integer.valueOf(lexeme));
        } else {
            throw new FormulaParseException("Unexpected construction: " + lexeme + " It should be reference or constant here.");
        }
        parts.add(constOrRefPart);

        lexeme = lexer.getLexeme();
        if (lexeme != null && LexemeUtils.isArithmeticOperation(lexeme.charAt(0))) {
            parts.add(FormulaPart.operation(lexeme));

            boolean isWhereRefOrConstNext = parseRefOrConst();
            if (!isWhereRefOrConstNext) {
                String formulaLocalizedPart = constOrRefPart.lexeme + lexeme;
                throw new FormulaParseException("It should be reference or constant after construction: " + formulaLocalizedPart);
            }
        }

        return true;
    }

    public List<FormulaPart> getParts() {
        return Collections.unmodifiableList(parts);//TODO: is it really required?
    }

    public static class FormulaPart {
        private String lexeme;
        private int constValue;
        private PartType type;

        public static FormulaPart constant(int constValue) {
            return new FormulaPart(constValue, PartType.CONSTANT);
        }

        public static FormulaPart operation(String lexeme) {
            return new FormulaPart(lexeme, PartType.OPERATION);
        }

        public static FormulaPart reference(String lexeme) {
            return new FormulaPart(lexeme, PartType.REFERENCE);
        }

        private FormulaPart(String lexeme, PartType type) {
            this.lexeme = lexeme;
            this.type = type;
        }

        private FormulaPart(int constValue, PartType type) {
            this.constValue = constValue;
            this.type = type;
        }

        public int getConstValue() {
            return constValue;
        }

        public String getLexeme() {
            return lexeme;
        }

        public PartType getType() {
            return type;
        }
    }

    public enum PartType {
        REFERENCE,
        OPERATION,
        CONSTANT
    }
}
