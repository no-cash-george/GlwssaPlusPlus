import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public class GlwssaPlusPlusTranspiler extends GlwssaBaseVisitor<String>
{

    private Map<String, String> symbolTable = new HashMap<>();

    @Override
    public String visitProgram(GlwssaParser.ProgramContext ctx)
    {
        String programName = Utils.toGreeklish(ctx.ID().getText()); // get program name and translate to english

        StringBuilder translatedJavaCode = new StringBuilder();// inside here we will put the translated java code

        translatedJavaCode.append("import java.util.Scanner;\n\n");//import scanner

        translatedJavaCode.append("public class " + programName + "{\n");
        translatedJavaCode.append("public static void main(String[] args) {\n");

        translatedJavaCode.append("Scanner scanner = new Scanner(System.in);\n");

        if (ctx.declarations() != null)
        {
            translatedJavaCode.append(visit(ctx.declarations()));
        }

        for ( GlwssaParser.StatementContext stmtCtx : ctx.statement() )
        {
            translatedJavaCode.append(visit(stmtCtx) + "\n");
        }

        translatedJavaCode.append("}\n");
        translatedJavaCode.append("}\n");

        return translatedJavaCode.toString();
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
        String glwssa2JavaType = switch (glwssaType)
        {
            case "ΑΚΕΡΑΙΕΣ:" -> "int";
            case "ΠΡΑΓΜΑΤΙΚΕΣ:" -> "float";
            case "ΛΟΓΙΚΕΣ:" -> "boolean";
            case "ΧΑΡΑΚΤΗΡΕΣ:" -> "String";
            default -> "Object";
        };

        for (org.antlr.v4.runtime.tree.TerminalNode node : ctx.ID()) // save the variable types of each one on the symbolTable
        {
            String varName = Utils.toGreeklish(node.getText());
            symbolTable.put(varName, glwssa2JavaType);
        }

        String variables = ctx.ID().stream()
                .map(node -> Utils.toGreeklish(node.getText()))
                .collect(Collectors.joining(", "));

        return glwssa2JavaType + " " + variables + ";\n";
    }

    @Override
    public String visitAssignment(GlwssaParser.AssignmentContext ctx)
    {
        String varName = Utils.toGreeklish(ctx.ID().getText());
        String expression = visit(ctx.expr());

        return varName + " = " + expression + " ;";
    }

    @Override
    public String visitPrint_stmnt(GlwssaParser.Print_stmntContext ctx)
    {
        // Collect all items to print, join them with a "+" for Java's System.out.println
        String printContent = ctx.print_item().stream()
                .map(this::visit)
                .collect(Collectors.joining(" + \" \" + ")); // Add a space between variables

        return "System.out.println(" + printContent + ");";
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

        return left + " " + operator + " " + right;
    }

    @Override
    public String visitRelationalExpr(GlwssaParser.RelationalExprContext ctx)
    {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        String operator = ctx.op.getText();

        if (operator.equals("="))
        {
            operator = "==";
        }
        if (operator.equals("<>"))
        {
            operator = "!=";
        }

        return left + " " + operator + " " + right;
    }

    @Override
    public String visitNumberExpr(GlwssaParser.NumberExprContext ctx)
    {
        return ctx.NUMBER().getText();
    }

    @Override
    public String visitIdExpr(GlwssaParser.IdExprContext ctx)
    {
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
    public String visitRead_stmnt(GlwssaParser.Read_stmntContext ctx)
    {
        StringBuilder readCode = new StringBuilder();

        for (org.antlr.v4.runtime.tree.TerminalNode node : ctx.ID())// go through all the printed variables
        {
            String varName = Utils.toGreeklish(node.getText());

            String javaType = symbolTable.get(varName);

            if (javaType == null)
            {
                throw new RuntimeException("SEMANTIC ERROR: Variable '" + node.getText() + "' used in ΔΙΑΒΑΣΕ but was never declared in ΜΕΤΑΒΛΗΤΕΣ.");
            }

            String chosenScannerMethod = switch (javaType)
            {
                case "int" -> "nextInt()";
                case "float" -> "nextFloat()";
                case "boolean" -> "nextBoolean()";
                case "String" -> "next()";
                default -> "next()";
            };

            readCode.append(varName).append(" = scanner.").append(chosenScannerMethod).append(";\n");
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
    public String visitSelect_stmnt(GlwssaParser.Select_stmntContext ctx)
    {
        StringBuilder selectCode = new StringBuilder();

        String switchExpr = visit(ctx.expr());

        selectCode.append("switch ( " + switchExpr + " ) \n { \n");

        for ( GlwssaParser.Case_blockContext caseCtx : ctx.case_block())
        {
            selectCode.append(visit(caseCtx));
        }

        if ( ctx.default_block() != null )
        {
            selectCode.append(visit(ctx.default_block()));
        }

        selectCode.append("} \n");

        return selectCode.toString();
    }

    public String visitCase_block( GlwssaParser.Case_blockContext ctx )
    {
        StringBuilder caseBlockCode = new StringBuilder();

        for ( GlwssaParser.ExprContext caseExprCtx : ctx.expr())
        {
            String caseValue = visit(caseExprCtx);

            caseBlockCode.append("case " + caseValue + ":\n");
        }

        for ( GlwssaParser.StatementContext stmntCtx : ctx.statement())
        {
            caseBlockCode.append(visit(stmntCtx) + "\n");
        }

        caseBlockCode.append("break;\n");

        return caseBlockCode.toString();
    }

    @Override
    public String visitDefault_block ( GlwssaParser.Default_blockContext ctx)
    {
        StringBuilder defaultCodeBlock = new StringBuilder();

        defaultCodeBlock.append("default: \n");

        for ( GlwssaParser.StatementContext stmntCtx : ctx.statement() )
        {
            defaultCodeBlock.append(visit(stmntCtx) + "\n");
        }
        defaultCodeBlock.append("break;");

        return defaultCodeBlock.toString();
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
}
