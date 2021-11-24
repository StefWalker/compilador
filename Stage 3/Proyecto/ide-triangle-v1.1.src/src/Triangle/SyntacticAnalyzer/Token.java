/*
 * @(#)Token.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */

package Triangle.SyntacticAnalyzer;


final public class Token extends Object {

  public int kind;
  public String spelling;
  protected SourcePosition position;

  public Token(int kind, String spelling, SourcePosition position) {

    if (kind == Token.IDENTIFIER) {
      int currentKind = firstReservedWord;
      boolean searching = true;

      while (searching) {
        int comparison = tokenTable[currentKind].compareTo(spelling);
        if (comparison == 0) {
          this.kind = currentKind;
          searching = false;
        } else if (comparison > 0 || currentKind == lastReservedWord) {
          this.kind = Token.IDENTIFIER;
          searching = false;
        } else {
          currentKind ++;
        }
      }
    } else
      this.kind = kind;

    this.spelling = spelling;
    this.position = position;

  }

  public static String spell (int kind) {
    return tokenTable[kind];
  }

  public String toString() {
    return "Kind=" + kind + ", spelling=" + spelling +
      ", position=" + position;
  }

  // Token classes...

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    OPERATOR	= 3,

    // reserved words - must be in alphabetical order...
    ARRAY		= 4,
    //BEGIN		= 5,    //Palabra reservada eliminada
    CONST		= 5,
    DO			= 6,
    ELSE		= 7,
    END			= 8,
    FOR                 = 9,    //Nueva palabra reservada
    FROM                = 10,   //Nueva palabra reservada
    FUNC		= 11,
    IF			= 12,
    IN			= 13,
    LET			= 14,
    LOCAL               = 15,   //Nueva palabra reservada
    OF			= 16,
    PROC		= 17,
    RANGE               = 18,   //Nueva palabra reservada
    RECORD		= 19,   
    RECURSIVE           = 20,   //Nueva palabra reservada
    REPEAT              = 21,   //Nueva palabra reservada
    SELECT              = 22,   //Nueva palabra reservada
    SKIP                = 23,   //Nueva palabra reservada
    THEN		= 24,
    TYPE		= 25,
    UNTIL               = 26,   //Nueva palabra reservada
    VAR			= 27,
    WHEN                = 28,   //Nueva palabra reservada
    WHILE		= 29,

    // punctuation...
    DOT			= 30,
    DOUBLEDOT           = 31,  //Nueva palabra reservada
    COLON		= 32,
    SEMICOLON           = 33,
    COMMA		= 34,
    BECOMES		= 35,  //":="
    IS			= 36,  //"~"

    // brackets...
    LPAREN		= 37,
    RPAREN		= 38,
    LBRACKET	        = 39,
    RBRACKET	        = 40,
    LCURLY		= 41,
    RCURLY		= 42,

    // special tokens...
  
    UPRSLASH            = 43, //Nueva palabra reservada ~ upright slash
    EOT			= 44,
    ERROR		= 45;

  private static String[] tokenTable = new String[] {
    "<int>",
    "<char>",
    "<identifier>",
    "<operator>",
    "array",
    //"begin",    //Palabra reservada eliminada
    "const",
    "do",
    "else",
    "end",
    "for",
    "from",       //Nueva palabra reservada
    "func",
    "if",
    "in",
    "let",
    "local",      //Nueva palabra reservada
    "of",
    "proc",
    "range",      //Nueva palabra reservada
    "record",
    "recursive",  //Nueva palabra reservada
    "repeat",     //Nueva palabra reservada
    "select",     //Nueva palabra reservada
    "skip",       //Nueva palabra reservada
    "then",
    "type",
    "until",      //Nueva palabra reservada
    "var",
    "when",       //Nueva palabra reservada
    "while",      //Nueva palabra reservada
    ".",
    "..",         //Nueva palabra reservada
    ":",
    ";",
    ",",
    ":=",
    "~",
    "(",
    ")",
    "[",
    "]",
    "{",
    "}",
    "|",         //Nueva palabra reservada
    "",
    "<error>"
  };

  private final static int	firstReservedWord = Token.ARRAY,
  				lastReservedWord  = Token.WHILE;
  
  public static boolean isReservedWord(String token) {
      boolean answer = false;
      for(int i = 4;i<30;i++) { // check con cada palabra reservada
        if(tokenTable[i].equals(token)) {
            answer = true;
            break;
        }
      }
      return answer;
}

}
