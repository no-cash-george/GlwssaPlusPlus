import javafx.application.Platform;
import javafx.scene.control.TextArea;
import java.io.OutputStream;

public class ConsoleStream extends OutputStream {
    private final TextArea console;

    public ConsoleStream(TextArea console) {
        this.console = console;
    }

    @Override
    public void write(int b) {
        // Append the character to the JavaFX TextArea safely on the UI thread
        Platform.runLater(() -> {
            console.appendText(String.valueOf((char) b));
            // Optional: Auto-scroll to bottom
            console.positionCaret(console.getLength());
        });
    }

    @Override
    public void write(byte[] b, int off, int len) {
        String text = new String(b, off, len);
        Platform.runLater(() -> {
            console.appendText(text);
            console.positionCaret(console.getLength());
        });
    }
}