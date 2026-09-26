import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class TranspileErrorListener extends BaseErrorListener
{
    public static final TranspileErrorListener INSTANCE = new TranspileErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new TranspilationException("Συντακτικό Σφάλμα [Γραμμή " + line + ":" + charPositionInLine + "] - " + msg);
    }
}
