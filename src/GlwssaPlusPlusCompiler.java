import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.*;
import java.io.File;
import java.util.Scanner;

public class GlwssaPlusPlusCompiler
{
    public static void main(String[] args)
    {
        System.out.println("Give me the file's absolute path:");
        Scanner input = new Scanner(System.in);
        String filePath = input.nextLine().strip();
        input.close();

        try
        {
            compile(filePath);
        }catch (Exception e)
        {
            System.err.println("CRITICAL COMPILER ERROR: " + e.getMessage());
        }
    }

    public static void compile(String filePath) throws java.io.IOException {
        File srcFile = new File(filePath);
        if (!srcFile.exists()) {
            System.err.println("ERROR: File not found.");
            return; // Stops here, no exception thrown
        }

        // 1. Read characters and output Tokens
        org.antlr.v4.runtime.CharStream charStream = org.antlr.v4.runtime.CharStreams.fromFileName(filePath);
        GlwssaLexer lexer = new GlwssaLexer(charStream);
        org.antlr.v4.runtime.CommonTokenStream tokens = new org.antlr.v4.runtime.CommonTokenStream(lexer);

        // 2. Read Tokens and build the Abstract Syntax Tree (AST)
        GlwssaParser parser = new GlwssaParser(tokens);
        org.antlr.v4.runtime.tree.ParseTree tree = parser.program(); // Start parsing at the 'program' rule

        // If the Lexer or Parser found bad syntax, stop BEFORE transpiling
        if (parser.getNumberOfSyntaxErrors() > 0) {
            System.err.println("ERROR: Syntax errors found in Glossa code. Compilation aborted.");
            return;
        }
        // ----------------------

        // 3. Walk the tree and generate Java code
        GlwssaPlusPlusTranspiler transpiler = new GlwssaPlusPlusTranspiler();
        String javaOutput = transpiler.visit(tree);

        // 4. Output the result
        GlwssaParser.ProgramContext programTree = (GlwssaParser.ProgramContext) tree;
        String programName = Utils.toGreeklish(programTree.ID().getText());

        Utils.writeArrayListToFile(
                new java.util.ArrayList<>(java.util.List.of(javaOutput.split("\n"))),
                programName + ".java"
        );
        System.out.println("Compilation Successful. Generated " + programName + ".java");
    }
}
