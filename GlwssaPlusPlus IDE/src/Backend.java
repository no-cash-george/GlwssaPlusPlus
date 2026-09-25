import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class Backend
{
    public static File openFile(File currentFile,  Stage primaryStage, CodeArea codeArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ΓΛΩΣΣΑ Files", "*.gl"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try {
                String content = Files.readString(file.toPath());
                codeArea.replaceText(content);
                currentFile = file;
                primaryStage.setTitle("ΓΛΩΣΣΑ++ IDE - " + file.getName());

                return currentFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static File saveFile(File currentFile,  Stage primaryStage, CodeArea codeArea) {
        if (currentFile == null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ΓΛΩΣΣΑ Files", "*.gl"));
            currentFile = fileChooser.showSaveDialog(primaryStage);
        }

        if (currentFile != null) {
            try {
                Files.writeString(currentFile.toPath(), codeArea.getText());
                primaryStage.setTitle("ΓΛΩΣΣΑ++ IDE - " + currentFile.getName());
                return currentFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
