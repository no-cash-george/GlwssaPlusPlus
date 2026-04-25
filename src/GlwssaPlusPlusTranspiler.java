import javax.annotation.processing.SupportedOptions;
import java.util.stream.Collectors;

public class GlwssaPlusPlusTranspiler extends GlwssaBaseVisitor<String>
{
    @Override
    public String visitProgram(GlwssaParser.ProgramContext ctx)
    {
        String programName = Utils.toGreeklish(ctx.ID().getText()); // get program name and translate to english

        StringBuilder translatedJavaCode = new StringBuilder();// inside here we will put the translated java code

        translatedJavaCode.append("public class " + programName + "{\n");
        translatedJavaCode.append("public static void main(String[] args) {\n");

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
}
