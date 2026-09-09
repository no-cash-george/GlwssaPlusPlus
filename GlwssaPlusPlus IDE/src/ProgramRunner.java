import java.io.File;

public class ProgramRunner
{
    public static void compileAndRun(File file)
    {
        System.out.println("Run");
        try {
            // Καλούμε την compile() που έφτιαξες στο προηγούμενο βήμα
            GlwssaPlusPlusCompiler.compile(file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("ΣΦΑΛΜΑ TRANSPILER: " + e.getMessage());
            throw  new RuntimeException(e);
        }

        String programName = GlwssaCLI.extractProgramName(file);
        if (programName == null) {
            System.err.println("ΣΦΑΛΜΑ: Δεν βρέθηκε η δήλωση 'ΠΡΟΓΡΑΜΜΑ [όνομα]' στο αρχείο.");
        }

        // 2. Compilation (Java -> Bytecode)
        System.out.println("[Βήμα 2/3] Μεταγλώττιση Java (javac)...");
        int javacStatus = GlwssaCLI.executeTerminalCommand("javac", programName + ".java");
        if (javacStatus != 0) {
            System.err.println("ΣΦΑΛΜΑ: Αποτυχία μεταγλώττισης της Java.");
        }

        // 3. Execution (Running the Bytecode)
        System.out.println("[Βήμα 3/3] Εκτέλεση Προγράμματος:\n");
        System.out.println("------------------ ΕΞΟΔΟΣ ΠΡΟΓΡΑΜΜΑΤΟΣ ------------------");

        GlwssaCLI.executeTerminalCommand("java", programName);

        System.out.println("---------------------------------------------------------");
    }

    public static void compileAndRunStepByStep(File file)//todo
    {
        System.out.println("Run step by step");
    }
}
