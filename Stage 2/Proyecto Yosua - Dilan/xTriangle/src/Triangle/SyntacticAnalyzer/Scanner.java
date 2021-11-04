/*
 * @(#)Scanner.java                        2.1 2003/10/07
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
//librerias para html
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Scanner {

  private SourceFile sourceFile;
  private boolean debug;

  private char currentChar;
  private StringBuffer currentSpelling;
  private boolean currentlyScanningToken;
  //HTML
  private String HTMLdoc;
  
  private boolean isLetter(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  private boolean isDigit(char c) {
    return (c >= '0' && c <= '9');
  }

// isOperator returns true iff the given character is an operator character.

  private boolean isOperator(char c) {
    return (c == '+' || c == '-' || c == '*' || c == '/' ||
	    c == '=' || c == '<' || c == '>' || c == '\\' ||
	    c == '&' || c == '@' || c == '%' || c == '^' ||
	    c == '?');
  }


///////////////////////////////////////////////////////////////////////////////

  public Scanner(SourceFile source) {
    sourceFile = source;
    currentChar = sourceFile.getSource();
    debug = false;
    //HTML
    HTMLdoc = "";
  }

  public void enableDebugging() {
    debug = true;
  }

  // takeIt appends the current character to the current token, and gets
  // the next character from the source program.

  private void takeIt() {
    if (currentlyScanningToken)
      currentSpelling.append(currentChar);
    currentChar = sourceFile.getSource();
  }

  // scanSeparator skips a single separator.
  // HTML agregado 
  
  
  private void scanSeparator() {
    switch (currentChar) {
    case '!':
        {
            HTMLdoc += "<font color='#00b300'>"; // inicia un comentario en el html
            HTMLdoc += currentChar; //agrega al comentario
            takeIt();
            while ((currentChar != SourceFile.EOL) && (currentChar != SourceFile.EOT)){
                HTMLdoc += currentChar; // agrega al comentario
                takeIt(); 
            }
            if (currentChar == SourceFile.EOL){
                HTMLdoc += "</font></br>";//cierra el comentario
                takeIt();
            }    
        }
        break;
    case '\n': 
        takeIt();
        HTMLdoc += "<br>";//<extended> agrega un cambio de linea al html
        break;
    case ' ': 
        HTMLdoc += "&nbsp;";//<extended> agrega un espacio en blanco al html
        takeIt();
        break;
    case '\r': case '\t':
        takeIt();
        break;
    }
  }

  private int scanToken() {

    switch (currentChar) {

    case 'a':  case 'b':  case 'c':  case 'd':  case 'e':
    case 'f':  case 'g':  case 'h':  case 'i':  case 'j':
    case 'k':  case 'l':  case 'm':  case 'n':  case 'o':
    case 'p':  case 'q':  case 'r':  case 's':  case 't':
    case 'u':  case 'v':  case 'w':  case 'x':  case 'y':
    case 'z':
    case 'A':  case 'B':  case 'C':  case 'D':  case 'E':
    case 'F':  case 'G':  case 'H':  case 'I':  case 'J':
    case 'K':  case 'L':  case 'M':  case 'N':  case 'O':
    case 'P':  case 'Q':  case 'R':  case 'S':  case 'T':
    case 'U':  case 'V':  case 'W':  case 'X':  case 'Y':
    case 'Z':
      takeIt();
      while (isLetter(currentChar) || isDigit(currentChar))
        takeIt();
      return Token.IDENTIFIER;

    case '0':  case '1':  case '2':  case '3':  case '4':
    case '5':  case '6':  case '7':  case '8':  case '9':
      takeIt();
      while (isDigit(currentChar))
        takeIt();
      return Token.INTLITERAL;

    case '+':  case '-':  case '*': case '/':  case '=':
    case '<':  case '>':  case '\\':  case '&':  case '@':
    case '%':  case '^':  case '?':
      takeIt();
      while (isOperator(currentChar))
        takeIt();
      return Token.OPERATOR;

    case '\'':
      takeIt();
      takeIt(); // the quoted character
      if (currentChar == '\'') {
      	takeIt();
        return Token.CHARLITERAL;
      } else
        return Token.ERROR;

    case '.':
      takeIt();
       if(currentChar == '.'){ // ".." 
        takeIt();
        return Token.DOUBLEDOT;  //nuevo
      }
      return Token.DOT;

    case ':':
      takeIt();
      if (currentChar == '=') {
        takeIt();
        return Token.BECOMES;
      } else
        return Token.COLON;

    case ';':
      takeIt();
      return Token.SEMICOLON;

    case ',':
      takeIt();
      return Token.COMMA;

    case '~':
      takeIt();
      return Token.IS;

    case '(':
      takeIt();
      return Token.LPAREN;

    case ')':
      takeIt();
      return Token.RPAREN;

    case '[':
      takeIt();
      return Token.LBRACKET;

    case ']':
      takeIt();
      return Token.RBRACKET;

    case '{':
      takeIt();
      return Token.LCURLY;

    case '}':
      takeIt();
      return Token.RCURLY;
      
    case '|': // nuevo 
      takeIt();
      return Token.OR;

    case SourceFile.EOT:
      return Token.EOT;

    default:
      takeIt();
      return Token.ERROR;
    }
  }

  public Token scan () {
    Token tok;
    SourcePosition pos;
    int kind;

    currentlyScanningToken = false;
    while (currentChar == '!'
           || currentChar == ' '
           || currentChar == '\n'
           || currentChar == '\r'
           || currentChar == '\t')
      scanSeparator();

    currentlyScanningToken = true;
    currentSpelling = new StringBuffer("");
    pos = new SourcePosition();
    pos.start = sourceFile.getCurrentLine();

    kind = scanToken();
    boolean ident = (kind == Token.IDENTIFIER);// <extended> identifier/palabra reservada       
    pos.finish = sourceFile.getCurrentLine();
    tok = new Token(kind, currentSpelling.toString(), pos);
    addToHTML(tok, ident); // <extended> agrega el token al html
    if (debug)
      System.out.println(tok);
    return tok;
  }
    /* < Extended >
   * agraga un token al HTMLdoc
  */
  private void addToHTML(Token tok, boolean ident){
    switch (tok.kind) {

      case Token.INTLITERAL: // int
      case Token.CHARLITERAL: // char
          HTMLdoc += "<font color='#0000cd'>"+tok.spelling+"</font>";
          break;

      case Token.EOT:
          break;

      default:
          if(ident && tok.kind != Token.IDENTIFIER)// Palabra Reservada
              HTMLdoc += "<b>"+tok.spelling+"</b>";
          else
              HTMLdoc += tok.spelling;
          break;
    }
  }
  
  /* < Extended >
   * crea el html final
   * se ejecuta al final de la transmision con el token EOT
  */
    public void createHTML(String fileURL) {
        try {  
            FileWriter htmlFile = new FileWriter(fileURL);//declarar el archivo
            PrintWriter printer = new PrintWriter(htmlFile);//declarar un impresor
            
            printer.println("<html>");
            printer.println("<p style=\"font-family: 'Courier New', monospace\">"); //style
            printer.println(HTMLdoc);
            printer.println("</p>"); //style
            printer.println("</html>");
            
            printer.close();
            System.out.println("Archivo HTML generado en el siguiente directorio: " + fileURL + "\n");
            
        } catch (IOException ex) {
            Logger.getLogger(Scanner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
