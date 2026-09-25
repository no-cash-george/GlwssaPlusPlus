import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ProgramRunner {

    public static void compileAndRun(File file) {
        System.out.println("Εκκίνηση Μεταγλώττισης...");
        try {
            // 1. Transpilation (ΓΛΩΣΣΑ -> Java)
            GlwssaPlusPlusCompiler.compile(file.getAbsolutePath());
        } catch (TranspilationException e) {
            System.err.println("Συντακτικό σφάλμα");
            System.err.println(e.getMessage());
            return;
        } catch (Exception e) {
            System.err.println("Εσωτερικό σφάλμα μεταγλώττισης");
        }

        String programName = GlwssaCLI.extractProgramName(file);
        if (programName == null) {
            System.err.println("ΣΦΑΛΜΑ: Δεν βρέθηκε η δήλωση 'ΠΡΟΓΡΑΜΜΑ [όνομα]' στο αρχείο.");
            return;
        }

        File parentDir = file.getAbsoluteFile().getParentFile();
        File buildDir = new File(parentDir, programName + "JavaBytecode");
        File javaFile = new File(buildDir, programName + ".java");

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            System.err.println("ΣΦΑΛΜΑ: Δεν βρέθηκε ενσωματωμένος Java Compiler.");
            System.err.println("Βεβαιωθείτε ότι η εφαρμογή εκτελείται μέσω ενός πλήρους JDK (όπως το GraalVM) και όχι ενός απλού JRE.");
            return;
        }

        // Execute compilation programmatically, routing outputs to our JavaFX console
        int javacStatus = compiler.run(null, System.out, System.err, javaFile.getAbsolutePath());

        if (javacStatus != 0) {
            System.err.println("ΣΦΑΛΜΑ: Αποτυχία μεταγλώττισης της Java (Κωδικός σφάλματος: " + javacStatus + ").");
            return;
        }

        // Verify the bytecode was successfully generated
        File classFile = new File(buildDir, programName + ".class");
        if (!classFile.exists()) {
            System.err.println("ΣΦΑΛΜΑ: Το αρχείο .class δεν βρέθηκε στο " + classFile.getAbsolutePath());
            return;
        }

        // 3. Execution (Running the Bytecode via Reflection)
        System.out.println("Εκτέλεση Προγράμματος:");
        runInCurrentJVM(buildDir, programName);

    }

    public static void compileAndRunStepByStep(File file) {
        System.out.println("Run step by step (Not yet implemented)");
    }

    /**
     * Loads the compiled .class file dynamically and invokes its main method
     * directly within the current JVM to preserve I/O stream routing.
     */
    private static void runInCurrentJVM(File buildDir, String className) {
        try {
            // Target the directory where the .class file resides
            URL[] urls = new URL[]{ buildDir.toURI().toURL() };

            // Instantiate a custom ClassLoader to pull the bytecode into memory
            try (URLClassLoader classLoader = new URLClassLoader(urls, ProgramRunner.class.getClassLoader())) {
                Class<?> compiledClass = classLoader.loadClass(className);

                // Locate public static void main(String[] args)
                Method mainMethod = compiledClass.getMethod("main", String[].class);

                // Invoke main method. The array is cast to Object to prevent varargs unpacking exceptions.
                mainMethod.invoke(null, (Object) new String[0]);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("ΣΦΑΛΜΑ: Η κλάση " + className + " δεν βρέθηκε στο σύστημα αρχείων.");
        } catch (NoSuchMethodException e) {
            System.err.println("ΣΦΑΛΜΑ: Δεν βρέθηκε η μέθοδος main() στην κλάση " + className + ".");
        } catch (InvocationTargetException e) {
            System.err.println("ΣΦΑΛΜΑ ΕΚΤΕΛΕΣΗΣ: Το πρόγραμμα τερμάτισε απροσδόκητα.");
            // Unwrap the InvocationTargetException to display the actual runtime error in ΓΛΩΣΣΑ
            if (e.getCause() != null) {
                e.getCause().printStackTrace(System.err);
            } else {
                e.printStackTrace(System.err);
            }
        } catch (Exception e) {
            System.err.println("ΣΦΑΛΜΑ: Απροσδόκητο πρόβλημα κατά την εκτέλεση μέσω Reflection.");
            e.printStackTrace(System.err);
        }
    }
}