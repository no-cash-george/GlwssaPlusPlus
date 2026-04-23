import java.io.*;
import java.util.*;

public class GlossaCompiler
{
    public static void main(String[] args)
    {
        System.out.println("Give me the file's absolute path:");
        Scanner input = new Scanner(System.in);
        File srcFile = new File(input.nextLine().strip());  // Read file name/path from user input

        String compiledProgramName = Utils.toGreeklish(Utils.getProgramName(srcFile));

        if (compiledProgramName.equals("FAILURE"))
        {
            System.err.println("ERROR: Failure while creating the file");
        }else
        {
            compileFile(srcFile, compiledProgramName);
        }

        // Close the scanner
        input.close();
    }

    public static void compileFile(File src, String programName)
    {
        ArrayList<String> programArrayList = Utils.readFileToArrayList(src);
        ArrayList<String> programOut = new ArrayList<>();

        //gets variable names and divides the by type
        ArrayList<Variable> intVariableList = Utils.getVarsOfType(src, "ΑΚΕΡΑΙΕΣ:");
        ArrayList<Variable> floatVariableList = Utils.getVarsOfType(src, "ΠΡΑΓΜΑΤΙΚΕΣ:");
        ArrayList<Variable> boolVariableList = Utils.getVarsOfType(src, "ΛΟΓΙΚΕΣ:");
        ArrayList<Variable> StringVariableList = Utils.getVarsOfType(src, "ΧΑΡΑΚΤΗΡΕΣ:");
        ArrayList<Variable> VariablesList = new ArrayList<>();

        programOut.add("import java.math.*;");
        programOut.add("public class " + programName + " {");
        programOut.add("public static void main(String[] args) {");

        //adding local variables
        for (Variable current : intVariableList)
        {
            VariablesList.add(current);
        }

        for (Variable current : floatVariableList)
        {
            VariablesList.add(current);
        }

        for (Variable current : boolVariableList)
        {
            VariablesList.add(current);
        }

        for (Variable current : StringVariableList)
        {
            VariablesList.add(current);
        }

        for (Variable current : VariablesList)
        {
            String variableType;

            if(current.typeOfVariable == Utils.TypeEnum.AKERAIOS)
            {
                variableType = "int ";
            }else if(current.typeOfVariable == Utils.TypeEnum.PRAGMATIKOS)
            {
                variableType = "float ";
            }else if(current.typeOfVariable == Utils.TypeEnum.LOGIKOS)
            {
                variableType = "boolean ";
            }else
            {
                variableType = "String ";
            }

            programOut.add(variableType + current.nameOfVariable + ";");
        }

        ArrayList<String> mainBody =  Utils.getMainBodyOfProgram(src, VariablesList);

        if(!mainBody.get(0).equals("FAILURE"))
        {
            for (String current : mainBody) {
                programOut.add(current);
            }
        }
        else
        {
            System.err.println("ERROR: Compilation Failed");
            return;
        }

        programOut.add("}");
        programOut.add("}");

        Utils.writeArrayListToFile(programOut, programName+".java");
        System.out.println("Compilation Successful");
    }
}

// /Users/georgepapachristodoulou/Documents/Programming/Java/IntelliJ Project/Glwssa Compiler/out/production/Glwssa Compiler/antwnis.gl
