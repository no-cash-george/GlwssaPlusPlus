import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils
{
    public enum TypeEnum{AKERAIOS,
        PRAGMATIKOS,
        LOGIKOS,
        XARAKTHRAS};

    public static String toGreeklish(String greekText)
    {
        String[][] map = {
                {"α", "a"}, {"β", "v"}, {"γ", "g"}, {"δ", "d"}, {"ε", "e"},
                {"ζ", "z"}, {"η", "i"}, {"θ", "th"}, {"ι", "i"}, {"κ", "k"},
                {"λ", "l"}, {"μ", "m"}, {"ν", "n"}, {"ξ", "x"}, {"ο", "o"},
                {"π", "p"}, {"ρ", "r"}, {"σ", "s"}, {"ς", "s"}, {"τ", "t"},
                {"υ", "y"}, {"φ", "f"}, {"χ", "x"}, {"ψ", "ps"}, {"ω", "o"},
                {"Α", "A"}, {"Β", "V"}, {"Γ", "G"}, {"Δ", "D"}, {"Ε", "E"},
                {"Ζ", "Z"}, {"Η", "I"}, {"Θ", "TH"}, {"Ι", "I"}, {"Κ", "K"},
                {"Λ", "L"}, {"Μ", "M"}, {"Ν", "N"}, {"Ξ", "X"}, {"Ο", "O"},
                {"Π", "P"}, {"Ρ", "R"}, {"Σ", "S"}, {"Τ", "T"}, {"Υ", "Y"},
                {"Φ", "F"}, {"Χ", "X"}, {"Ψ", "PS"}, {"Ω", "O"}
        };

        for (String[] pair : map) {
            greekText = greekText.replace(pair[0], pair[1]);
        }

        return greekText;
    }

    public static void writeArrayListToFile(ArrayList<String> lines, String filename)
    {
        File outputFile = new File(filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}