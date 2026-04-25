import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.*;
import java.io.File;
import java.util.Scanner;

public class GlwssaPlusPlusCompiler
{
    public static void main(String[] args) throws Exception {
        System.out.println("Give me the file's absolute path:");
        Scanner input = new Scanner(System.in);
        String filePath = input.nextLine().strip();
        input.close();

        File srcFile = new File(filePath);
        if (!srcFile.exists()) {
            System.err.println("ERROR: File not found.");
            return;
        }

        // 1. Lexer: Read characters and output Tokens
        CharStream charStream = CharStreams.fromFileName(filePath);
        GlwssaLexer lexer = new GlwssaLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 2. Parser: Read Tokens and build the Abstract Syntax Tree (AST)
        GlwssaParser parser = new GlwssaParser(tokens);
        ParseTree tree = parser.program(); // Start parsing at the 'program' rule

        // 3. Transpile: Walk the tree and generate Java code
        GlwssaPlusPlusTranspiler transpiler = new GlwssaPlusPlusTranspiler();
        String javaOutput = transpiler.visit(tree);

        // 4. Output the result
        // CORRECTED: Cast the tree we ALREADY parsed to get the ID, do not call parser.program() again.
        GlwssaParser.ProgramContext programTree = (GlwssaParser.ProgramContext) tree;
        String programName = Utils.toGreeklish(programTree.ID().getText());

        Utils.writeArrayListToFile(
                new java.util.ArrayList<>(java.util.List.of(javaOutput.split("\n"))),
                programName + ".java"
        );

        System.out.println("Compilation Successful. Generated " + programName + ".java");
    }
}
