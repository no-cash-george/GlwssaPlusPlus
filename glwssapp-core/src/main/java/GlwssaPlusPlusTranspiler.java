import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public class GlwssaPlusPlusTranspiler extends GlwssaBaseVisitor<String>
{

    private Map<String, String> symbolTable = new HashMap<>();
    private Map<String, String> symbolTableSubroutines = new HashMap<>();
    private Map<String, String> subprogramNameTable = new HashMap<>();
    private boolean inSubprogram = false;

    @Override
    public String visitProgram(GlwssaParser.ProgramContext ctx)
    {
        String programName = Utils.toGreeklish(ctx.ID().getText()); // get program name and translate to english

        StringBuilder mainCode = new StringBuilder();// inside here we will put the translated java code

        mainCode.append("public static void main (String[] args) \n{\n");

        if (ctx.const_declarations() != null)
        {
            mainCode.append(visit(ctx.const_declarations()));
        }

        if (ctx.declarations() != null)
        {
            mainCode.append(visit(ctx.declarations()));
        }

        for ( GlwssaParser.StatementContext stmtCtx : ctx.statement() )
        {
            mainCode.append(visit(stmtCtx) + "\n");
        }

        mainCode.append("}\n");

        return mainCode.toString();
    }

    @Override
    public String visitFile ( GlwssaParser.FileContext ctx )
    {
        String programName = Utils.toGreeklish(ctx.program().ID().getText());

        StringBuilder fileCode = new StringBuilder();

        fileCode.append( "import java.util.Scanner;\n\n" +
                            "public class " + programName + "\n{\n\n" +
                            "static Scanner scanner = new Scanner(System.in);\n\n");

        for ( GlwssaParser.SubprogramContext subPrCtx : ctx.subprogram() )
        {
            fileCode.append(visit(subPrCtx));
        }

        fileCode.append(visit(ctx.program()));

        //the bellow function injected in my code fixes issues #7 and #11. More in commit description
        fileCode.append("""
            
            public static void __print(Object... args) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    Object current = args[i];
                    
                    // 1. Format Booleans
                    if (current instanceof Boolean) {
                        sb.append((Boolean) current ? "ΑΛΗΘΗΣ" : "ΨΕΥΔΗΣ");
                    } 
                    // 2. Format Reals (Strip .0 and use Greek comma)
                    else if (current instanceof Float || current instanceof Double) {
                        String numStr = current.toString();
                        if (numStr.endsWith(".0")) {
                            numStr = numStr.substring(0, numStr.length() - 2);
                        }
                        sb.append(numStr.replace('.', ','));
                    } 
                    // 3. Format Strings & Integers
                    else {
                        sb.append(current);
                    }
                    
                    // 4. Alki Spacing Logic: Space ONLY between two numbers
                    if (i < args.length - 1) {
                        Object next = args[i + 1];
                        if (current instanceof Number && next instanceof Number) {
                            sb.append(" ");
                        }
                    }
                }
                System.out.println(sb.toString());
            }
        """);

        fileCode.append("\n}\n" +
                "class RefInt { public int value; public RefInt(int v) { this.value = v; } }\n" +
                "class RefDouble { public double value; public RefDouble(double v) { this.value = v; } }\n" +
                "class RefBoolean { public boolean value; public RefBoolean(boolean v) { this.value = v; } }\n" +
                "class RefString { public String value; public RefString(String v) { this.value = v; } }");

        return fileCode.toString();
    }

    @Override
    public String visitDeclarations(GlwssaParser.DeclarationsContext ctx)
    {
        StringBuilder declarations = new StringBuilder();

        for (GlwssaParser.Var_declContext varCtx : ctx.var_decl()) // visit each declaration line
        {
            declarations.append(visit(varCtx));
        }

        return declarations.toString();
    }

    @Override
    public String visitVar_decl(GlwssaParser.Var_declContext ctx)
    {
        String glwssaType = ctx.TYPE_KW().getText();
        String javaBaseType = switch (glwssaType)
        {
            case "ΑΚΕΡΑΙΕΣ:" -> "int";
            case "ΠΡΑΓΜΑΤΙΚΕΣ:" -> "double";
            case "ΛΟΓΙΚΕΣ:" -> "boolean";
            case "ΧΑΡΑΚΤΗΡΕΣ:" -> "String";
            default -> "Object";
        };

        StringBuilder declarationsCode = new StringBuilder();
        String defaultVal = Utils.getDefaultValue(javaBaseType);

        // Τρέχουμε για κάθε var_def (είτε είναι απλό ID είτε πίνακας)
        for (GlwssaParser.Var_defContext varDefCtx : ctx.var_def())
        {
            String varName = Utils.toGreeklish(varDefCtx.ID().getText());

            boolean alreadyExists;
            if (inSubprogram) {
                alreadyExists = symbolTableSubroutines.containsKey(varName);
            } else {
                alreadyExists = symbolTable.containsKey(varName);
            }

            if (alreadyExists) {
                // Το μήνυμα παραμένει ουδέτερο, καθώς καλύπτει τόσο σταθερές όσο και διπλο-δηλωμένες μεταβλητές
                throw new TranspilationException("Το αναγνωριστικό '" + varDefCtx.ID().getText() + "' έχει ήδη δηλωθεί.");
            }

            // 1. Ελέγχουμε αν έχει διαστάσεις (Πίνακας)
            if (varDefCtx.NUMBER() != null && !varDefCtx.NUMBER().isEmpty())
            {
                StringBuilder brackets = new StringBuilder();
                StringBuilder sizes = new StringBuilder();

                for (org.antlr.v4.runtime.tree.TerminalNode numNode : varDefCtx.NUMBER()) {
                    brackets.append("[]");
                    sizes.append("[").append(numNode.getText()).append("]");
                }

                String arrayType = javaBaseType + brackets.toString();
                if (inSubprogram) {
                    symbolTableSubroutines.put(varName, arrayType);
                } else {
                    symbolTable.put(varName, arrayType);
                }

                // Δημιουργεί: int[] A = new int[10];
                declarationsCode.append(arrayType).append(" ").append(varName)
                        .append(" = new ").append(javaBaseType).append(sizes.toString()).append(";\n");
            }
            else // 2. Απλή μεταβλητή
            {
                if (inSubprogram) {
                    symbolTableSubroutines.put(varName, javaBaseType);
                } else {
                    symbolTable.put(varName, javaBaseType);
                }

                // Δημιουργεί: int A = 0;
                declarationsCode.append(javaBaseType).append(" ").append(varName)
                        .append(" = ").append(defaultVal).append(";\n");
            }
        }

        return declarationsCode.toString();
    }

    @Override
    public String visitConst_declarations(GlwssaParser.Const_declarationsContext ctx)
    {
        StringBuilder declarations = new StringBuilder();

        for (GlwssaParser.Const_declContext constDeclCtx : ctx.const_decl())
        {
            declarations.append(visit(constDeclCtx));
        }

        return declarations.toString();
    }

    @Override
    public String visitConst_decl(GlwssaParser.Const_declContext ctx)
    {
        String varName = Utils.toGreeklish(ctx.ID().getText());
        String valueExpr = visit(ctx.expr()); //εξαγωγή τιμής

        // πολύ απλό type inference τιμής αρχικοποίησης
        String javaBaseType = "int"; // Προεπιλογή
        if (valueExpr.startsWith("\"")) {
            javaBaseType = "String";
        } else if (valueExpr.equals("true") || valueExpr.equals("false")) {
            javaBaseType = "boolean";
        } else if (valueExpr.contains(".")) {
            javaBaseType = "double";
        }

        // Καταγραφή στο σωστό εύρος
        if (inSubprogram) {
            symbolTableSubroutines.put(varName, javaBaseType);
        } else {
            symbolTable.put(varName, javaBaseType);
        }

        // Παραγωγή Java κώδικα με χρήση του 'final var' για απόλυτη συμβατότητα
        return "final var " + varName + " = " + valueExpr + ";\n";
    }

    @Override
    public String visitAssignment(GlwssaParser.AssignmentContext ctx)
    {
        String target;

        // Check assignment target
        if ( ctx.ID() != null )
        {
            String varName = Utils.toGreeklish(ctx.ID().getText());
            String type = resolveVariableType(varName);

            if ( type != null && type.startsWith("Ref") )
            {
                target = varName + ".value";
            }else
            {
                target = varName;
            }

            if (resolveVariableType(varName) == null)
            {
                throw new TranspilationException("Η μεταβλητή " + ctx.ID().getText() + " δεν υπάρχει");
            }
        }else // if ID is null then the target is an array
        {
            target = visit(ctx.array_access());
        }

        String expression = visit(ctx.expr());
        return target + " = " + expression + " ;";
    }

    @Override
    public String visitPrint_stmnt(GlwssaParser.Print_stmntContext ctx)
    {
        // Deprecated -> Collect all items to print, join them with a "+" for Java's System.out.println
        // Collect all items to print, join them with a "," for my custom print method.
        String printContent = ctx.print_item().stream()
                .map(this::visit)
                .collect(Collectors.joining(", "));

        return "__print(" + printContent + ");";
    }

    @Override
    public String visitPrint_item(GlwssaParser.Print_itemContext ctx)
    {
        if (ctx.STRING() != null)
        {
            return ctx.STRING().getText(); // already in double quotes
        }else
        {
            return visit(ctx.expr()); // variable or math expression
        }
    }

    // Set expression rules

    @Override
    public String visitMathExpr(GlwssaParser.MathExprContext ctx)
    {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String operator = ctx.op.getText();

        if (operator.equals("DIV"))
        {
            operator = "/";
        }
        if (operator.equals("MOD"))
        {
            operator = "%";
        }
        if (operator.equals("^"))
        {
            return "Math.pow(" + left + ", " + right + ")";
        }

        return "( " + left + " " + operator + " " + right + " )";
    }

    @Override
    public String visitRelationalExpr(GlwssaParser.RelationalExprContext ctx)
    {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String operator = ctx.op.getText();

        boolean isStringComparison = isStringType(ctx.expr(0)) || isStringType(ctx.expr(1));

        if (isStringComparison) {
            if (operator.equals("="))
            {
                return "(" + left + ".equals(" + right + "))";
            }
            else if (operator.equals("<>"))
            {
                return "(!" + left + ".equals(" + right + "))";
            }
            else
            {
                throw new TranspilationException("Δεν επιτρέπεται η χρήση του τελεστή '" + operator + "' σε μεταβλητές τύπου ΧΑΡΑΚΤΗΡΕΣ. Η σύγκριση επιτρέπεται μόνο με '=' και '<>'.");
            }
        }

        if (operator.equals("="))
        {
            operator = "==";
        }
        else if (operator.equals("<>"))
        {
            operator = "!=";
        }

        return "(" + left + " " + operator + " " + right + ")";
    }

    @Override
    public String visitNumberExpr(GlwssaParser.NumberExprContext ctx)
    {
        return ctx.NUMBER().getText();
    }

    @Override
    public String visitIdExpr(GlwssaParser.IdExprContext ctx)
    {
        String varName = Utils.toGreeklish(ctx.ID().getText());
        String type = resolveVariableType(varName);

        if ( type != null && type.startsWith("Ref") )
        {
            return varName + ".value";
        }

        return Utils.toGreeklish(ctx.ID().getText());
    }

    @Override
    public String visitBoolExpr(GlwssaParser.BoolExprContext ctx)
    {
        return ctx.TRUE_KW() != null ? "true" : "false";
    }

    @Override
    public String visitParenExpr(GlwssaParser.ParenExprContext ctx) {
        return "(" + visit(ctx.expr()) + ")";
    }

    @Override
    public String visitRead_stmnt(GlwssaParser.Read_stmntContext ctx) {
        StringBuilder readCode = new StringBuilder();

        // Start at index 1 to skip the 'ΔΙΑΒΑΣΕ' keyword token itself
        for (int i = 1; i < ctx.getChildCount(); i++) {
            org.antlr.v4.runtime.tree.ParseTree child = ctx.getChild(i);

            // Skip the commas entirely
            if (child.getText().equals(",")) {
                continue;
            }

            String target;     // The literal Java text to assign to (e.g., "a" or "A[(v) - 1]")
            String lookupName; // The base name for the symbol table (e.g., "a" or "A")

            if (child instanceof GlwssaParser.Array_accessContext) {
                // Intercept array access logic
                GlwssaParser.Array_accessContext arrCtx = (GlwssaParser.Array_accessContext) child;

                // Translate the array access (this will route to your visitArray_access method)
                target = visit(arrCtx);

                // For the symbol table, we only want the array's root name (e.g., "A")
                lookupName = Utils.toGreeklish(arrCtx.ID().getText());
            } else {
                // It is a standard ID token
                target = Utils.toGreeklish(child.getText());
                lookupName = target;
            }

            String javaType = resolveVariableType(lookupName);

            if (javaType == null) {
                throw new RuntimeException("SEMANTIC ERROR: Variable '" + lookupName + "' used in ΔΙΑΒΑΣΕ but was never declared in ΜΕΤΑΒΛΗΤΕΣ.");
            }

            if (javaType.startsWith("Ref")) {
                target = target + ".value";
                javaType = switch (javaType) {
                    case "RefInt" -> "int";
                    case "RefDouble" -> "double";
                    case "RefBoolean" -> "boolean";
                    case "RefString" -> "String";
                    default -> "String";
                };
            }

            String chosenScannerMethod = switch (javaType) {
                case "int" -> "nextInt()";
                case "double" -> "nextDouble()";
                case "boolean" -> "nextBoolean()";
                case "String" -> "next()";
                case "int[]" -> "nextInt()";
                case "double[]" -> "nextDouble()";
                case "boolean[]" -> "nextBoolean()";
                case "String[]" -> "next()";
                default -> "next()";
            };

            // Note: Using 'target' here, not 'varName'
            readCode.append(target).append(" = scanner.").append(chosenScannerMethod).append(";\n");
        }

        return readCode.toString();
    }

    @Override
    public String visitIf_stmnt(GlwssaParser.If_stmntContext ctx)
    {
        StringBuilder ifCode = new StringBuilder();

        String condition = visit(ctx.expr()); // main if condition

        ifCode.append("if ( " + condition + " ) \n { \n");

        for ( GlwssaParser.StatementContext stmntCtx : ctx.statement()) // get inside statements
        {
            ifCode.append(visit(stmntCtx) + "\n");
        }

        ifCode.append("}\n");

        for ( GlwssaParser.Else_if_blockContext elifCtx : ctx.else_if_block() ) // elif block evaluation
        {
            ifCode.append(visit(elifCtx));
        }

        if ( ctx.else_block() != null ) // else block exists?
        {
            ifCode.append(visit(ctx.else_block()));
        }

        return ifCode.toString();
    }

    @Override
    public String visitElse_if_block(GlwssaParser.Else_if_blockContext ctx)
    {
        StringBuilder elifCode = new StringBuilder();

        String condition = visit(ctx.expr()); // get elif expression

        elifCode.append("else if ( " + condition + " ) \n{ \n");

        for ( GlwssaParser.StatementContext stmntCtx : ctx.statement() ) // get inside statements
        {
            elifCode.append(visit(stmntCtx) + "\n");
        }

        elifCode.append("}\n");

        return elifCode.toString();
    }

    @Override
    public String visitElse_block ( GlwssaParser.Else_blockContext ctx )
    {
        StringBuilder elseCode = new StringBuilder();

        elseCode.append("else \n{ \n");

        for ( GlwssaParser.StatementContext stmntCtx : ctx.statement() )
        {
            elseCode.append(visit(stmntCtx) + "\n");
        }

        elseCode.append("}\n");

        return elseCode.toString();
    }

    @Override
    public String visitSelect_stmnt(GlwssaParser.Select_stmntContext ctx) {
        StringBuilder selectCode = new StringBuilder();

        String rawExpr = visit(ctx.expr()); // one time evaluation
        String tempVar = "target_" + ctx.getStart().getLine();
        selectCode.append("var ").append(tempVar).append(" = ").append(rawExpr).append(";\n");//let java decide what type the variable has with var

        boolean hasCases = ctx.case_block() != null && !ctx.case_block().isEmpty();

        // create if else chain
        if (hasCases) {
            for (int i = 0; i < ctx.case_block().size(); i++) {
                GlwssaParser.Case_blockContext caseCtx = ctx.case_block(i);
                StringBuilder conditionBuilder = new StringBuilder();

                for (int j = 0; j < caseCtx.case_item().size(); j++) {
                    GlwssaParser.Case_itemContext itemCtx = caseCtx.case_item(j);

                    if (j > 0) {
                        conditionBuilder.append(" || ");
                    }

                    if (itemCtx.op != null) {
                        // the case has an operator
                        String operator = itemCtx.op.getText();
                        String value = visit(itemCtx.expr(0));
                        conditionBuilder.append("(")
                                .append(tempVar).append(" ").append(operator).append(" ").append(value)
                                .append(")");
                    }
                    else if (itemCtx.DOTDOT() != null) {
                        // range
                        String lowerBound = visit(itemCtx.expr(0));
                        String upperBound = visit(itemCtx.expr(1));
                        conditionBuilder.append("(")
                                .append(tempVar).append(" >= ").append(lowerBound)
                                .append(" && ")
                                .append(tempVar).append(" <= ").append(upperBound)
                                .append(")");
                    }
                    else {
                        // value
                        String value = visit(itemCtx.expr(0));
                        conditionBuilder.append("(")
                                .append(tempVar).append(" == ").append(value)
                                .append(")");
                    }
                }

                // is if or else
                if (i == 0) {
                    selectCode.append("if (").append(conditionBuilder).append(") {\n");
                } else {
                    selectCode.append("else if (").append(conditionBuilder).append(") {\n");
                }

                for (GlwssaParser.StatementContext stmntCtx : caseCtx.statement()) {
                    selectCode.append(visit(stmntCtx)).append("\n");
                }
                selectCode.append("}\n");
            }
        }

        // 3. Χειρισμός του ΠΕΡΙΠΤΩΣΗ ΑΛΛΙΩΣ
        if (ctx.default_block() != null) {
            if (hasCases) {
                selectCode.append("else {\n");
            } else {
                // Σπάνια περίπτωση: Η ΕΠΙΛΕΞΕ έχει μόνο ΠΕΡΙΠΤΩΣΗ ΑΛΛΙΩΣ
                selectCode.append("{\n");
            }

            for (GlwssaParser.StatementContext stmntCtx : ctx.default_block().statement()) {
                selectCode.append(visit(stmntCtx)).append("\n");
            }
            selectCode.append("}\n");
        }

        return selectCode.toString();
    }

    @Override
    public String visitWhile_stmnt ( GlwssaParser.While_stmntContext ctx )
    {
        StringBuilder whileCode = new StringBuilder();

        String condition = visit(ctx.expr());

        whileCode.append("while ( " + condition + " ) \n{ \n");

        for ( GlwssaParser.StatementContext stmntCtx : ctx.statement() )
        {
            whileCode.append(visit(stmntCtx) + "\n");
        }

        whileCode.append("}\n");

        return whileCode.toString();
    }

    @Override
    public String visitDo_while_stmnt(GlwssaParser.Do_while_stmntContext ctx)
    {
        StringBuilder doWhileCode = new StringBuilder();

        doWhileCode.append("do {");

        for ( GlwssaParser.StatementContext stmntCtx : ctx.statement() )
        {
            doWhileCode.append(visit(stmntCtx) + "\n");
        }

        String condition = visit(ctx.expr());

        doWhileCode.append("}while ( !( " + condition + ") );\n"); //java do while logic opposite to GLWSSA logic

        return doWhileCode.toString();
    }

    @Override
    public String visitFor_loop_stmnt ( GlwssaParser.For_loop_stmntContext ctx )
    {
        StringBuilder forCode = new StringBuilder();

        String iter = Utils.toGreeklish(ctx.ID().getText());
        String iterType = resolveVariableType(iter);
        if (iterType != null && iterType.startsWith("Ref"))
        {
            iter += ".value";
        }

        String start = visit(ctx.expr(0));
        String end = visit(ctx.expr(1));
        String step = (ctx.expr().size() == 3) ? visit(ctx.expr(2)) : "1";

        try {
            double stepFloat = Double.parseDouble(step);

            forCode.append("for ( " + iter + " = " + start + "; ");

            if (stepFloat < 0) {
                forCode.append(iter + " >= " + end + "; " + iter + " += " + step + " ) \n {\n");
            } else {
                forCode.append(iter + " <= " + end + "; " + iter + " += " + step + " ) \n {\n");
            }

            for (GlwssaParser.StatementContext stmntCtx : ctx.statement()) {
                forCode.append(visit(stmntCtx) + "\n");
            }

            forCode.append("\n}");
        } catch (NumberFormatException e) // step not a number
        {
            StringBuilder bodyCode = new StringBuilder();
            for ( GlwssaParser.StatementContext stmntCtx : ctx.statement() ) {
                bodyCode.append("        ").append(visit(stmntCtx)).append("; \n");
            }

            forCode.append("if ( (").append(step).append(") >= 0 ) \n{ \n");
            forCode.append("for ( " + iter + " = " + start + "; " + iter + " <= " + end + "; " + iter + " += " + step + " ) \n{ \n");
            forCode.append(bodyCode);
            forCode.append("}\n");
            forCode.append("} else \n{ \n");
            forCode.append("for ( " + iter + " = " + start + "; " + iter + " >= " + end + "; " + iter + " += " + step + " ) \n{ \n");
            forCode.append(bodyCode);
            forCode.append("}\n");
            forCode.append("}\n");
        }

        return forCode.toString();
    }

    @Override
    public String visitArrayExpr(GlwssaParser.ArrayExprContext ctx)
    {
        return visit(ctx.array_access());
    }

    @Override
    public String visitArray_access(GlwssaParser.Array_accessContext ctx)
    {
        StringBuilder arrayAccessCode = new StringBuilder();

        String arrayName = Utils.toGreeklish(ctx.ID().getText());
        arrayAccessCode.append(arrayName);

        if (!symbolTable.containsKey(arrayName))
        {
            throw new TranspilationException("Δεν υπάρχει πίνακας με όνομα " + ctx.ID().getText());
        }

        for ( GlwssaParser.ExprContext exprContext : ctx.expr() )
        {
            String indexName = visit( exprContext );

            arrayAccessCode.append("[( " + indexName + " ) - 1 ]");
        }

        return arrayAccessCode.toString();
    }

    @Override
    public String visitFunctionCallExpr(GlwssaParser.FunctionCallExprContext ctx)
    {
        String rawFunctionName = ctx.ID().getText();

        // 1. Μετάφραση των ορισμάτων της συνάρτησης
        List<String> args = new ArrayList<>();
        if (ctx.expr() != null && !ctx.expr().isEmpty())
        {
            for (GlwssaParser.ExprContext exprCtx : ctx.expr())
            {
                args.add(visit(exprCtx));
            }
        }
        String joinedArgs = String.join(", ", args);

        // 2. Έλεγχος αν πρόκειται για ενσωματωμένη συνάρτηση της ΓΛΩΣΣΑΣ
        return switch (rawFunctionName) {
            case "Α_Μ"  -> "(int) Math.floor(" + joinedArgs + ")"; // Casting σε int για το ακέραιο μέρος
            case "Α_Τ" -> "Math.abs(" + joinedArgs + ")";
            case "Τ_Ρ" -> "Math.sqrt(" + joinedArgs + ")";
            case "Ε" -> "Math.exp(" + joinedArgs + ")";
            case "ΗΜ"  -> "Math.sin(" + joinedArgs + ")";
            case "ΣΥΝ" -> "Math.cos(" + joinedArgs + ")";
            case "ΕΦ"  -> "Math.tan(" + joinedArgs + ")";
            case "ΛΟΓ" -> "Math.log(" + joinedArgs + ")";
            default    -> {
                // Custom συνάρτηση χρήστη: μετατροπή του ονόματος σε greeklish
                String greeklishName = Utils.toGreeklish(rawFunctionName);
                if (!subprogramNameTable.containsKey(greeklishName))
                {
                    throw new TranspilationException("Δεν υπάρχει συνάρτηση με όνομα " + rawFunctionName);
                }

                yield greeklishName + "(" + joinedArgs + ")";
            }
        };
    }

    @Override
    public String visitFunction(GlwssaParser.FunctionContext ctx)
    {
        inSubprogram = true;
        symbolTableSubroutines.clear();

        StringBuilder functionCode = new StringBuilder();
        String functionName = Utils.toGreeklish(ctx.ID().getText());
        String glwssaReturnType = ctx.RETURN_TYPE_KW().getText();

        if (subprogramNameTable.containsKey(functionName))
        {
            throw new TranspilationException("Έχει ήδη δηλωθεί " + subprogramNameTable.get(functionName) + " με όνομα " + ctx.ID().getText());
        }

        subprogramNameTable.put(functionName, "συνάρτηση");

        String javaReturnType = switch (glwssaReturnType)
        {
            case "ΑΚΕΡΑΙΑ" -> "int";
            case "ΠΡΑΓΜΑΤΙΚΗ" -> "double";
            case "ΛΟΓΙΚΗ" -> "boolean";
            case "ΧΑΡΑΚΤΗΡΑΣ" -> "String";
            default -> "Object";
        };

        Map<String, String> localVarTypes = new HashMap<>();
        if (ctx.declarations() != null)
        {
            for (GlwssaParser.Var_declContext varCtx : ctx.declarations().var_decl())
            {
                String typeKW = varCtx.TYPE_KW().getText();
                String javaType = switch (typeKW)
                {
                    case "ΑΚΕΡΑΙΕΣ:" -> "int";
                    case "ΠΡΑΓΜΑΤΙΚΕΣ:" -> "double";
                    case "ΛΟΓΙΚΕΣ:" -> "boolean";
                    case "ΧΑΡΑΚΤΗΡΕΣ:" -> "String";
                    default -> "Object";
                };

                for (GlwssaParser.Var_defContext defCtx : varCtx.var_def())
                {
                    String arrBrackets = "";
                    if (defCtx.NUMBER() != null && !defCtx.NUMBER().isEmpty()) {
                        for (int k = 0; k < defCtx.NUMBER().size(); k++) arrBrackets += "[]";
                    }
                    localVarTypes.put(Utils.toGreeklish(defCtx.ID().getText()), javaType + arrBrackets);
                }
            }
        }

        StringBuilder parametersCode = new StringBuilder();
        java.util.List<String> paramNames = new ArrayList<>();

        if (ctx.param_list() != null)
        {
            java.util.List<org.antlr.v4.runtime.tree.TerminalNode> paramIdNodes = ctx.param_list().ID();
            for (int i = 0; i < paramIdNodes.size(); i++)
            {
                String paramName = Utils.toGreeklish(paramIdNodes.get(i).getText());
                paramNames.add(paramName);

                String paramType = localVarTypes.getOrDefault(paramName, "int");
                symbolTableSubroutines.put(paramName, paramType);
                parametersCode.append(paramType).append(" ").append(paramName);
                if (i < paramIdNodes.size() - 1)
                    parametersCode.append(", ");
            }
        }

        functionCode.append("public static ").append(javaReturnType).append(" ").append(functionName).append("( ").append(parametersCode).append(") \n{\n");
        functionCode.append("    ").append(javaReturnType).append(" ").append(functionName).append(" = ").append(Utils.getDefaultValue(javaReturnType)).append(";\n");
        symbolTableSubroutines.put(functionName, javaReturnType);

        if (ctx.declarations() != null)
        {
            for (GlwssaParser.Var_declContext varCtx : ctx.declarations().var_decl())
            {
                String typeKW = varCtx.TYPE_KW().getText();
                String javaType = switch (typeKW)
                {
                    case "ΑΚΕΡΑΙΕΣ:" -> "int";
                    case "ΠΡΑΓΜΑΤΙΚΕΣ:" -> "float";
                    case "ΛΟΓΙΚΕΣ:" -> "boolean";
                    case "ΧΑΡΑΚΤΗΡΕΣ:" -> "String";
                    default -> "Object";
                };

                for (GlwssaParser.Var_defContext defCtx : varCtx.var_def())
                {
                    String varName = Utils.toGreeklish(defCtx.ID().getText());
                    if (!paramNames.contains(varName))
                    {
                        if (defCtx.NUMBER() != null && !defCtx.NUMBER().isEmpty()) {
                            String brackets = "";
                            String sizes = "";
                            for (org.antlr.v4.runtime.tree.TerminalNode numNode : defCtx.NUMBER()) {
                                brackets += "[]";
                                sizes += "[" + numNode.getText() + "]";
                            }
                            String arrayType = javaType + brackets;
                            symbolTableSubroutines.put(varName, arrayType);
                            functionCode.append("    ").append(arrayType).append(" ").append(varName)
                                    .append(" = new ").append(javaType).append(sizes).append(";\n");
                        } else {
                            symbolTableSubroutines.put(varName, javaType);
                            functionCode.append("    ").append(javaType).append(" ").append(varName)
                                    .append(" = ").append(Utils.getDefaultValue(javaType)).append(";\n");
                        }
                    }
                }
            }
        }

        for (GlwssaParser.StatementContext stmntCtx : ctx.statement())
        {
            functionCode.append("    ").append(visit(stmntCtx)).append("\n");
        }

        functionCode.append("    return ").append(functionName).append(";\n");
        functionCode.append("}\n\n");

        inSubprogram = false;
        return functionCode.toString();
    }

    @Override
    public String visitProcedure(GlwssaParser.ProcedureContext ctx)
    {
        inSubprogram = true;
        symbolTableSubroutines.clear();

        StringBuilder procedureCode = new StringBuilder();
        String procedureName = Utils.toGreeklish(ctx.ID().getText());


        if (subprogramNameTable.containsKey(procedureName))
        {
            throw new TranspilationException("Έχει ήδη δηλωθεί " + subprogramNameTable.get(procedureName) + " με όνομα " + ctx.ID().getText());
        }

        subprogramNameTable.put(procedureName, "διαδικασία");


        Map<String, String> declaredTypes = new HashMap<>();

        if (ctx.declarations() != null)
        {
            for (GlwssaParser.Var_declContext varCtx : ctx.declarations().var_decl())
            {
                String typeKW = varCtx.TYPE_KW().getText();
                String javaType = switch (typeKW)
                {
                    case "ΑΚΕΡΑΙΕΣ:" -> "int";
                    case "ΠΡΑΓΜΑΤΙΚΕΣ:" -> "double";
                    case "ΛΟΓΙΚΕΣ:" -> "boolean";
                    case "ΧΑΡΑΚΤΗΡΕΣ:" -> "String";
                    default -> "Object";
                };

                for (GlwssaParser.Var_defContext defCtx : varCtx.var_def())
                {
                    String arrBrackets = "";
                    if (defCtx.NUMBER() != null && !defCtx.NUMBER().isEmpty()) {
                        for (int k = 0; k < defCtx.NUMBER().size(); k++) arrBrackets += "[]";
                    }
                    declaredTypes.put(Utils.toGreeklish(defCtx.ID().getText()), javaType + arrBrackets);
                }
            }
        }

        StringBuilder parametersCode = new StringBuilder();
        java.util.List<String> paramNames = new ArrayList<>();

        if (ctx.param_list() != null)
        {
            java.util.List<org.antlr.v4.runtime.tree.TerminalNode> parameterNodes = ctx.param_list().ID();

            for (int i = 0; i < parameterNodes.size(); i++)
            {
                String parameterName = Utils.toGreeklish(parameterNodes.get(i).getText());
                paramNames.add(parameterName);

                String baseType = declaredTypes.getOrDefault(parameterName, "int");

                // Κρίσιμο: Οι πίνακες στη Java είναι ΗΔΗ references. Δεν θέλουν Wrapper.
                if (baseType.contains("[]")) {
                    symbolTableSubroutines.put(parameterName, baseType);
                    parametersCode.append(baseType).append(" ").append(parameterName);
                } else {
                    String wrapperType = getWrapperType(baseType);
                    symbolTableSubroutines.put(parameterName, wrapperType);
                    parametersCode.append(wrapperType).append(" ").append(parameterName);
                }

                if (i < parameterNodes.size() - 1)
                    parametersCode.append(", ");
            }
        }

        procedureCode.append("public static void ").append(procedureName).append("( ").append(parametersCode).append(" )\n{\n");

        if (ctx.declarations() != null)
        {
            for (GlwssaParser.Var_declContext varCtx : ctx.declarations().var_decl())
            {
                String typeKW = varCtx.TYPE_KW().getText();
                String javaType = switch (typeKW)
                {
                    case "ΑΚΕΡΑΙΕΣ:" -> "int";
                    case "ΠΡΑΓΜΑΤΙΚΕΣ:" -> "float";
                    case "ΛΟΓΙΚΕΣ:" -> "boolean";
                    case "ΧΑΡΑΚΤΗΡΕΣ:" -> "String";
                    default -> "Object";
                };

                for (GlwssaParser.Var_defContext defCtx : varCtx.var_def())
                {
                    String variableName = Utils.toGreeklish(defCtx.ID().getText());
                    if (!paramNames.contains(variableName))
                    {
                        if (defCtx.NUMBER() != null && !defCtx.NUMBER().isEmpty()) {
                            String brackets = "";
                            String sizes = "";
                            for (org.antlr.v4.runtime.tree.TerminalNode numNode : defCtx.NUMBER()) {
                                brackets += "[]";
                                sizes += "[" + numNode.getText() + "]";
                            }
                            String arrayType = javaType + brackets;
                            symbolTableSubroutines.put(variableName, arrayType);
                            procedureCode.append("    ").append(arrayType).append(" ").append(variableName)
                                    .append(" = new ").append(javaType).append(sizes).append(";\n");
                        } else {
                            symbolTableSubroutines.put(variableName, javaType);
                            procedureCode.append("    ").append(javaType).append(" ").append(variableName)
                                    .append(" = ").append(Utils.getDefaultValue(javaType)).append(";\n");
                        }
                    }
                }
            }
        }

        for (GlwssaParser.StatementContext stmntCtx : ctx.statement())
        {
            procedureCode.append("    ").append(visit(stmntCtx)).append("\n");
        }

        procedureCode.append("}\n\n");

        inSubprogram = false;
        return procedureCode.toString();
    }

    @Override
    public String visitProcedure_call_statement( GlwssaParser.Procedure_call_statementContext ctx )
    {
        StringBuilder callCode = new StringBuilder();
        String procName = Utils.toGreeklish(ctx.ID().getText());

        if (!subprogramNameTable.containsKey(procName))
        {
            throw new TranspilationException("Δεν υπάρχει διαδικασία με όνομα '" + ctx.ID().getText() + "'.");
        }

        if ("συνάρτηση".equals(subprogramNameTable.get(procName)))
        {
            throw new TranspilationException("Η συνάρτηση '" + ctx.ID().getText() + "' δεν μπορεί να κληθεί με την εντολή ΚΑΛΕΣΕ.");
        }

        callCode.append(procName).append("(");

        if (ctx.expr() != null && !ctx.expr().isEmpty())
        {
            for (int i = 0; i < ctx.expr().size(); i++)
            {
                callCode.append(visit(ctx.expr(i)));
                if (i < ctx.expr().size() - 1)
                {
                    callCode.append(", ");
                }
            }
        }
        callCode.append(");");

        return callCode.toString();
    }

    @Override
    public String visitUnaryMinusExpr(GlwssaParser.UnaryMinusExprContext ctx )
    {
        return "- " + visit(ctx.expr());
    }

    @Override
    public String visitNotExpr(GlwssaParser.NotExprContext ctx )
    {
        return "!(" + visit(ctx.expr()) + ")";
    }

    @Override
    public String visitLogicExpr(GlwssaParser.LogicExprContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String operator = ctx.op.getText();

        if (operator.equals("ΚΑΙ")) {
            operator = "&&";
        } else if (operator.equals("Η")) {
            operator = "||";
        }

        // Return without a trailing semicolon, as this is an expression
        return "( " + left + " " + operator + " " + right + " )";
    }

    @Override
    public String visitStringExpr(GlwssaParser.StringExprContext ctx) {
        // Επιστρέφει το κείμενο ακριβώς όπως γράφτηκε, μαζί με τα εισαγωγικά του
        return ctx.STRING().getText();
    }

    private String resolveVariableType ( String varName )
    {
        if( inSubprogram && symbolTableSubroutines.containsKey(varName) )
        {
            return symbolTableSubroutines.get(varName);
        }

        return symbolTable.get(varName);
    }

    private String getWrapperType(String javaType)
    {
        return switch (javaType) {
            case "int" -> "RefInt";
            case "double" -> "RefDouble";
            case "boolean" -> "RefBoolean";
            case "String" -> "RefString";
            default -> "RefObject";
        };
    }

    private boolean isStringType(GlwssaParser.ExprContext exprCtx) {
        if (exprCtx instanceof GlwssaParser.ParenExprContext) {
            return isStringType(((GlwssaParser.ParenExprContext) exprCtx).expr());//recursion YEAH
        }

        if (exprCtx instanceof GlwssaParser.IdExprContext) {
            String varName = Utils.toGreeklish(((GlwssaParser.IdExprContext) exprCtx).ID().getText());
            String type = resolveVariableType(varName);
            return type != null && type.contains("String");
        }

        if (exprCtx instanceof GlwssaParser.ArrayExprContext) {
            String arrayName = Utils.toGreeklish(((GlwssaParser.ArrayExprContext) exprCtx).array_access().ID().getText());
            String type = resolveVariableType(arrayName);
            return type != null && type.contains("String");
        }

        String text = exprCtx.getText();
        return text.startsWith("\"") || text.startsWith("'");
    }
}
