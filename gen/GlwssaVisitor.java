// Generated from /Users/georgepapachristodoulou/Documents/Programming/Java/IntelliJ Project/GlwssaPlusPlus/src/Glwssa.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GlwssaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GlwssaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GlwssaParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GlwssaParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlwssaParser#declarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarations(GlwssaParser.DeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlwssaParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_decl(GlwssaParser.Var_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlwssaParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GlwssaParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlwssaParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(GlwssaParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlwssaParser#print_stmnt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_stmnt(GlwssaParser.Print_stmntContext ctx);
	/**
	 * Visit a parse tree produced by {@link GlwssaParser#print_item}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint_item(GlwssaParser.Print_itemContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BoolExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolExpr(GlwssaParser.BoolExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(GlwssaParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NumberExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberExpr(GlwssaParser.NumberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code RelationalExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpr(GlwssaParser.RelationalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(GlwssaParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MathExpr}
	 * labeled alternative in {@link GlwssaParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMathExpr(GlwssaParser.MathExprContext ctx);
}