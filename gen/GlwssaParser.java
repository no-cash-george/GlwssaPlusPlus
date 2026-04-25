// Generated from /Users/georgepapachristodoulou/Documents/Programming/Java/IntelliJ Project/GlwssaPlusPlus/src/Glwssa.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GlwssaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, PROGRAM_KW=4, VARS_KW=5, START_KW=6, END_KW=7, 
		PRINT_KW=8, READ_KW=9, IF_KW=10, THEN_KW=11, ELSE_IF_KW=12, ELSE_KW=13, 
		END_IF_KW=14, TYPE_KW=15, TRUE_KW=16, FALSE_KW=17, ASSIGN=18, PLUS=19, 
		MINUS=20, MULT=21, SLASH=22, DIV_KW=23, MOD_KW=24, EQ=25, NEQ=26, LT=27, 
		GT=28, LTE=29, GTE=30, ID=31, NUMBER=32, STRING=33, COMMENT=34, WS=35;
	public static final int
		RULE_program = 0, RULE_declarations = 1, RULE_var_decl = 2, RULE_statement = 3, 
		RULE_assignment = 4, RULE_print_stmnt = 5, RULE_print_item = 6, RULE_read_stmnt = 7, 
		RULE_if_stmnt = 8, RULE_else_if_block = 9, RULE_else_block = 10, RULE_expr = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "declarations", "var_decl", "statement", "assignment", "print_stmnt", 
			"print_item", "read_stmnt", "if_stmnt", "else_if_block", "else_block", 
			"expr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "'('", "')'", "'\\u03A0\\u03A1\\u039F\\u0393\\u03A1\\u0391\\u039C\\u039C\\u0391'", 
			"'\\u039C\\u0395\\u03A4\\u0391\\u0392\\u039B\\u0397\\u03A4\\u0395\\u03A3'", 
			"'\\u0391\\u03A1\\u03A7\\u0397'", "'\\u03A4\\u0395\\u039B\\u039F\\u03A3_\\u03A0\\u03A1\\u039F\\u0393\\u03A1\\u0391\\u039C\\u039C\\u0391\\u03A4\\u039F\\u03A3'", 
			"'\\u0393\\u03A1\\u0391\\u03A8\\u0395'", "'\\u0394\\u0399\\u0391\\u0392\\u0391\\u03A3\\u0395'", 
			"'\\u0391\\u039D'", "'\\u03A4\\u039F\\u03A4\\u0395'", "'\\u0391\\u039B\\u039B\\u0399\\u03A9\\u03A3_\\u0391\\u039D'", 
			"'\\u0391\\u039B\\u039B\\u0399\\u03A9\\u03A3'", "'\\u03A4\\u0395\\u039B\\u039F\\u03A3_\\u0391\\u039D'", 
			null, "'\\u0391\\u039B\\u0397\\u0398\\u0397\\u03A3'", "'\\u03A8\\u0395\\u03A5\\u0394\\u0397\\u03A3'", 
			"'<-'", "'+'", "'-'", "'*'", "'/'", "'DIV'", "'MOD'", "'='", "'<>'", 
			"'<'", "'>'", "'<='", "'>='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "PROGRAM_KW", "VARS_KW", "START_KW", "END_KW", 
			"PRINT_KW", "READ_KW", "IF_KW", "THEN_KW", "ELSE_IF_KW", "ELSE_KW", "END_IF_KW", 
			"TYPE_KW", "TRUE_KW", "FALSE_KW", "ASSIGN", "PLUS", "MINUS", "MULT", 
			"SLASH", "DIV_KW", "MOD_KW", "EQ", "NEQ", "LT", "GT", "LTE", "GTE", "ID", 
			"NUMBER", "STRING", "COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Glwssa.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GlwssaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode PROGRAM_KW() { return getToken(GlwssaParser.PROGRAM_KW, 0); }
		public TerminalNode ID() { return getToken(GlwssaParser.ID, 0); }
		public DeclarationsContext declarations() {
			return getRuleContext(DeclarationsContext.class,0);
		}
		public TerminalNode START_KW() { return getToken(GlwssaParser.START_KW, 0); }
		public TerminalNode END_KW() { return getToken(GlwssaParser.END_KW, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(PROGRAM_KW);
			setState(25);
			match(ID);
			setState(26);
			declarations();
			setState(27);
			match(START_KW);
			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2147485440L) != 0)) {
				{
				{
				setState(28);
				statement();
				}
				}
				setState(33);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(34);
			match(END_KW);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationsContext extends ParserRuleContext {
		public TerminalNode VARS_KW() { return getToken(GlwssaParser.VARS_KW, 0); }
		public List<Var_declContext> var_decl() {
			return getRuleContexts(Var_declContext.class);
		}
		public Var_declContext var_decl(int i) {
			return getRuleContext(Var_declContext.class,i);
		}
		public DeclarationsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarations; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterDeclarations(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitDeclarations(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitDeclarations(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationsContext declarations() throws RecognitionException {
		DeclarationsContext _localctx = new DeclarationsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declarations);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			match(VARS_KW);
			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TYPE_KW) {
				{
				{
				setState(37);
				var_decl();
				}
				}
				setState(42);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_declContext extends ParserRuleContext {
		public TerminalNode TYPE_KW() { return getToken(GlwssaParser.TYPE_KW, 0); }
		public List<TerminalNode> ID() { return getTokens(GlwssaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GlwssaParser.ID, i);
		}
		public Var_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterVar_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitVar_decl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitVar_decl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_declContext var_decl() throws RecognitionException {
		Var_declContext _localctx = new Var_declContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_var_decl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			match(TYPE_KW);
			setState(44);
			match(ID);
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(45);
				match(T__0);
				setState(46);
				match(ID);
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public Print_stmntContext print_stmnt() {
			return getRuleContext(Print_stmntContext.class,0);
		}
		public Read_stmntContext read_stmnt() {
			return getRuleContext(Read_stmntContext.class,0);
		}
		public If_stmntContext if_stmnt() {
			return getRuleContext(If_stmntContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement);
		try {
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				assignment();
				}
				break;
			case PRINT_KW:
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				print_stmnt();
				}
				break;
			case READ_KW:
				enterOuterAlt(_localctx, 3);
				{
				setState(54);
				read_stmnt();
				}
				break;
			case IF_KW:
				enterOuterAlt(_localctx, 4);
				{
				setState(55);
				if_stmnt();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GlwssaParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GlwssaParser.ASSIGN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(ID);
			setState(59);
			match(ASSIGN);
			setState(60);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Print_stmntContext extends ParserRuleContext {
		public TerminalNode PRINT_KW() { return getToken(GlwssaParser.PRINT_KW, 0); }
		public List<Print_itemContext> print_item() {
			return getRuleContexts(Print_itemContext.class);
		}
		public Print_itemContext print_item(int i) {
			return getRuleContext(Print_itemContext.class,i);
		}
		public Print_stmntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_stmnt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterPrint_stmnt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitPrint_stmnt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitPrint_stmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Print_stmntContext print_stmnt() throws RecognitionException {
		Print_stmntContext _localctx = new Print_stmntContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_print_stmnt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(PRINT_KW);
			setState(63);
			print_item();
			setState(68);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(64);
				match(T__0);
				setState(65);
				print_item();
				}
				}
				setState(70);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Print_itemContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode STRING() { return getToken(GlwssaParser.STRING, 0); }
		public Print_itemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_item; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterPrint_item(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitPrint_item(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitPrint_item(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Print_itemContext print_item() throws RecognitionException {
		Print_itemContext _localctx = new Print_itemContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_print_item);
		try {
			setState(73);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
			case TRUE_KW:
			case FALSE_KW:
			case ID:
			case NUMBER:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				expr(0);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Read_stmntContext extends ParserRuleContext {
		public TerminalNode READ_KW() { return getToken(GlwssaParser.READ_KW, 0); }
		public List<TerminalNode> ID() { return getTokens(GlwssaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(GlwssaParser.ID, i);
		}
		public Read_stmntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read_stmnt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterRead_stmnt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitRead_stmnt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitRead_stmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Read_stmntContext read_stmnt() throws RecognitionException {
		Read_stmntContext _localctx = new Read_stmntContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_read_stmnt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(READ_KW);
			setState(76);
			match(ID);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(77);
				match(T__0);
				setState(78);
				match(ID);
				}
				}
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class If_stmntContext extends ParserRuleContext {
		public TerminalNode IF_KW() { return getToken(GlwssaParser.IF_KW, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode THEN_KW() { return getToken(GlwssaParser.THEN_KW, 0); }
		public TerminalNode END_IF_KW() { return getToken(GlwssaParser.END_IF_KW, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<Else_if_blockContext> else_if_block() {
			return getRuleContexts(Else_if_blockContext.class);
		}
		public Else_if_blockContext else_if_block(int i) {
			return getRuleContext(Else_if_blockContext.class,i);
		}
		public Else_blockContext else_block() {
			return getRuleContext(Else_blockContext.class,0);
		}
		public If_stmntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stmnt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterIf_stmnt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitIf_stmnt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitIf_stmnt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_stmntContext if_stmnt() throws RecognitionException {
		If_stmntContext _localctx = new If_stmntContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_if_stmnt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(IF_KW);
			setState(85);
			expr(0);
			setState(86);
			match(THEN_KW);
			setState(90);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2147485440L) != 0)) {
				{
				{
				setState(87);
				statement();
				}
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELSE_IF_KW) {
				{
				{
				setState(93);
				else_if_block();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE_KW) {
				{
				setState(99);
				else_block();
				}
			}

			setState(102);
			match(END_IF_KW);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Else_if_blockContext extends ParserRuleContext {
		public TerminalNode ELSE_IF_KW() { return getToken(GlwssaParser.ELSE_IF_KW, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode THEN_KW() { return getToken(GlwssaParser.THEN_KW, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Else_if_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_if_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterElse_if_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitElse_if_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitElse_if_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Else_if_blockContext else_if_block() throws RecognitionException {
		Else_if_blockContext _localctx = new Else_if_blockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_else_if_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(ELSE_IF_KW);
			setState(105);
			expr(0);
			setState(106);
			match(THEN_KW);
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2147485440L) != 0)) {
				{
				{
				setState(107);
				statement();
				}
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Else_blockContext extends ParserRuleContext {
		public TerminalNode ELSE_KW() { return getToken(GlwssaParser.ELSE_KW, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Else_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterElse_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitElse_block(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitElse_block(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Else_blockContext else_block() throws RecognitionException {
		Else_blockContext _localctx = new Else_blockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_else_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(ELSE_KW);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2147485440L) != 0)) {
				{
				{
				setState(114);
				statement();
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BoolExprContext extends ExprContext {
		public TerminalNode TRUE_KW() { return getToken(GlwssaParser.TRUE_KW, 0); }
		public TerminalNode FALSE_KW() { return getToken(GlwssaParser.FALSE_KW, 0); }
		public BoolExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitBoolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdExprContext extends ExprContext {
		public TerminalNode ID() { return getToken(GlwssaParser.ID, 0); }
		public IdExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterIdExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitIdExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitIdExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberExprContext extends ExprContext {
		public TerminalNode NUMBER() { return getToken(GlwssaParser.NUMBER, 0); }
		public NumberExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterNumberExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitNumberExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitNumberExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RelationalExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(GlwssaParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(GlwssaParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(GlwssaParser.LT, 0); }
		public TerminalNode GT() { return getToken(GlwssaParser.GT, 0); }
		public TerminalNode LTE() { return getToken(GlwssaParser.LTE, 0); }
		public TerminalNode GTE() { return getToken(GlwssaParser.GTE, 0); }
		public RelationalExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterRelationalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitRelationalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitRelationalExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParenExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MathExprContext extends ExprContext {
		public Token op;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode MULT() { return getToken(GlwssaParser.MULT, 0); }
		public TerminalNode DIV_KW() { return getToken(GlwssaParser.DIV_KW, 0); }
		public TerminalNode MOD_KW() { return getToken(GlwssaParser.MOD_KW, 0); }
		public TerminalNode SLASH() { return getToken(GlwssaParser.SLASH, 0); }
		public TerminalNode PLUS() { return getToken(GlwssaParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(GlwssaParser.MINUS, 0); }
		public MathExprContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).enterMathExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GlwssaListener ) ((GlwssaListener)listener).exitMathExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GlwssaVisitor ) return ((GlwssaVisitor<? extends T>)visitor).visitMathExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NUMBER:
				{
				_localctx = new NumberExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(121);
				match(NUMBER);
				}
				break;
			case ID:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(122);
				match(ID);
				}
				break;
			case TRUE_KW:
				{
				_localctx = new BoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				match(TRUE_KW);
				}
				break;
			case FALSE_KW:
				{
				_localctx = new BoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(124);
				match(FALSE_KW);
				}
				break;
			case T__1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(125);
				match(T__1);
				setState(126);
				expr(0);
				setState(127);
				match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(142);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(140);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new MathExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(131);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(132);
						((MathExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 31457280L) != 0)) ) {
							((MathExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(133);
						expr(9);
						}
						break;
					case 2:
						{
						_localctx = new MathExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(134);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(135);
						((MathExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((MathExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(136);
						expr(8);
						}
						break;
					case 3:
						{
						_localctx = new RelationalExprContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(137);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(138);
						((RelationalExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2113929216L) != 0)) ) {
							((RelationalExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(139);
						expr(7);
						}
						break;
					}
					} 
				}
				setState(144);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 11:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 8);
		case 1:
			return precpred(_ctx, 7);
		case 2:
			return precpred(_ctx, 6);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001#\u0092\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u001e"+
		"\b\u0000\n\u0000\f\u0000!\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0005\u0001\'\b\u0001\n\u0001\f\u0001*\t\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u00020\b\u0002\n\u0002\f\u0002"+
		"3\t\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"9\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005C\b\u0005\n\u0005\f\u0005"+
		"F\t\u0005\u0001\u0006\u0001\u0006\u0003\u0006J\b\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0005\u0007P\b\u0007\n\u0007\f\u0007S\t"+
		"\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0005\bY\b\b\n\b\f\b\\\t\b\u0001"+
		"\b\u0005\b_\b\b\n\b\f\bb\t\b\u0001\b\u0003\be\b\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0005\tm\b\t\n\t\f\tp\t\t\u0001\n\u0001\n\u0005"+
		"\nt\b\n\n\n\f\nw\t\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0082"+
		"\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u008d\b\u000b\n"+
		"\u000b\f\u000b\u0090\t\u000b\u0001\u000b\u0000\u0001\u0016\f\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0000\u0003\u0001\u0000"+
		"\u0015\u0018\u0001\u0000\u0013\u0014\u0001\u0000\u0019\u001e\u009a\u0000"+
		"\u0018\u0001\u0000\u0000\u0000\u0002$\u0001\u0000\u0000\u0000\u0004+\u0001"+
		"\u0000\u0000\u0000\u00068\u0001\u0000\u0000\u0000\b:\u0001\u0000\u0000"+
		"\u0000\n>\u0001\u0000\u0000\u0000\fI\u0001\u0000\u0000\u0000\u000eK\u0001"+
		"\u0000\u0000\u0000\u0010T\u0001\u0000\u0000\u0000\u0012h\u0001\u0000\u0000"+
		"\u0000\u0014q\u0001\u0000\u0000\u0000\u0016\u0081\u0001\u0000\u0000\u0000"+
		"\u0018\u0019\u0005\u0004\u0000\u0000\u0019\u001a\u0005\u001f\u0000\u0000"+
		"\u001a\u001b\u0003\u0002\u0001\u0000\u001b\u001f\u0005\u0006\u0000\u0000"+
		"\u001c\u001e\u0003\u0006\u0003\u0000\u001d\u001c\u0001\u0000\u0000\u0000"+
		"\u001e!\u0001\u0000\u0000\u0000\u001f\u001d\u0001\u0000\u0000\u0000\u001f"+
		" \u0001\u0000\u0000\u0000 \"\u0001\u0000\u0000\u0000!\u001f\u0001\u0000"+
		"\u0000\u0000\"#\u0005\u0007\u0000\u0000#\u0001\u0001\u0000\u0000\u0000"+
		"$(\u0005\u0005\u0000\u0000%\'\u0003\u0004\u0002\u0000&%\u0001\u0000\u0000"+
		"\u0000\'*\u0001\u0000\u0000\u0000(&\u0001\u0000\u0000\u0000()\u0001\u0000"+
		"\u0000\u0000)\u0003\u0001\u0000\u0000\u0000*(\u0001\u0000\u0000\u0000"+
		"+,\u0005\u000f\u0000\u0000,1\u0005\u001f\u0000\u0000-.\u0005\u0001\u0000"+
		"\u0000.0\u0005\u001f\u0000\u0000/-\u0001\u0000\u0000\u000003\u0001\u0000"+
		"\u0000\u00001/\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u00002\u0005"+
		"\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000049\u0003\b\u0004\u0000"+
		"59\u0003\n\u0005\u000069\u0003\u000e\u0007\u000079\u0003\u0010\b\u0000"+
		"84\u0001\u0000\u0000\u000085\u0001\u0000\u0000\u000086\u0001\u0000\u0000"+
		"\u000087\u0001\u0000\u0000\u00009\u0007\u0001\u0000\u0000\u0000:;\u0005"+
		"\u001f\u0000\u0000;<\u0005\u0012\u0000\u0000<=\u0003\u0016\u000b\u0000"+
		"=\t\u0001\u0000\u0000\u0000>?\u0005\b\u0000\u0000?D\u0003\f\u0006\u0000"+
		"@A\u0005\u0001\u0000\u0000AC\u0003\f\u0006\u0000B@\u0001\u0000\u0000\u0000"+
		"CF\u0001\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000DE\u0001\u0000\u0000"+
		"\u0000E\u000b\u0001\u0000\u0000\u0000FD\u0001\u0000\u0000\u0000GJ\u0003"+
		"\u0016\u000b\u0000HJ\u0005!\u0000\u0000IG\u0001\u0000\u0000\u0000IH\u0001"+
		"\u0000\u0000\u0000J\r\u0001\u0000\u0000\u0000KL\u0005\t\u0000\u0000LQ"+
		"\u0005\u001f\u0000\u0000MN\u0005\u0001\u0000\u0000NP\u0005\u001f\u0000"+
		"\u0000OM\u0001\u0000\u0000\u0000PS\u0001\u0000\u0000\u0000QO\u0001\u0000"+
		"\u0000\u0000QR\u0001\u0000\u0000\u0000R\u000f\u0001\u0000\u0000\u0000"+
		"SQ\u0001\u0000\u0000\u0000TU\u0005\n\u0000\u0000UV\u0003\u0016\u000b\u0000"+
		"VZ\u0005\u000b\u0000\u0000WY\u0003\u0006\u0003\u0000XW\u0001\u0000\u0000"+
		"\u0000Y\\\u0001\u0000\u0000\u0000ZX\u0001\u0000\u0000\u0000Z[\u0001\u0000"+
		"\u0000\u0000[`\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000]_\u0003"+
		"\u0012\t\u0000^]\u0001\u0000\u0000\u0000_b\u0001\u0000\u0000\u0000`^\u0001"+
		"\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000ad\u0001\u0000\u0000\u0000"+
		"b`\u0001\u0000\u0000\u0000ce\u0003\u0014\n\u0000dc\u0001\u0000\u0000\u0000"+
		"de\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000fg\u0005\u000e\u0000"+
		"\u0000g\u0011\u0001\u0000\u0000\u0000hi\u0005\f\u0000\u0000ij\u0003\u0016"+
		"\u000b\u0000jn\u0005\u000b\u0000\u0000km\u0003\u0006\u0003\u0000lk\u0001"+
		"\u0000\u0000\u0000mp\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000"+
		"no\u0001\u0000\u0000\u0000o\u0013\u0001\u0000\u0000\u0000pn\u0001\u0000"+
		"\u0000\u0000qu\u0005\r\u0000\u0000rt\u0003\u0006\u0003\u0000sr\u0001\u0000"+
		"\u0000\u0000tw\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000uv\u0001"+
		"\u0000\u0000\u0000v\u0015\u0001\u0000\u0000\u0000wu\u0001\u0000\u0000"+
		"\u0000xy\u0006\u000b\uffff\uffff\u0000y\u0082\u0005 \u0000\u0000z\u0082"+
		"\u0005\u001f\u0000\u0000{\u0082\u0005\u0010\u0000\u0000|\u0082\u0005\u0011"+
		"\u0000\u0000}~\u0005\u0002\u0000\u0000~\u007f\u0003\u0016\u000b\u0000"+
		"\u007f\u0080\u0005\u0003\u0000\u0000\u0080\u0082\u0001\u0000\u0000\u0000"+
		"\u0081x\u0001\u0000\u0000\u0000\u0081z\u0001\u0000\u0000\u0000\u0081{"+
		"\u0001\u0000\u0000\u0000\u0081|\u0001\u0000\u0000\u0000\u0081}\u0001\u0000"+
		"\u0000\u0000\u0082\u008e\u0001\u0000\u0000\u0000\u0083\u0084\n\b\u0000"+
		"\u0000\u0084\u0085\u0007\u0000\u0000\u0000\u0085\u008d\u0003\u0016\u000b"+
		"\t\u0086\u0087\n\u0007\u0000\u0000\u0087\u0088\u0007\u0001\u0000\u0000"+
		"\u0088\u008d\u0003\u0016\u000b\b\u0089\u008a\n\u0006\u0000\u0000\u008a"+
		"\u008b\u0007\u0002\u0000\u0000\u008b\u008d\u0003\u0016\u000b\u0007\u008c"+
		"\u0083\u0001\u0000\u0000\u0000\u008c\u0086\u0001\u0000\u0000\u0000\u008c"+
		"\u0089\u0001\u0000\u0000\u0000\u008d\u0090\u0001\u0000\u0000\u0000\u008e"+
		"\u008c\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000\u008f"+
		"\u0017\u0001\u0000\u0000\u0000\u0090\u008e\u0001\u0000\u0000\u0000\u000f"+
		"\u001f(18DIQZ`dnu\u0081\u008c\u008e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}