package br.univali.ps.dominio;

import java.io.*;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.*;


%%

%public
%class PortugolTokenMaker
%extends AbstractJFlexCTokenMaker
%unicode
%type br.univali.ps.dominio


%{


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public PortugolTokenMaker() {
		super();
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 * @see #addToken(int, int, int)
	 */
	private void addHyperlinkToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so, true);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *                    occurs.
	 */
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * Returns the text to place at the beginning and end of a
	 * line to "comment" it in a this programming language.
	 *
	 * @return The start and end strings to add to a line to "comment"
	 *         it out.
	 */
	public String[] getLineCommentStartAndEnd() {
		return new String[] { "//", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = Token.NULL;
		switch (initialTokenType) {
			case Token.COMMENT_MULTILINE:
				state = MLC;
				start = text.offset;
				break;
			default:
				state = Token.NULL;
		}

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 * @exception   IOException  if any I/O-Error occurs.
	 */
	private boolean zzRefill() throws java.io.IOException {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(java.io.Reader reader) throws java.io.IOException {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}


%}

Letter				= [A-Za-z]
LetterOrUnderscore	= ({Letter}|"_")
Digit				= [0-9]
HexDigit			= {Digit}|[A-Fa-f]
OctalDigit			= [0-7]
Exponent			= [eE][+-]?{Digit}+

PreprocessorWord	= define|elif|else|endif|error|if|ifdef|ifndef|include|line|pragma|undef

Trigraph				= ("??="|"??("|"??)"|"??/"|"??'"|"??<"|"??>"|"??!"|"??-")

OctEscape1			= ([\\]{OctalDigit})
OctEscape2			= ([\\]{OctalDigit}{OctalDigit})
OctEscape3			= ([\\][0-3]{OctalDigit}{OctalDigit})
OctEscape				= ({OctEscape1}|{OctEscape2}|{OctEscape3})
HexEscape				= ([\\][xX]{HexDigit}{HexDigit})

AnyChrChr					= ([^\'\n\\])
Escape					= ([\\]([abfnrtv\'\"\?\\0]))
UnclosedCharLiteral			= ([\']({Escape}|{OctEscape}|{HexEscape}|{Trigraph}|{AnyChrChr}))
CharLiteral				= ({UnclosedCharLiteral}[\'])
ErrorUnclosedCharLiteral		= ([\'][^\'\n]*)
ErrorCharLiteral			= (([\'][\'])|{ErrorUnclosedCharLiteral}[\'])
AnyStrChr					= ([^\"\n\\])
FalseTrigraph				= (("?"(("?")*)[^\=\(\)\/\'\<\>\!\-\\\?\"\n])|("?"[\=\(\)\/\'\<\>\!\-]))
StringLiteral				= ([\"]((((("?")*)({Escape}|{OctEscape}|{HexEscape}|{Trigraph}))|{FalseTrigraph}|{AnyStrChr})*)(("?")*)[\"])
UnclosedStringLiteral		= ([\"]([\\].|[^\\\"])*[^\"]?)
ErrorStringLiteral			= ({UnclosedStringLiteral}[\"])


LineTerminator		= \n
WhiteSpace		= [ \t\f]

MLCBegin			= "/*"
MLCEnd			= "*/"
LineCommentBegin	= "//"

NonFloatSuffix		= (([uU][lL]?)|([lL][uU]?))
IntegerLiteral		= ({Digit}+{Exponent}?{NonFloatSuffix}?)
HexLiteral		= ("0"[xX]{HexDigit}+{NonFloatSuffix}?)
BinLiteral              = ("0"[bB](0|1)+{NonFloatSuffix}?)
FloatLiteral		= ((({Digit}*[\.]{Digit}+)|({Digit}+[\.]{Digit}*)){Exponent}?[fFlL]?)
ErrorNumberFormat	= (({IntegerLiteral}|{HexLiteral}|{BinLiteral}|{FloatLiteral}){NonSeparator}+)

NonSeparator		= ([^\t\f\r\n\ \(\)\{\}\[\]\;\,\.\=\>\<\!\~\?\:\+\-\*\/\&\|\^\%\"\']|"#")
Identifier		= ({LetterOrUnderscore}({LetterOrUnderscore}|{Digit}|[$])*)
ErrorIdentifier	= ({NonSeparator}+)

URLGenDelim				= ([:\/\?#\[\]@])
URLSubDelim				= ([\!\$&'\(\)\*\+,;=])
URLUnreserved			= ({LetterOrUnderscore}|{Digit}|[\-\.\~])
URLCharacter			= ({URLGenDelim}|{URLSubDelim}|{URLUnreserved}|[%])
URLCharacters			= ({URLCharacter}*)
URLEndCharacter			= ([\/\$]|{Letter}|{Digit})
URL						= (((https?|f(tp|ile))"://"|"www.")({URLCharacters}{URLEndCharacter})?)


%state MLC
%state EOL_COMMENT

%%

<YYINITIAL> {

	/* Keywords */
	"programa" |
	"escolha" |
	"caso" |
	"contrario" |
	"const" |
	"funcao" |
	"retorne" |
	"para" |
	"pare" |
	"faca" |
	"enquanto" |
	"se" |
	"senao" |
	"inclua" |
        "e" |
        "nao" |
	"ou" |
	"biblioteca" 			{ addToken(Token.RESERVED_WORD); }

	/* Boolean literals. */
	"verdadeiro" |
	"falso"                         { addToken(Token.LITERAL_BOOLEAN); }

	/* Data types. */
	"logico" |
	"cadeia" |
	"caracter" |
	"real" |
	"inteiro" |
	"vazio" 				{ addToken(Token.DATA_TYPE); }

	
	"escreva" |
	"leia"  				{ addToken(Token.FUNCTION); }

	

	{LineTerminator}				{ addNullToken(); return firstToken; }

	{Identifier}					{ addToken(Token.IDENTIFIER); }

	{WhiteSpace}+					{ addToken(Token.WHITESPACE); }

	/* Preprocessor directives */
	"#"{WhiteSpace}*{PreprocessorWord}	{ addToken(Token.PREPROCESSOR); }

	/* String/Character Literals. */
	{CharLiteral}					{ addToken(Token.LITERAL_CHAR); }
	{UnclosedCharLiteral}			{ addToken(Token.ERROR_CHAR); /*addNullToken(); return firstToken;*/ }
	{ErrorUnclosedCharLiteral}		{ addToken(Token.ERROR_CHAR); addNullToken(); return firstToken; }
	{ErrorCharLiteral}				{ addToken(Token.ERROR_CHAR); }
	{StringLiteral}				{ addToken(Token.LITERAL_STRING_DOUBLE_QUOTE); }
	{UnclosedStringLiteral}			{ addToken(Token.ERROR_STRING_DOUBLE); addNullToken(); return firstToken; }
	{ErrorStringLiteral}			{ addToken(Token.ERROR_STRING_DOUBLE); }

	/* Comment Literals. */
	{MLCBegin}					{ start = zzMarkedPos-2; yybegin(MLC); }
	{LineCommentBegin}			{ start = zzMarkedPos-2; yybegin(EOL_COMMENT); }

	/* Separators. */
	"(" |
	")" |
	"[" |
	"]" |
	"{" |
	"}"							{ addToken(Token.SEPARATOR); }

	/* Operators. */
	{Trigraph} |
	"=" |
	"+" |
	"-" |
	"*" |
	"/" |
	"%" |
	"<" |
	">" |	
	"==" |
	"+=" |
	"-=" |
	"*=" |
	"/=" |
	"%=" |
	"&" |
        "|" |
        "^" |
        "~" |
	":" |
	"," |
	"." |
	"++" |
	"--" 						{ addToken(Token.OPERATOR); }

	/* Numbers */
	{IntegerLiteral}				{ addToken(Token.LITERAL_NUMBER_DECIMAL_INT); }
	{HexLiteral}					{ addToken(Token.LITERAL_NUMBER_HEXADECIMAL); }
	{BinLiteral}                                    { addToken(Token.LITERAL_NUMBER_HEXADECIMAL); }
        {FloatLiteral}					{ addToken(Token.LITERAL_NUMBER_FLOAT); }
	{ErrorNumberFormat}				{ addToken(Token.ERROR_NUMBER_FORMAT); }

	/* Some lines will end in '\' to wrap an expression. */
	"\\"							{ addToken(Token.IDENTIFIER); }

	{ErrorIdentifier}				{ addToken(Token.ERROR_IDENTIFIER); }

	/* Other punctuation, we'll highlight it as "identifiers." */
	";"							{ addToken(Token.IDENTIFIER); }

	/* Ended with a line not in a string or comment. */
	<<EOF>>						{ addNullToken(); return firstToken; }

	/* Catch any other (unhandled) characters and flag them as bad. */
	.							{ addToken(Token.ERROR_IDENTIFIER); }

}

<MLC> {

	[^hwf\n\*]+			{}
	{URL}				{ int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_MULTILINE); start = zzMarkedPos; }
	[hwf]				{}

	\n					{ addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }
	{MLCEnd}			{ yybegin(YYINITIAL); addToken(start,zzStartRead+1, Token.COMMENT_MULTILINE); }
	\*					{}
	<<EOF>>				{ addToken(start,zzStartRead-1, Token.COMMENT_MULTILINE); return firstToken; }

}

<EOL_COMMENT> {
	[^hwf\n]+				{}
	{URL}					{ int temp=zzStartRead; addToken(start,zzStartRead-1, Token.COMMENT_EOL); addHyperlinkToken(temp,zzMarkedPos-1, Token.COMMENT_EOL); start = zzMarkedPos; }
	[hwf]					{}
	\n						{ addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken; }
	<<EOF>>					{ addToken(start,zzStartRead-1, Token.COMMENT_EOL); addNullToken(); return firstToken; }

}
