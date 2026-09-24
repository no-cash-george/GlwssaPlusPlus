import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InteractiveConsole extends TextArea {
    private int inputStart = 0;
    private final PipedOutputStream userOut;
    private final ExecutorService ioExecutor;

    public InteractiveConsole() throws IOException {
        this.userOut = new PipedOutputStream();
        PipedInputStream systemIn = new PipedInputStream(userOut);

        // Single background thread to handle writing to the pipe without freezing UI
        this.ioExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "Console-Input-Thread");
            t.setDaemon(true);
            return t;
        });

        System.setIn(systemIn);

        setStyle("-fx-font-family: 'Consolas', monospace; -fx-background-color: #2b2b2b; -fx-control-inner-background: #2b2b2b; -fx-text-fill: #a9b7c6; -fx-font-size: 14px;");
        setWrapText(true);

        // Utilize a buffered output stream to prevent Platform.runLater flooding
        ConsoleOutputStream consoleOut = new ConsoleOutputStream();
        PrintStream printStream = new PrintStream(consoleOut, true, StandardCharsets.UTF_8);
        System.setOut(printStream);
        System.setErr(printStream);

        // TextFormatter is impenetrable; it intercepts all modifications (typing, pasting, deleting)
        setTextFormatter(new TextFormatter<>(change -> {
            // Allow programmatic changes (like appendText) to pass freely
            if (!change.isContentChange()) {
                return change;
            }

            // Reject any user attempt to modify text before the input start index
            if (change.getRangeStart() < inputStart) {
                return null;
            }
            return change;
        }));

        // Handle Enter key for submission, block backspace at the prompt boundary
        addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                e.consume();
                String input = getText(inputStart, getLength());
                appendText("\n"); // Programmatic append; updates inputStart via the stream

                // Offload the blocking write operation
                ioExecutor.submit(() -> {
                    try {
                        userOut.write((input + "\n").getBytes(StandardCharsets.UTF_8));
                        userOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } else if ((e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT)
                    && getCaretPosition() <= inputStart) {
                e.consume();
            }
        });
    }

    public class ConsoleOutputStream extends OutputStream {
        private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        @Override
        public void write(int b) {
            buffer.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) {
            buffer.write(b, off, len);
        }

        @Override
        public void flush() {
            if (buffer.size() == 0) return;

            final String text = buffer.toString(StandardCharsets.UTF_8);
            buffer.reset();

            Platform.runLater(() -> {
                // Ensure we don't exceed a safe capacity (e.g., 50,000 characters)
                if (getLength() > 50000) {
                    deleteText(0, 10000);
                }

                // Temporarily disable the formatter's bounds check by adjusting inputStart
                int previousStart = inputStart;
                inputStart = 0;
                appendText(text);
                inputStart = getLength();
                positionCaret(getLength());
            });
        }
    }
}