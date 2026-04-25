// Generated from /Users/georgepapachristodoulou/Documents/Programming/Java/IntelliJ Project/GlwssaPlusPlus/src/Glwssa.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GlwssaParser}.
 */
public interface GlwssaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GlwssaParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GlwssaParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#declarations}.
	 * @param ctx the parse tree
	 */
	void enterDeclarations(GlwssaParser.DeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#declarations}.
	 * @param ctx the parse tree
	 */
	void exitDeclarations(GlwssaParser.DeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(GlwssaParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(GlwssaParser.Var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GlwssaParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GlwssaParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(GlwssaParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(GlwssaParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#print_stmnt}.
	 * @param ctx the parse tree
	 */
	void enterPrint_stmnt(GlwssaParser.Print_stmntContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#print_stmnt}.
	 * @param ctx the parse tree
	 */
	void exitPrint_stmnt(GlwssaParser.Print_stmntContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#print_item}.
	 * @param ctx the parse tree
	 */
	void enterPrint_item(GlwssaParser.Print_itemContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#print_item}.
	 * @param ctx the parse tree
	 */
	void exitPrint_item(GlwssaParser.Print_itemContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#read_stmnt}.
	 * @param ctx the parse tree
	 */
	void enterRead_stmnt(GlwssaParser.Read_stmntContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#read_stmnt}.
	 * @param ctx the parse tree
	 */
	void exitRead_stmnt(GlwssaParser.Read_stmntContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#if_stmnt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmnt(GlwssaParser.If_stmntContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#if_stmnt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmnt(GlwssaParser.If_stmntContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#else_if_block}.
	 * @param ctx the parse tree
	 */
	void enterElse_if_block(GlwssaParser.Else_if_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#else_if_block}.
	 * @param ctx the parse tree
	 */
	void exitElse_if_block(GlwssaParser.Else_if_blockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GlwssaParser#else_block}.
	 * @param ctx the parse tree
	 */
	void enterElse_block(GlwssaParser.Else_blockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GlwssaParser#else_block}.
	 * @param ctx the parse tree
	 */
	void exitElse_block(GlwssaParser.Else_blockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBoolExpr(GlwssaParser.BoolExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBoolExpr(GlwssaParser.BoolExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(GlwssaParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(GlwssaParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNumberExpr(GlwssaParser.NumberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNumberExpr(GlwssaParser.NumberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code RelationalExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterRelationalExpr(GlwssaParser.RelationalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code RelationalExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitRelationalExpr(GlwssaParser.RelationalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(GlwssaParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(GlwssaParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MathExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMathExpr(GlwssaParser.MathExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MathExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMathExpr(GlwssaParser.MathExprContext ctx);
}