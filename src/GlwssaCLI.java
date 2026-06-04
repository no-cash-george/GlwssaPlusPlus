import java.io.File;
import java.util.Scanner;

public class GlwssaCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("+================================================+");
        System.out.println("|       ΓΛΩΣΣΑ-PLUS-PLUS | Compiler & CLI        |");
        System.out.println("+================================================+");

        while (true) {
            System.out.println("\nΕπίλεξε ενέργεια:");
            System.out.println("[1] Μεταγλώττιση σε Java (Transpile only)");
            System.out.println("[2] Μεταγλώττιση & Εκτέλεση (Compile & Run)");
            System.out.println("[3] Έξοδος");
            System.out.print("-> ");

            String choice = scanner.nextLine().strip();

            if (choice.equals("3")) {
                System.out.println("Τερματισμός. Καλό γράψιμο κώδικα!");
                break;
            }

            if (choice.equals("1") || choice.equals("2")) {
                System.out.print("Δώσε το path του αρχείου ΓΛΩΣΣΑ (.gl): ");
                String filePath = scanner.nextLine().strip();

                File file = new File(filePath);
                if (!file.exists()) {
                    System.err.println("ΣΦΑΛΜΑ: Το αρχείο '" + filePath + "' δεν βρέθηκε.");
                    continue;
                }

                // 1. Transpilation (ΓΛΩΣΣΑ -> Java)
                System.out.println("\n[Βήμα 1/3] Μετάφραση σε Java...");
                try {
                    // Καλούμε την compile() που έφτιαξες στο προηγούμενο βήμα
                    GlwssaPlusPlusCompiler.compile(filePath);
                } catch (Exception e) {
                    System.err.println("ΣΦΑΛΜΑ TRANSPILER: " + e.getMessage());
                    continue;
                }

                if (choice.equals("1")) continue;

                // Για να τρέξουμε το αρχείο, πρέπει να βρούμε πώς το ονόμασε ο Transpiler
                String programName = extractProgramName(file);
                if (programName == null) {
                    System.err.println("ΣΦΑΛΜΑ: Δεν βρέθηκε η δήλωση 'ΠΡΟΓΡΑΜΜΑ [όνομα]' στο αρχείο.");
                    continue;
                }

                // 2. Compilation (Java -> Bytecode)
                System.out.println("[Βήμα 2/3] Μεταγλώττιση Java (javac)...");
                int javacStatus = executeTerminalCommand("javac", programName + ".java");
                if (javacStatus != 0) {
                    System.err.println("ΣΦΑΛΜΑ: Αποτυχία μεταγλώττισης της Java.");
                    continue;
                }

                // 3. Execution (Running the Bytecode)
                System.out.println("[Βήμα 3/3] Εκτέλεση Προγράμματος:\n");
                System.out.println("------------------ ΕΞΟΔΟΣ ΠΡΟΓΡΑΜΜΑΤΟΣ ------------------");

                executeTerminalCommand("java", programName);

                System.out.println("---------------------------------------------------------");
            } else {
                System.out.println("Λάθος επιλογή. Δοκίμασε ξανά.");
            }
        }
        scanner.close();
    }

    /**
     * Τρέχει μια εντολή στο τερματικό του λειτουργικού συστήματος.
     * Το .inheritIO() εξασφαλίζει ότι το `Scanner(System.in)` της παραγόμενης Java 
     * θα διαβάζει κανονικά από το τερματικό που βρισκόμαστε τώρα.
     */
    private static int executeTerminalCommand(String... command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.inheritIO();
            Process process = pb.start();
            return process.waitFor();
        } catch (Exception e) {
            System.err.println("Αποτυχία εκτέλεσης εντολής συστήματος: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Διαβάζει γρήγορα το αρχείο ΓΛΩΣΣΑΣ για να βρει το όνομα του προγράμματος
     * και το περνάει από το Utils.toGreeklish για να ξέρουμε ποιο .java αρχείο να τρέξουμε.
     */
    private static String extractProgramName(File file) {
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.startsWith("ΠΡΟΓΡΑΜΜΑ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length > 1) {
                        return Utils.toGreeklish(parts[1]);
                    }
                }
            }
        } catch (Exception e) {
            // Αγνοούμε τα λάθη ανάγνωσης εδώ, θα έχουν ήδη πιαστεί από τον Transpiler
        }
        return null;
    }
}