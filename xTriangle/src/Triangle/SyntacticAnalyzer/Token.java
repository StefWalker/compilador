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


final class Token extends Object {

  protected int kind;
  protected String spelling;
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
  // Yosua Blanco 26-sep-2021

  public static final int

    // literals, identifiers, operators...
    INTLITERAL	= 0,
    CHARLITERAL	= 1,
    IDENTIFIER	= 2,
    OPERATOR	= 3,

    // reserved words - must be in alphabetical order...
    ARRAY		= 4,
    CONST		= 5,
    DO			= 6,
    ELSE		= 7,
    END			= 8,
    FOR                 = 9,  // TOKEN AGREGADO
    FROM                = 10, // TOKEN AGREGADO
    FUNC		= 11,
    IF			= 12,
    IN			= 13,
    LET			= 14,
    LOCAL               = 15, // TOKEN AGREGADO
    OF			= 16,
    PROC		= 17,
    RANGE               = 18, // TOKEN AGREGADO
    RECORD		= 19,
    RECURSIVE           = 20, // TOKEN AGREGADO
    REPEAT              = 21, // TOKEN AGREGADO
    SELECT              = 22, // TOKEN AGREGADO
    SKIP                = 23, // TOKEN AGREGADO
    THEN		= 24,
    TO                  = 25, // TOKEN AGREGADO
    TYPE		= 26,
    UNTIL               = 27, // TOKEN AGREGADO
    VAR			= 28,
    WHEN                = 29, // TOKEN AGREGADO
    WHILE		= 30,

    // punctuation...
    DOT			= 31,
    COLON		= 32,
    SEMICOLON           = 33,
    COMMA		= 34,
    BECOMES		= 35,
    IS			= 36,
    OR                  = 37, // TOKEN AGREGADO
    DDOT                = 38, // TOKEN AGREGADO

    // brackets...
    LPAREN		= 39,
    RPAREN		= 40,
    LBRACKET            = 41,
    RBRACKET            = 42,
    LCURLY		= 43,
    RCURLY		= 44,
          

    // special tokens...
    EOT			= 45,
    ERROR		= 46;
    

  private static String[] tokenTable = new String[] {
    "<int>",
    "<char>",
    "<identifier>",
    "<operator>",
    "array",
    "const",
    "do",
    "else",
    "end",
    "for",       // TOKEN AGREGADO
    "from",      // TOKEN AGREGADO
    "func",
    "if",
    "in",
    "let",
    "local",     // TOKEN AGREGADO
    "of",
    "proc",
    "range",     // TOKEN AGREGADO
    "record",
    "recursive", // TOKEN AGREGADO
    "repeat",    // TOKEN AGREGADO
    "select",    // TOKEN AGREGADO
    "skip",      // TOKEN AGREGADO
    "then",
    "to",        // TOKEN AGREGADO
    "type",
    "until",     // TOKEN AGREGADO
    "var",
    "when",      // TOKEN AGREGADO 
    "while",
    ".",
    ":",
    ";",
    ",",
    ":=",
    "~",
    "|", // TOKEN AGREGADO
    "..",// TOKEN AGREGADO
    "(",
    ")",
    "[",
    "]",
    "{",
    "}",
    "",
    "<error>"
  };

  private final static int  firstReservedWord = Token.ARRAY,
          lastReservedWord  = Token.WHILE;

}
