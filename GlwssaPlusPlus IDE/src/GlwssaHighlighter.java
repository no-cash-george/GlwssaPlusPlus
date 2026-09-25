import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;

public class GlwssaHighlighter {

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {
        GlwssaLexer lexer = new GlwssaLexer(CharStreams.fromString(text));

        lexer.removeErrorListeners();

        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        int lastTokenEnd = 0;

        for (Token t = lexer.nextToken(); t.getType() != Token.EOF; t = lexer.nextToken()) {
            int start = t.getStartIndex();
            int end = t.getStopIndex() + 1;
            int type = t.getType();

            String styleClass = getStyleClassForToken(type);

            if (start > lastTokenEnd) {
                spansBuilder.add(Collections.emptyList(), start - lastTokenEnd);
            }

            spansBuilder.add(Collections.singleton(styleClass), end - start);
            lastTokenEnd = end;
        }

        if (text.length() > lastTokenEnd) {
            spansBuilder.add(Collections.emptyList(), text.length() - lastTokenEnd);
        }

        return spansBuilder.create();
    }

    private static String getStyleClassForToken(int tokenType) {
            switch (tokenType) {
                // Keywords & Data Types
                case GlwssaLexer.PROGRAM_KW:
                case GlwssaLexer.VARS_KW:
                case GlwssaLexer.START_KW:
                case GlwssaLexer.END_KW:
                case GlwssaLexer.PRINT_KW:
                case GlwssaLexer.READ_KW:
                case GlwssaLexer.IF_KW:
                case GlwssaLexer.THEN_KW:
                case GlwssaLexer.ELSE_IF_KW:
                case GlwssaLexer.ELSE_KW:
                case GlwssaLexer.END_IF_KW:
                case GlwssaLexer.SELECT_KW:
                case GlwssaLexer.CASE_KW:
                case GlwssaLexer.END_SELECT_KW:
                case GlwssaLexer.WHILE_KW:
                case GlwssaLexer.DO_KW:
                case GlwssaLexer.END_LOOP_KW:
                case GlwssaLexer.START_LOOP_KW:
                case GlwssaLexer.UNTIL_KW:
                case GlwssaLexer.START_FOR_KW:
                case GlwssaLexer.FROM_KW:
                case GlwssaLexer.TO_KW:
                case GlwssaLexer.STEP_KW:
                case GlwssaLexer.FUNCTION_KW:
                case GlwssaLexer.END_FUNCTION_KW:
                case GlwssaLexer.PROCEDURE_KW:
                case GlwssaLexer.END_PROCEDURE_KW:
                case GlwssaLexer.CALL_KW:
                case GlwssaLexer.TYPE_KW:
                case GlwssaLexer.RETURN_TYPE_KW:
                case GlwssaLexer.TRUE_KW:
                case GlwssaLexer.FALSE_KW:
                    return "keyword";

                // Operators & Math Keywords
                case GlwssaLexer.ASSIGN:
                case GlwssaLexer.PLUS:
                case GlwssaLexer.MINUS:
                case GlwssaLexer.MULT:
                case GlwssaLexer.SLASH:
                case GlwssaLexer.DIV_KW:
                case GlwssaLexer.MOD_KW:
                case GlwssaLexer.EQ:
                case GlwssaLexer.NEQ:
                case GlwssaLexer.LT:
                case GlwssaLexer.GT:
                case GlwssaLexer.LTE:
                case GlwssaLexer.GTE:
                    return "operator";

                // Primitives
                case GlwssaLexer.NUMBER:
                    return "number";

                case GlwssaLexer.STRING:
                    return "string";

                case GlwssaLexer.COMMENT:
                    return "comment";

                // Identifiers, Brackets, and unrecognized tokens fall back to standard text color
                default:
                    return "default";
            }
        }
}