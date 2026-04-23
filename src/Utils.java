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

    public static String getProgramName(File current)
    {
        if(current == null)
        {
            return "FAILURE";
        }

        String nameOfFile = current.getName().split("\\.")[0].strip();
        String nameOfProgram = "FAILURE";
        boolean foundProgramStart = false;
        ArrayList<String> fileArray = readFileToArrayList(current);

        for(String line : fileArray)
        {
            String[] tokens = line.split(" ");

            if (tokens[0].equals("ΠΡΟΓΡΑΜΜΑ") && !foundProgramStart) {
                nameOfProgram = tokens[1].strip();
                foundProgramStart = true;
            }else if (tokens[0].equals("ΠΡΟΓΡΑΜΜΑ"))
            {
                System.err.println("ERROR: Main program function cannot be defined more than one time");
                return "FAILURE";
            }
        }

        if(!nameOfFile.equals(nameOfProgram))
        {
            System.err.println("ERROR: Name of program does not match name of file");
            return "FAILURE";
        }

        return nameOfProgram;
    }

    public static ArrayList<String> readFileToArrayList(File file)
    {
        ArrayList<String> fileContents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContents.add(line);  // Add each line to the ArrayList
            }
        } catch (IOException e) {
            System.err.println("ERROR: Opening File Failure");
        }

        return fileContents;
    }

    public static ArrayList<Variable> getVarsOfType(File src, String type)
    {
        ArrayList<Variable> variables = new ArrayList<>();
        ArrayList<String> fileArray = readFileToArrayList(src);

        for (int i = 0; i < fileArray.size(); i++) {
            String line = fileArray.get(i).strip();

            if (line.equals("ΜΕΤΑΒΛΗΤΕΣ")) {
                for (int j = i + 1; j < fileArray.size(); j++) {
                    String nextLine = fileArray.get(j).strip();

                    if (nextLine.equals("ΑΡΧΗ")) break;

                    if (nextLine.startsWith(type)) {
                        nextLine = nextLine.replaceFirst(type, "").trim();
                        String[] parts = nextLine.split("\\s*,\\s*");

                        for (String var : parts) {
                            TypeEnum type2enum;

                            //convert to enum
                            if(type.equals("ΑΚΕΡΑΙΕΣ:"))
                            {
                                type2enum = TypeEnum.AKERAIOS;
                            }else if(type.equals("ΠΡΑΓΜΑΤΙΚΕΣ:"))
                            {
                                type2enum = TypeEnum.PRAGMATIKOS;
                            }else if(type.equals("ΛΟΓΙΚΕΣ:"))
                            {
                                type2enum = TypeEnum.LOGIKOS;
                            }else
                            {
                                type2enum = TypeEnum.XARAKTHRAS;
                            }

                            if(Character.isDigit(var.toCharArray()[0]))
                            {
                                System.err.println("ERROR: Variables cannot start with a digit");
                            }

                            Variable tmp = new Variable(toGreeklish(var.trim()), type2enum);

                            variables.add(tmp);
                        }
                        break;
                    }
                }
                break;
            }
        }

        return variables;
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

    public static ArrayList<String> getMainBodyOfProgram(File src, ArrayList<Variable> Variables)
    {
        ArrayList<String> mainBody = new ArrayList<>();
        ArrayList<String> fileArray = readFileToArrayList(src);

        int i;
        for(i = 0; !fileArray.get(i).equals("ΑΡΧΗ"); i++);

        if(!fileArray.get(i).equals("ΑΡΧΗ"))
        {
            mainBody.add("FAILURE");
            return mainBody;
        }
        i++;

        for(int j = i; j < fileArray.size(); j++)
        {
            if(fileArray.get(j).strip().equals("ΤΕΛΟΣ_ΠΡΟΓΡΑΜΜΑΤΟΣ"))
            {
                if(j == i)
                {
                    mainBody.add("emptyMainBody");
                }
                break;
            }

            if(fileArray.get(j).contains("<-"))
            {
                String[] lineTokens = fileArray.get(j).split( "\\s+");
                ArrayList<String> tmpTokens = new ArrayList<>();
                boolean firstTokenIsVariable = false, thirdTokenIsVariable = false;

                for(String now : lineTokens)
                {
                    if(!now.isBlank())
                    {
                        tmpTokens.add(now);
                    }
                }

                lineTokens[0] = tmpTokens.get(0);
                lineTokens[1] = tmpTokens.get(1);
                lineTokens[2] = tmpTokens.get(2);

                for(Variable current : Variables)
                {
                    if(toGreeklish(lineTokens[0].trim()).contains(current.nameOfVariable))
                    {
                        firstTokenIsVariable = true;
                    }

                    if(toGreeklish(lineTokens[3].trim()).contains(current.nameOfVariable))
                    {
                        thirdTokenIsVariable = true;
                    }
                }

                if(!firstTokenIsVariable)
                {
                    System.err.println("ERROR[at line " + (j+1) + " ]: Assignment to not declared variable");
                    return FAILURE();
                }else
                {
                    lineTokens[0] = toGreeklish(lineTokens[0]);
                }

                if(thirdTokenIsVariable)
                {
                    lineTokens[2] = toGreeklish(lineTokens[2]);
                }else if(lineTokens[2].equals("ΑΛΗΘΗΣ") || lineTokens[2].equals("ΨΕΥΔΗΣ"))
                {
                    if(lineTokens[2].equals("ΑΛΗΘΗΣ"))
                    {
                        lineTokens[2] = "true";
                    }else
                    {
                        lineTokens[2] = "false";
                    }
                }

                lineTokens[1] = "=";

                mainBody.add(lineTokens[0] + lineTokens[1] + lineTokens[2] + ";");
            }else if(fileArray.get(j).contains("ΓΡΑΨΕ"))
            {
                String[] lineTokensTMP = fileArray.get(j).split("[, ]+");
                ArrayList<String> printValues = new ArrayList<>();

                List<String> lineTokens = new ArrayList<>();
                for (String part : lineTokensTMP) {
                    if (!part.isEmpty()) {
                        lineTokens.add(part);
                    }
                }

                for (int k = 1; k < lineTokens.size(); k++)
                {
                    if(lineTokens.get(k).equals("ΓΡΑΨΕ"))
                    {
                        System.err.println("ERROR[at line:" + (j+1) + " ]: Incorrect print statement");
                        return FAILURE();
                    }
                    else
                    {
                        boolean isVariable = false;
                        Variable currentVariable = new Variable(null,null);

                        for(Variable current : Variables)
                        {
                            if (current.nameOfVariable.equals(toGreeklish(lineTokens.get(k))))
                            {
                                isVariable = true;
                                currentVariable = current;
                                break;
                            }
                        }

                        String tmpPrintToken;

                        if(isVariable)
                        {
                            if(currentVariable.typeOfVariable == TypeEnum.LOGIKOS)
                            {
                                tmpPrintToken = "(" + currentVariable.nameOfVariable + " ? \"ΑΛΗΘΗΣ\" : \"ΨΕΥΔΗΣ\")";
                            }else
                            {
                                tmpPrintToken = currentVariable.nameOfVariable;
                            }
                        }else if(lineTokens.get(k).equals("ΑΛΗΘΗΣ"))
                        {
                            tmpPrintToken = "\"ΑΛΗΘΗΣ\"";
                        }else if(lineTokens.get(k).equals("ΨΕΥΔΗΣ"))
                        {
                            tmpPrintToken = "\"ΨΕΥΔΗΣ\"";
                        }else if(lineTokens.get(k).strip().startsWith("\"") && lineTokens.get(k).strip().endsWith("\"") || isNumber(lineTokens.get(k)))
                        {
                            tmpPrintToken = lineTokens.get(k);
                        }else if(glwssaOperatorsToJava(lineTokens.get(k).strip()) != null)
                        {
                            boolean isVariableBefore = false, isVariableAfter = false;
                            Variable beforeVariable = new Variable(null, null), afterVariable = new Variable(null, null);
                            StringBuilder printStatement = new StringBuilder();

                            for(Variable current : Variables)
                            {
                                if(k - 1 > 1)
                                {
                                    if (current.nameOfVariable.strip().equals(toGreeklish(lineTokens.get(k - 1).strip())))
                                    {
                                        isVariableBefore = true;
                                        beforeVariable = current;
                                    }
                                }

                                if(k + 1 <= lineTokens.size())
                                {
                                    if (current.nameOfVariable.strip().equals(toGreeklish(lineTokens.get(k + 1))))
                                    {
                                        isVariableAfter = true;
                                        afterVariable = current;
                                    }
                                }
                            }

                            System.out.println(beforeVariable.nameOfVariable + " " +afterVariable.nameOfVariable);

                            if(isVariableBefore)
                            {
                                printStatement.append(beforeVariable.nameOfVariable);
                            }else if (lineTokens.get(k - 1).strip().startsWith("\"") && lineTokens.get(k - 1).strip().endsWith("\"") || isNumber(lineTokens.get(k - 1)))
                            {
                                printStatement.append(lineTokens.get(k - 1));
                            }

                            printStatement.append(glwssaOperatorsToJava(lineTokens.get(k).strip()));

                            if(isVariableAfter)
                            {
                                printStatement.append(afterVariable.nameOfVariable);
                            }else if (lineTokens.get(k + 1).strip().startsWith("\"") && lineTokens.get(k + 1).strip().endsWith("\"") || isNumber(lineTokens.get(k + 1)))
                            {
                                printStatement.append(lineTokens.get(k + 1));
                            }

                            tmpPrintToken = printStatement.toString();
                        }
                        else
                        {
                            System.err.println("ERROR[at line " + (j+1) + " ]: Varible " + lineTokens.get(k) + " is not defined");
                            return FAILURE();
                        }

                        printValues.add(tmpPrintToken);
                    }
                }

                String printStatement = "System.out.println(\" \" ";
                for(String current : printValues)
                {
                    printStatement += (" +" + current + " + \" \"");
                }
                printStatement += ");";

                mainBody.add(printStatement);

            }else if(!fileArray.get(j).isEmpty())
            {
                System.err.println("ERROR[at line:" + (j+1) + " ]: Unknown statement");
                return FAILURE();
            }
        }

        return mainBody;
    }

    public static ArrayList<String> FAILURE()
    {
        ArrayList<String> error = new ArrayList<>();
        error.add("FAILURE");
        return error;
    }

    public static boolean isNumber(String str)
    {
        if (str == null || str.trim().isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String glwssaOperatorsToJava(String src)
    {
        return switch (src.strip()) {
            case "DIV" -> "/";
            case "MOD" -> "%";
            case "ΟΧΙ" -> "!";
            case "ΚΑΙ" -> "&&";
            case "H" -> "||";
            case "<>" -> "!=";
            case ">" -> ">";
            case "<" -> "<";
            case ">=" -> ">=";
            case "<=" -> "<=";
            case "=" -> "==";
            default -> null;
        };
    }
}
