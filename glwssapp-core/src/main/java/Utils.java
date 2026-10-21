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

    public static String getDefaultValue(String javaType) {
        return switch (javaType) {
            case "int" -> "0";
            case "double" -> "0.0f";
            case "boolean" -> "false";
            case "String" -> "\"\"";
            default -> "null";
        };
    }

    public static String toGreeklish(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        // 1. Ελέγχουμε αν το αρχικό κείμενο περιέχει ελληνικούς χαρακτήρες
        boolean containsGreek = text.matches(".*[α-ωΑ-Ω].*");

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

        String result = text;
        for (String[] pair : map) {
            result = result.replace(pair[0], pair[1]);
        }

        // 2. Αν ήταν εξ αρχής ελληνικό, προσθέτουμε πρόθεμα για αποφυγή σύγκρουσης
        if (containsGreek) {
            return "greek_" + result;
        }

        return result;
    }

    public static File writeArrayListToFile(ArrayList<String> lines, String filename) {
        File outputFile = new File(filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }
}