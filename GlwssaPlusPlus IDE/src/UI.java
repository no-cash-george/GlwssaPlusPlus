import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.*;
import java.util.Set;

public class UI extends Application {

    private CodeArea codeArea;
    private InteractiveConsole consoleArea;
    private File currentFile;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        Set<String> KEYWORDS = Set.of(
                "ΠΡΟΓΡΑΜΜΑ", "ΜΕΤΑΒΛΗΤΕΣ", "ΑΚΕΡΑΙΕΣ", "ΠΡΑΓΜΑΤΙΚΕΣ",
                "ΧΑΡΑΚΤΗΡΕΣ", "ΛΟΓΙΚΕΣ", "ΑΡΧΗ", "ΓΡΑΨΕ", "ΔΙΑΒΑΣΕ",
                "ΑΝ", "ΤΟΤΕ", "ΑΛΛΙΩΣ", "ΑΛΛΙΩΣ_ΑΝ", "ΤΕΛΟΣ_ΑΝ",
                "ΕΠΙΛΕΞΕ", "ΠΕΡΙΠΤΩΣΗ", "ΤΕΛΟΣ_ΕΠΙΛΟΓΩΝ",
                "ΟΣΟ", "ΕΠΑΝΑΛΑΒΕ", "ΤΕΛΟΣ_ΕΠΑΝΑΛΗΨΗΣ",
                "ΑΡΧΗ_ΕΠΑΝΑΛΗΨΗΣ", "ΜΕΧΡΙΣ_ΟΤΟΥ",
                "ΓΙΑ", "ΑΠΟ", "ΜΕΧΡΙ", "ΜΕ_ΒΗΜΑ",
                "ΤΕΛΟΣ_ΠΡΟΓΡΑΜΜΑΤΟΣ"
        );

        this.primaryStage = stage;
        BorderPane root = new BorderPane();

        // 1. Setup Editor
        codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.addEventHandler(KeyEvent.KEY_TYPED, event -> {
            String typed = event.getCharacter();

            // Trigger when user finishes a word
            if (typed.equals(" ") || typed.equals("\n") || typed.equals("\r")) {
                Platform.runLater(() -> {
                    int currentCaret = codeArea.getCaretPosition();
                    if (currentCaret < 2) return;

                    int endOfWord = currentCaret - 1;
                    int startOfWord = endOfWord;

                    String fullText = codeArea.getText();

                    // Scan backwards to find the start of the word
                    while (startOfWord > 0) {
                        char c = fullText.charAt(startOfWord - 1);
                        if (Character.isLetter(c) || c == '_') {
                            startOfWord--;
                        } else {
                            break;
                        }
                    }

                    if (startOfWord < endOfWord) {
                        String word = fullText.substring(startOfWord, endOfWord);

                        for (String kw : KEYWORDS) {
                            if (kw.equalsIgnoreCase(word) && !kw.equals(word)) {
                                codeArea.replaceText(startOfWord, endOfWord, kw);
                                codeArea.moveTo(currentCaret);
                                break;
                            }
                        }
                    }
                });
            }
        });

        // Listen to text changes and apply ANTLR highlighting
        codeArea.textProperty().addListener((obs, oldText, newText) -> {
            codeArea.setStyleSpans(0, GlwssaHighlighter.computeHighlighting(newText));
        });

        // 2. Setup Resizable Console Pane
        try {
            consoleArea = new InteractiveConsole();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize console streams.", e);
        }

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(codeArea, consoleArea);
        splitPane.setDividerPositions(0.75); // 75% for code, 25% for terminal

        root.setCenter(splitPane);

        // 3. Setup Menus
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu runMenu = new Menu("Run");

        MenuItem openItem = new MenuItem("Open");
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.META_DOWN));
        openItem.setOnAction(e -> {
            currentFile = Backend.openFile(currentFile, primaryStage, codeArea);
        });

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.META_DOWN));
        saveItem.setOnAction(e -> {
            currentFile = Backend.saveFile(currentFile, primaryStage, codeArea);
        });

        MenuItem runItem = new MenuItem("Save & Run");
        runItem.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCodeCombination.SHORTCUT_DOWN));
        runItem.setOnAction(e -> {
            currentFile = Backend.saveFile(currentFile, primaryStage, codeArea);
            System.out.println("Auto Save");
            runItem.setDisable(true);
            //consoleArea.clearConsole();

            // Execute on a background thread to prevent UI freezing
            new Thread(() -> {
                try {
                    ProgramRunner.compileAndRun(currentFile);
                }finally {
                    runItem.setDisable(false);
                }
            }).start();
        });

        MenuItem runStepByStep = new MenuItem("Save & RunStepByStep");
        runStepByStep.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCodeCombination.ALT_DOWN, KeyCombination.SHORTCUT_DOWN));
        runStepByStep.setOnAction(e -> {
            if (currentFile == null) return;
            //consoleArea.clearConsole();
            new Thread(() -> {
                ProgramRunner.compileAndRunStepByStep(currentFile);
            }).start();
        });

        fileMenu.getItems().addAll(openItem, saveItem);
        runMenu.getItems().addAll(runItem, runStepByStep);

        menuBar.getMenus().addAll(fileMenu, runMenu);
        root.setTop(menuBar);

        // 4. Display
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("ide.css").toExternalForm());

        stage.setTitle("ΓΛΩΣΣΑ++ IDE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Inner class representing the interactive terminal.
     * It redirects System.out/err to the TextArea and pipes user input to System.in.
     */
    /*
    private static class InteractiveConsole extends TextArea {
        private int inputStart = 0;
        private final PipedOutputStream userOut;

        public InteractiveConsole() throws IOException {
            this.userOut = new PipedOutputStream();
            PipedInputStream systemIn = new PipedInputStream(userOut);

            // Redirect System.in to listen to our TextArea
            System.setIn(systemIn);

            setStyle("-fx-font-family: 'Consolas', monospace; -fx-background-color: #2b2b2b; -fx-control-inner-background: #2b2b2b; -fx-text-fill: #a9b7c6; -fx-font-size: 14px;");
            setWrapText(true);

            // Redirect System.out and System.err to this TextArea
            PrintStream outStream = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    Platform.runLater(() -> {
                        appendText(String.valueOf((char) b));
                        inputStart = getLength();
                    });
                }
                @Override
                public void write(byte[] b, int off, int len) {
                    Platform.runLater(() -> {
                        appendText(new String(b, off, len));
                        inputStart = getLength();
                    });
                }
            }, true);
            System.setOut(outStream);
            System.setErr(outStream);

            // Intercept keystrokes for the interactive prompt
            addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                // Prevent user from editing previous terminal output
                if (getCaretPosition() < inputStart) {
                    positionCaret(getLength());
                }

                if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.LEFT) {
                    if (getCaretPosition() <= inputStart) {
                        e.consume(); // Block deleting the prompt
                    }
                } else if (e.getCode() == KeyCode.ENTER) {
                    e.consume();
                    String input = getText(inputStart, getLength());
                    appendText("\n");
                    inputStart = getLength();
                    try {
                        // Send the typed string into the PipedOutputStream
                        userOut.write((input + "\n").getBytes());
                        userOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        public void clearConsole() {
            Platform.runLater(() -> {
                clear();
                inputStart = 0;
            });
        }
    }

     */
}