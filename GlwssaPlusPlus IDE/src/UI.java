import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import javafx.application.Platform;

import java.io.File;
import java.util.Set;

public class UI extends Application {

    private CodeArea codeArea;
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
                                // Replace the text
                                codeArea.replaceText(startOfWord, endOfWord, kw);

                                // FIX: Restore the cursor to its correct position after the space/newline
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

        root.setCenter(codeArea);

        // 2. Setup Menus
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        Menu runMenu = new Menu("Run");

        MenuItem openItem = new MenuItem("Open...");
        openItem.setOnAction(e ->
        {
            currentFile = Backend.openFile(currentFile,
                    primaryStage,
                    codeArea);
        });

        MenuItem saveItem = new MenuItem("Save");
        saveItem.setOnAction(e ->
        {
            currentFile = Backend.saveFile(currentFile,
                    primaryStage,
                    codeArea);
        });

        MenuItem runItem = new MenuItem("Run");
        runItem.setOnAction(e -> ProgramRunner.compileAndRun(currentFile));

        MenuItem runStepByStep = new MenuItem("RunStepByStep");
        runStepByStep.setOnAction(e -> ProgramRunner.compileAndRunStepByStep(currentFile));

        fileMenu.getItems().addAll(openItem, saveItem);
        runMenu.getItems().addAll(runItem,  runStepByStep);

        menuBar.getMenus().addAll(fileMenu, runMenu);
        root.setTop(menuBar);

        // 3. Display
        Scene scene = new Scene(root, 800, 600);
        // Load the CSS for the syntax highlighting
        scene.getStylesheets().add(getClass().getResource("ide.css").toExternalForm());

        stage.setTitle("ΓΛΩΣΣΑ++ IDE");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}