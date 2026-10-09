import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;

public class DeveloperTools
{
    public static void showJavaCode (File generatedJavaFile)
    {
        javafx.application.Platform.runLater(() -> {
            try {
                // Read the newly generated Java file from the disk
                String generatedCode = Files.readString(generatedJavaFile.toPath());

                // Dump it into a simple text area
                TextArea codeView = new TextArea(generatedCode);
                codeView.setEditable(false);
                codeView.setStyle("-fx-font-family: 'Consolas'; -fx-background-color: #2b2b2b; -fx-text-fill: #a9b7c6;");

                // Pop open a new window to display it
                Stage stage = new Stage();
                stage.setTitle("Developer Diagnostics: " + generatedJavaFile.getName());
                stage.setScene(new Scene(codeView, 600, 700));
                stage.show();

            } catch (Exception e) {
                System.err.println("ΣΦΑΛΜΑ DEV MENU: Αδυναμία ανάγνωσης του αρχείου Java.");
            }
        });
    }

    public static void showErrorWindow(String errorMessage) {
        Platform.runLater(() -> {
            TextArea errorView = new TextArea(errorMessage);
            errorView.setEditable(false);
            // Using a reddish hue (#ff6b68) to immediately distinguish it as a crash log
            errorView.setStyle("-fx-font-family: 'Consolas'; -fx-background-color: #2b2b2b; -fx-text-fill: #ff6b68; -fx-font-size: 14px;");

            Stage stage = new Stage();
            stage.setTitle("Developer Diagnostics: Transpiler Crash Log");
            stage.setScene(new Scene(errorView, 800, 600));
            stage.show();
        });
    }
}
