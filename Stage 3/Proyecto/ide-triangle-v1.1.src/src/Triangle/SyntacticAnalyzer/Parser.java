/*
 * @(#)Parser.java                        2.1 2003/10/07
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

import Triangle.ErrorReporter;
import Triangle.AbstractSyntaxTrees.ActualParameter;
import Triangle.AbstractSyntaxTrees.ActualParameterSequence;
import Triangle.AbstractSyntaxTrees.ArrayAggregate;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.Case;
import Triangle.AbstractSyntaxTrees.CaseLiteral;
import Triangle.AbstractSyntaxTrees.CaseLiteralCHAR;
import Triangle.AbstractSyntaxTrees.CaseLiteralINT;
import Triangle.AbstractSyntaxTrees.CaseLiterals;
import Triangle.AbstractSyntaxTrees.CaseRange;
import Triangle.AbstractSyntaxTrees.CaseRangeCase;
import Triangle.AbstractSyntaxTrees.CaseWhen;
import Triangle.AbstractSyntaxTrees.Cases;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.Command;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.ElseCase;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.Expression;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.InVarDecl;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.LocalProcFuncDeclaration;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.ProcFuncsDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFuncs;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RangeVarDecl;
import Triangle.AbstractSyntaxTrees.RecordAggregate;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatForRange;
import Triangle.AbstractSyntaxTrees.RepeatForRangeUntil;
import Triangle.AbstractSyntaxTrees.RepeatForRangeWhile;
import Triangle.AbstractSyntaxTrees.RepeatIn;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.SelectCommand;
import Triangle.AbstractSyntaxTrees.SequentialCase;
import Triangle.AbstractSyntaxTrees.SequentialCaseRange;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarDeclarationExpression;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Vname;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.RecursiveDeclaration;
import Triangle.AbstractSyntaxTrees.RecursiveFunc;
import Triangle.AbstractSyntaxTrees.RecursiveProc;
import Triangle.AbstractSyntaxTrees.SequentialProcFuncs;

public class Parser {

  private Scanner lexicalAnalyser;
  private ErrorReporter errorReporter;
  private Token currentToken;
  private SourcePosition previousTokenPosition;

  public Parser(Scanner lexer, ErrorReporter reporter) {
    lexicalAnalyser = lexer;
    errorReporter = reporter;
    previousTokenPosition = new SourcePosition();
  }

// accept checks whether the current token matches tokenExpected.
// If so, fetches the next token.
// If not, reports a syntactic error.

  void accept (int tokenExpected) throws SyntaxError {
    if (currentToken.kind == tokenExpected) {
      previousTokenPosition = currentToken.position;
      currentToken = lexicalAnalyser.scan();
    } else {
      syntacticError("\"%\" expected here", Token.spell(tokenExpected));
    }
  }

  void acceptIt() {
    previousTokenPosition = currentToken.position;
    currentToken = lexicalAnalyser.scan();
  }

// start records the position of the start of a phrase.
// This is defined to be the position of the first
// character of the first token of the phrase.

  void start(SourcePosition position) {
    position.start = currentToken.position.start;
  }

// finish records the position of the end of a phrase.
// This is defined to be the position of the last
// character of the last token of the phrase.

  void finish(SourcePosition position) {
    position.finish = previousTokenPosition.finish;
  }

  void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
    SourcePosition pos = currentToken.position;
    errorReporter.reportError(messageTemplate, tokenQuoted, pos);
    throw(new SyntaxError());
  }

///////////////////////////////////////////////////////////////////////////////
//
// PROGRAMS
//
///////////////////////////////////////////////////////////////////////////////

  public Program parseProgram() {

    Program programAST = null;

    previousTokenPosition.start = 0;
    previousTokenPosition.finish = 0;
    currentToken = lexicalAnalyser.scan();

    try {
      Command cAST = parseCommand();
      programAST = new Program(cAST, previousTokenPosition);
      if (currentToken.kind != Token.EOT) {
        syntacticError("\"%\" not expected after end of program",
          currentToken.spelling);
      }
    }
    catch (SyntaxError s) { return null; }
    return programAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// LITERALS
//
///////////////////////////////////////////////////////////////////////////////

// parseIntegerLiteral parses an integer-literal, and constructs
// a leaf AST to represent it.

  IntegerLiteral parseIntegerLiteral() throws SyntaxError {
    IntegerLiteral IL = null;

    if (currentToken.kind == Token.INTLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      IL = new IntegerLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      IL = null;
      syntacticError("integer literal expected here", "");
    }
    return IL;
  }

// parseCharacterLiteral parses a character-literal, and constructs a leaf
// AST to represent it.

  CharacterLiteral parseCharacterLiteral() throws SyntaxError {
    CharacterLiteral CL = null;

    if (currentToken.kind == Token.CHARLITERAL) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      CL = new CharacterLiteral(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      CL = null;
      syntacticError("character literal expected here", "");
    }
    return CL;
  }

// parseIdentifier parses an identifier, and constructs a leaf AST to
// represent it.

  Identifier parseIdentifier() throws SyntaxError {
    Identifier I = null;

    if (currentToken.kind == Token.IDENTIFIER) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      I = new Identifier(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      I = null;
      syntacticError("identifier expected here", "");
    }
    return I;
  }

// parseOperator parses an operator, and constructs a leaf AST to
// represent it.

  Operator parseOperator() throws SyntaxError {
    Operator O = null;

    if (currentToken.kind == Token.OPERATOR) {
      previousTokenPosition = currentToken.position;
      String spelling = currentToken.spelling;
      O = new Operator(spelling, previousTokenPosition);
      currentToken = lexicalAnalyser.scan();
    } else {
      O = null;
      syntacticError("operator expected here", "");
    }
    return O;
  }

///////////////////////////////////////////////////////////////////////////////
//
// COMMANDS
//
///////////////////////////////////////////////////////////////////////////////

// parseCommand parses the command, and constructs an AST
// to represent its phrase structure.

  Command parseCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();

    start(commandPos);
    commandAST = parseSingleCommand();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Command c2AST = parseSingleCommand();
      finish(commandPos);
      commandAST = new SequentialCommand(commandAST, c2AST, commandPos);
    }
    return commandAST;
  }

  // Se agregan nuevos comandos
  Command parseSingleCommand() throws SyntaxError {
    Command commandAST = null; // in case there's a syntactic error

    SourcePosition commandPos = new SourcePosition();
    start(commandPos);

    switch (currentToken.kind) {
    
    // Este cubre el V-name ":=" Expression porque este es tambi�n un identifier
    case Token.IDENTIFIER:  
      {
        Identifier iAST = parseIdentifier();
        if (currentToken.kind == Token.LPAREN) {
          acceptIt();
          ActualParameterSequence apsAST = parseActualParameterSequence();
          accept(Token.RPAREN);
          finish(commandPos);
          commandAST = new CallCommand(iAST, apsAST, commandPos);

        } else {

          Vname vAST = parseRestOfVname(iAST);
          accept(Token.BECOMES);
          Expression eAST = parseExpression();
          finish(commandPos);
          commandAST = new AssignCommand(vAST, eAST, commandPos);
        }
      }
      break;
      
    //Agregando el caso del SKIP (Reemplazo del epsil�n)
      case Token.SKIP: {
        acceptIt();
        finish(commandPos);
        commandAST = new EmptyCommand(commandPos);
      }
      break;
    
    // Se elimina este caso 
    /* 
    case Token.BEGIN:
      acceptIt();
      commandAST = parseCommand();
      accept(Token.END);
      break;
    */
      
    case Token.LET:  //Cambiar caso del let por --> "let" Declaration "in" Command "end"
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Command cAST = parseCommand(); //Editado
        accept(Token.END);
        finish(commandPos);
        commandAST = new LetCommand(dAST, cAST, commandPos);
      }
      break;
     
    // Cambiar caso del if por --> "if" Expression "then" Command ("|" Expression "then" Command)*
    //"else" Command "end"
    case Token.IF:   
      {
        acceptIt();
        Expression e1AST = parseExpression();
        accept(Token.THEN);
        Command c1AST = parseCommand();
        Command c2AST = parseElifOrElseOfIf(); // Este m�todo me da el arbol de los elifs o del else
        finish(commandPos);
        commandAST = new IfCommand(e1AST, c1AST, c2AST, commandPos);
      }
      break;
      
    case Token.SELECT: 
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.FROM);
        Command casesAST = parseCases();
        accept(Token.END);
        finish(commandPos);
        commandAST = new SelectCommand(eAST, casesAST, commandPos);
      }
      break;
      
    
    /*
    Ya no forma parte de la gram�tica  
    case Token.WHILE:
      {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.DO);
        Command cAST = parseSingleCommand();
        finish(commandPos);
        commandAST = new WhileCommand(eAST, cAST, commandPos);
      }
      break;
    
    El select es opcional
    case Token.SELECT:  // "select" Expression "from" Cases "end"
        
    {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.FROM);
        // aqui se parsea Cases
        accept(Token.END);
        finish(commandPos);
        // commandAST = new select(); NOT IMPLEMENTED
    }
    */
    
      
    // Aqu� toda la factorizaci�n de REPEAT
    case Token.REPEAT: {
        acceptIt();

        switch (currentToken.kind) {
          case Token.WHILE: { // Camino del while,  "repeat" "while" Expression "do" Command "end"
            acceptIt();
            Expression eAST = parseExpression();
            accept(Token.DO);
            Command cAST = parseCommand();
            accept(Token.END);
            finish(commandPos);
            commandAST = new RepeatWhileCommand(eAST, cAST, commandPos); 
            break;
          }
            
          case Token.UNTIL: { // Camino del until, "repeat" "until" Expression "do" Command "end"
            acceptIt();
            Expression eAST = parseExpression();
            accept(Token.DO);
            Command cAST = parseCommand();
            accept(Token.END);
            finish(commandPos);
            commandAST = new RepeatUntilCommand(eAST, cAST, commandPos); 
            break;
          }
          case Token.DO: { // Camino del do, "repeat" "do" Command ("while"|"until") Expression "end"
            acceptIt();
            Command cAST = parseCommand();
            switch(currentToken.kind){
                case Token.WHILE: {
                    acceptIt();
                    Expression eAST = parseExpression();
                    accept(Token.END);
                    finish(commandPos);
                    commandAST = new RepeatDoWhileCommand(cAST, eAST, commandPos);
                    break;
                }
                case Token.UNTIL: {
                    acceptIt();
                    Expression eAST = parseExpression();
                    accept(Token.END);
                    finish(commandPos);
                    commandAST = new RepeatDoUntilCommand(cAST, eAST, commandPos);
                    break;
                }
            }
            break;
          }

          case Token.FOR: {  // Camino del "repeat" �for� Identifier ( (�:=� �range� Expression �..� Expression (�do�  |�while�|�until�) ) | (�in�) Expression �do� ) ) Command  �end�
              acceptIt();
              Identifier iAST = parseIdentifier();

              switch(currentToken.kind){
                  case Token.BECOMES: {  // camino del (�for� Identifier ( (�:=� �range� Expression �..� Expression
                                         // (�do�  |�while�|�until�) ) | (�in�) Expression �do� ) )
                      
                      acceptIt();
                      accept(Token.RANGE);
                      Expression eAST = parseExpression();
                      accept(Token.DOUBLEDOT);
                      RangeVarDecl rvdAst = new RangeVarDecl(iAST,eAST,commandPos);
                      Expression e2AST = parseExpression();
                      
                      switch(currentToken.kind){
                          case Token.DO: {
                              acceptIt();
                              Command cAST = parseCommand();
                              accept(Token.END);
                              finish(commandPos);
                              commandAST = new RepeatForRange(rvdAst, e2AST, cAST, commandPos);
                              break;
                          }
                          case Token.WHILE: {
                              acceptIt();
                              Expression e3AST = parseExpression();
                              accept(Token.DO);
                              Command cAST = parseCommand();
                              accept(Token.END);
                              finish(commandPos);
                              commandAST = new RepeatForRangeWhile(rvdAst, e2AST, cAST, e3AST, commandPos);
                              break;
                          }
                          case Token.UNTIL: {
                              acceptIt();
                              Expression e3AST = parseExpression();
                              accept(Token.DO);
                              Command cAST = parseCommand();
                              accept(Token.END);
                              finish(commandPos);
                              commandAST = new RepeatForRangeUntil(rvdAst, e2AST, cAST, e3AST, commandPos);
                              break;
                          }
                      }
                      break;
                  }
                  case Token.IN: {
                      acceptIt();
                      Expression eAST = parseExpression();
                      InVarDecl ivdAst = new InVarDecl(iAST,eAST,commandPos);
                      accept(Token.DO);
                      Command cAST = parseCommand();
                      accept(Token.END);
                      finish(commandPos);
                      commandAST = new RepeatIn(ivdAst,cAST,commandPos);
                      break;
                  }
                  default:
                    syntacticError("\"%\" cannot start a command", currentToken.spelling);
                    break;
              }
              break;
          }
          default:
             syntacticError("\"%\" cannot start a command", currentToken.spelling);
             break;
        }
      break;
    }
    

    case Token.SEMICOLON:
    case Token.END:
    case Token.ELSE:
    case Token.IN:
    case Token.EOT:

//      finish(commandPos);
//      commandAST = new EmptyCommand(commandPos);
//      break;

    default:
      syntacticError("\"%\" cannot start a command", currentToken.spelling);
      break;
      
    }

    return commandAST;
  }

  /**
   * M�todo para la segunda parte de un if, que puede | o else
   * Dada por la gram�tica: ("|" Expression "then" Command)* "else" Command "end"
   * @return commandAST
   * @throws SyntaxError 
   */
  
  Command parseElifOrElseOfIf() throws SyntaxError {  
      
    Command commandAST = null; 
    SourcePosition commandPos = new SourcePosition();
    start(commandPos);
    
    switch (currentToken.kind) {
      case Token.ELSE: {
        acceptIt();
        Command cAST = parseCommand();
        accept(Token.END);
        commandAST = cAST;
      }
        break;
      case Token.UPRSLASH: {  //Elifs("|")
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.THEN);
        Command cAST = parseCommand();
        Command c2AST = parseElifOrElseOfIf();
        finish(commandPos);
        commandAST = new IfCommand(eAST, cAST, c2AST, commandPos); 
      }
        break;

    default:
        syntacticError("\"%\" cannot start a command", currentToken.spelling);
        break;
    }
    return commandAST;
  }
  
  
      Cases parseCases() throws SyntaxError {
        Case caseAST = null;
        ElseCase elseCaseAST = null;
        SourcePosition casePos = new SourcePosition();
        start(casePos);
        caseAST = parseCase();
        finish(casePos);
        while (currentToken.kind == Token.WHEN) {
            Case case2AST = parseCase();
            finish(casePos);
            caseAST = new SequentialCase(caseAST, case2AST, casePos);
        }

        if (currentToken.kind == Token.ELSE) {
            elseCaseAST = parseElseCase();
            finish(casePos);
        }
        Cases casesAST = new Cases(caseAST, elseCaseAST, casePos);
        return casesAST;
    }
    
      Case parseCase() throws SyntaxError {
        Case caseAST = null;
        SourcePosition casePos = new SourcePosition();
        start(casePos);
        accept(Token.WHEN);
        CaseLiterals caseLitAST = parseCaseLiterals();
        accept(Token.THEN);
        Command cmdAST = parseCommand();
        finish(casePos);
        caseAST = new CaseWhen(caseLitAST, cmdAST, casePos);
        return caseAST;
    }
    
      ElseCase parseElseCase() throws SyntaxError {
        ElseCase elseCaseAST = null;
        SourcePosition elseCasePos = new SourcePosition();
        start(elseCasePos);
        accept(Token.ELSE);
        Command cmdAST = parseCommand();
        finish(elseCasePos);
        elseCaseAST = new ElseCase(cmdAST, elseCasePos);
        return elseCaseAST;
    }
      
      CaseLiterals parseCaseLiterals() throws SyntaxError {
        CaseLiterals caseLiteralsAST = null;
        SourcePosition caseLiteralsPos = new SourcePosition();
        start(caseLiteralsPos);
        CaseRange caseRangeAST = parseCaseRange();
        finish(caseLiteralsPos);
        while (currentToken.kind == Token.UPRSLASH) {
            acceptIt();
            CaseRange caseRange2AST = parseCaseRange();
            finish(caseLiteralsPos);
            caseRangeAST = new SequentialCaseRange(caseRangeAST, caseRange2AST, caseLiteralsPos);
        }

        caseLiteralsAST = new CaseLiterals(caseRangeAST, caseLiteralsPos);
        return caseLiteralsAST;
    }
      
      CaseRange parseCaseRange() throws SyntaxError {
        CaseLiteral caseLiteral2AST = null;

        SourcePosition caseRangePos = new SourcePosition();
        start(caseRangePos);
        CaseLiteral caseLiteralAST = parseCaseLiteral();

        if (currentToken.kind == Token.DOUBLEDOT) {
            acceptIt();
            caseLiteral2AST = parseCaseLiteral();
        }
        finish(caseRangePos);
        CaseRange caseRangeAST = new CaseRangeCase(caseLiteralAST, caseLiteral2AST, caseRangePos);
        return caseRangeAST;
    }
      
    CaseLiteral parseCaseLiteral() throws SyntaxError {
        CaseLiteral caseLiteralAST = null;
        SourcePosition caseLiteralPos = new SourcePosition();
        start(caseLiteralPos);

        switch (currentToken.kind) {

            case Token.INTLITERAL: {
                IntegerLiteral ilAST = parseIntegerLiteral();
                finish(caseLiteralPos);
                caseLiteralAST = new CaseLiteralINT(ilAST, caseLiteralPos);
            }
            break;
            case Token.CHARLITERAL: {
                CharacterLiteral clAST = parseCharacterLiteral();
                finish(caseLiteralPos);
                caseLiteralAST = new CaseLiteralCHAR(clAST, caseLiteralPos);
            }
            break;
            default:
                syntacticError("\"%\" cannot start a case-literal",
                        currentToken.spelling);
                break;
        }
        finish(caseLiteralPos);
        return caseLiteralAST;
    }
      
  
///////////////////////////////////////////////////////////////////////////////
//
// EXPRESSIONS
//
///////////////////////////////////////////////////////////////////////////////

  Expression parseExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();

    start (expressionPos);

    switch (currentToken.kind) {

    case Token.LET:
      {
        acceptIt();
        Declaration dAST = parseDeclaration();
        accept(Token.IN);
        Expression eAST = parseExpression();
        finish(expressionPos);
        expressionAST = new LetExpression(dAST, eAST, expressionPos);
      }
      break;

    case Token.IF:
      {
        acceptIt();
        Expression e1AST = parseExpression();
        accept(Token.THEN);
        Expression e2AST = parseExpression();
        accept(Token.ELSE);
        Expression e3AST = parseExpression();
        finish(expressionPos);
        expressionAST = new IfExpression(e1AST, e2AST, e3AST, expressionPos);
      }
      break;
     
    default:
      expressionAST = parseSecondaryExpression();
      break;
    }
    return expressionAST;
  }

  Expression parseSecondaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    expressionAST = parsePrimaryExpression();
    while (currentToken.kind == Token.OPERATOR) {
      Operator opAST = parseOperator();
      Expression e2AST = parsePrimaryExpression();
      expressionAST = new BinaryExpression (expressionAST, opAST, e2AST,
        expressionPos);
    }
    return expressionAST;
  }

  Expression parsePrimaryExpression() throws SyntaxError {
    Expression expressionAST = null; // in case there's a syntactic error

    SourcePosition expressionPos = new SourcePosition();
    start(expressionPos);

    switch (currentToken.kind) {

    case Token.INTLITERAL:
      {
        IntegerLiteral ilAST = parseIntegerLiteral();
        finish(expressionPos);
        expressionAST = new IntegerExpression(ilAST, expressionPos);
      }
      break;

    case Token.CHARLITERAL:
      {
        CharacterLiteral clAST= parseCharacterLiteral();
        finish(expressionPos);
        expressionAST = new CharacterExpression(clAST, expressionPos);
      }
      break;

    case Token.LBRACKET:
      {
        acceptIt();
        ArrayAggregate aaAST = parseArrayAggregate();
        accept(Token.RBRACKET);
        finish(expressionPos);
        expressionAST = new ArrayExpression(aaAST, expressionPos);
      }
      break;

    case Token.LCURLY:
      {
        acceptIt();
        RecordAggregate raAST = parseRecordAggregate();
        accept(Token.RCURLY);
        finish(expressionPos);
        expressionAST = new RecordExpression(raAST, expressionPos);
      }
      break;

    case Token.IDENTIFIER:
      {
        Identifier iAST= parseIdentifier();
        if (currentToken.kind == Token.LPAREN) {
          acceptIt();
          ActualParameterSequence apsAST = parseActualParameterSequence();
          accept(Token.RPAREN);
          finish(expressionPos);
          expressionAST = new CallExpression(iAST, apsAST, expressionPos);

        } else {
          Vname vAST = parseRestOfVname(iAST);
          finish(expressionPos);
          expressionAST = new VnameExpression(vAST, expressionPos);
        }
      }
      break;

    case Token.OPERATOR:
      {
        Operator opAST = parseOperator();
        Expression eAST = parsePrimaryExpression();
        finish(expressionPos);
        expressionAST = new UnaryExpression(opAST, eAST, expressionPos);
      }
      break;

    case Token.LPAREN:
      acceptIt();
      expressionAST = parseExpression();
      accept(Token.RPAREN);
      break;

    default:
      syntacticError("\"%\" cannot start an expression",
        currentToken.spelling);
      break;

    }
    return expressionAST;
  }

  RecordAggregate parseRecordAggregate() throws SyntaxError {
    RecordAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Identifier iAST = parseIdentifier();
    accept(Token.IS);
    Expression eAST = parseExpression();

    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      RecordAggregate aAST = parseRecordAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleRecordAggregate(iAST, eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleRecordAggregate(iAST, eAST, aggregatePos);
    }
    return aggregateAST;
  }

  ArrayAggregate parseArrayAggregate() throws SyntaxError {
    ArrayAggregate aggregateAST = null; // in case there's a syntactic error

    SourcePosition aggregatePos = new SourcePosition();
    start(aggregatePos);

    Expression eAST = parseExpression();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ArrayAggregate aAST = parseArrayAggregate();
      finish(aggregatePos);
      aggregateAST = new MultipleArrayAggregate(eAST, aAST, aggregatePos);
    } else {
      finish(aggregatePos);
      aggregateAST = new SingleArrayAggregate(eAST, aggregatePos);
    }
    return aggregateAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// VALUE-OR-VARIABLE NAMES
//
///////////////////////////////////////////////////////////////////////////////

  Vname parseVname () throws SyntaxError {
    Vname vnameAST = null; // in case there's a syntactic error
    Identifier iAST = parseIdentifier();
    vnameAST = parseRestOfVname(iAST);
    return vnameAST;
  }

  Vname parseRestOfVname(Identifier identifierAST) throws SyntaxError {
    SourcePosition vnamePos = new SourcePosition();
    vnamePos = identifierAST.position;
    Vname vAST = new SimpleVname(identifierAST, vnamePos);

    while (currentToken.kind == Token.DOT ||
           currentToken.kind == Token.LBRACKET) {

      if (currentToken.kind == Token.DOT) {
        acceptIt();
        Identifier iAST = parseIdentifier();
        vAST = new DotVname(vAST, iAST, vnamePos);
      } else {
        acceptIt();
        Expression eAST = parseExpression();
        accept(Token.RBRACKET);
        finish(vnamePos);
        vAST = new SubscriptVname(vAST, eAST, vnamePos);
      }
    }
    return vAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// DECLARATIONS
//
///////////////////////////////////////////////////////////////////////////////

   //Gramatica modificada 
  Declaration parseDeclaration() throws SyntaxError {
    

    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    declarationAST = parseCompoundDeclaration();
    while (currentToken.kind == Token.SEMICOLON) {
      acceptIt();
      Declaration d2AST = parseCompoundDeclaration();
      finish(declarationPos);
      declarationAST = new SequentialDeclaration(declarationAST, d2AST,declarationPos);
    }
    return declarationAST;
  }

   //Gramatica modificada 
  Declaration parseSingleDeclaration() throws SyntaxError {
    Declaration declarationAST = null; // in case there's a syntactic error

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);

    switch (currentToken.kind) {

    case Token.CONST:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new ConstDeclaration(iAST, eAST, declarationPos);
      }
      break;
     
    //Gramatica cambiada porque ahora hay 2 tipos de var
    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        if (currentToken.kind == Token.COLON) {     
            acceptIt();
            TypeDenoter tAST = parseTypeDenoter();
            finish(declarationPos);
            declarationAST = new VarDeclaration(iAST, tAST, declarationPos);

        }
       //Ser� :=
        else{
            acceptIt();
            Expression eAST = parseExpression();
            finish(declarationPos);
            declarationAST = new VarDeclarationExpression(iAST, eAST, declarationPos);
        }
        
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.IS); //~
        Command cAST = parseCommand();
        accept(Token.END);
        finish(declarationPos);
        declarationAST = new ProcDeclaration(iAST, fAST, cAST, declarationPos); 
        
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        accept(Token.IS);
        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new FuncDeclaration(iAST, fpsAST, tAST, eAST,
          declarationPos);
      }
      break;

    case Token.TYPE:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        TypeDenoter tAST = parseTypeDenoter();
        finish(declarationPos);
        declarationAST = new TypeDeclaration(iAST, tAST, declarationPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a declaration",
        currentToken.spelling);
      break;

    }
    return declarationAST;
  }

  
   // Regla a�adida 
  Declaration parseCompoundDeclaration() throws SyntaxError {
    //compound-Declaration ::= single-Declaration | "recursive" Proc-Funcs "end" | "local" Declaration "in" Declaration "end"
    Declaration declarationAST = null; 

    SourcePosition declarationPos = new SourcePosition();
    start(declarationPos);
    
    //ors aqu� 
    switch (currentToken.kind) {

    case Token.RECURSIVE:
      {
        acceptIt();
        ProcFuncs pfAST = parseProcFuncs();
        accept(Token.END);
        finish(declarationPos);
        declarationAST = new RecursiveDeclaration(pfAST, declarationPos);;
      }
      break;

    case Token.LOCAL:
      {
        acceptIt();
        Declaration decAST = parseDeclaration();
        accept(Token.IN);
        Declaration dec2AST = parseDeclaration();
        accept(Token.END);
        finish(declarationPos);
        declarationAST = new LocalProcFuncDeclaration(decAST, dec2AST, declarationPos);
      }
      break;
    
    default:
      declarationAST = parseSingleDeclaration();
      break;
  }
    
      return declarationAST;
  }
    
  
    // Nueva implementacion creada para el metodo parseProcFuncs
    ProcFuncs parseProcFunc() throws SyntaxError {
    ProcFuncs procFuncsAST = null; // En caso de error Sintactico

    SourcePosition procFuncPos = new SourcePosition();
    start(procFuncPos);
    switch(currentToken.kind){
      case Token.PROC:
        {
          acceptIt();
          Identifier iAST = parseIdentifier();
          accept(Token.LPAREN);
          FormalParameterSequence fpsAST = parseFormalParameterSequence();
          accept(Token.RPAREN);
          accept(Token.IS);
          Command cmdAST = parseCommand();
          accept(Token.END);
          finish(procFuncPos);
          procFuncsAST = new RecursiveProc(iAST, fpsAST, cmdAST, procFuncPos);
        }
        break;
      case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        accept(Token.IS);
        Expression eAST = parseExpression();
        procFuncsAST = new RecursiveFunc(iAST, fpsAST, tAST, eAST, procFuncPos);
      }
      break;
      default:
      syntacticError("\"%\" cannot start a type denoter",
                    currentToken.spelling);
    }
    return procFuncsAST;
  }
  

  // Nueva implementacion de parseProcFuncs
  ProcFuncs parseProcFuncs() throws SyntaxError{
    ProcFuncs ProcFuncsAST = null; // En caso de error sintactico

    SourcePosition propFuncsPos = new SourcePosition();
    start(propFuncsPos);
    ProcFuncs secondProcFuncsAST = parseProcFunc();
    do {
      accept(Token.UPRSLASH);
      ProcFuncs thirdProcFuncsAST = parseProcFunc();
      secondProcFuncsAST = new SequentialProcFuncs(secondProcFuncsAST, thirdProcFuncsAST, propFuncsPos);
    } while (currentToken.kind == Token.UPRSLASH); //"|"
    finish(propFuncsPos);
    ProcFuncsAST = secondProcFuncsAST;
    return ProcFuncsAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// PARAMETERS
//
///////////////////////////////////////////////////////////////////////////////

  FormalParameterSequence parseFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST;

    SourcePosition formalsPos = new SourcePosition();

    start(formalsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(formalsPos);
      formalsAST = new EmptyFormalParameterSequence(formalsPos);

    } else {
      formalsAST = parseProperFormalParameterSequence();
    }
    return formalsAST;
  }

  FormalParameterSequence parseProperFormalParameterSequence() throws SyntaxError {
    FormalParameterSequence formalsAST = null; // in case there's a syntactic error;

    SourcePosition formalsPos = new SourcePosition();
    start(formalsPos);
    FormalParameter fpAST = parseFormalParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FormalParameterSequence fpsAST = parseProperFormalParameterSequence();
      finish(formalsPos);
      formalsAST = new MultipleFormalParameterSequence(fpAST, fpsAST,
        formalsPos);

    } else {
      finish(formalsPos);
      formalsAST = new SingleFormalParameterSequence(fpAST, formalsPos);
    }
    return formalsAST;
  }

  FormalParameter parseFormalParameter() throws SyntaxError {
    FormalParameter formalAST = null; // in case there's a syntactic error;

    SourcePosition formalPos = new SourcePosition();
    start(formalPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new ConstFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new VarFormalParameter(iAST, tAST, formalPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        finish(formalPos);
        formalAST = new ProcFormalParameter(iAST, fpsAST, formalPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        accept(Token.LPAREN);
        FormalParameterSequence fpsAST = parseFormalParameterSequence();
        accept(Token.RPAREN);
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        finish(formalPos);
        formalAST = new FuncFormalParameter(iAST, fpsAST, tAST, formalPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a formal parameter",
        currentToken.spelling);
      break;

    }
    return formalAST;
  }


  ActualParameterSequence parseActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST;

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    if (currentToken.kind == Token.RPAREN) {
      finish(actualsPos);
      actualsAST = new EmptyActualParameterSequence(actualsPos);

    } else {
      actualsAST = parseProperActualParameterSequence();
    }
    return actualsAST;
  }

  ActualParameterSequence parseProperActualParameterSequence() throws SyntaxError {
    ActualParameterSequence actualsAST = null; // in case there's a syntactic error

    SourcePosition actualsPos = new SourcePosition();

    start(actualsPos);
    ActualParameter apAST = parseActualParameter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      ActualParameterSequence apsAST = parseProperActualParameterSequence();
      finish(actualsPos);
      actualsAST = new MultipleActualParameterSequence(apAST, apsAST,
        actualsPos);
    } else {
      finish(actualsPos);
      actualsAST = new SingleActualParameterSequence(apAST, actualsPos);
    }
    return actualsAST;
  }

  ActualParameter parseActualParameter() throws SyntaxError {
    ActualParameter actualAST = null; // in case there's a syntactic error

    SourcePosition actualPos = new SourcePosition();

    start(actualPos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
    case Token.INTLITERAL:
    case Token.CHARLITERAL:
    case Token.OPERATOR:
    case Token.LET:
    case Token.IF:
    case Token.LPAREN:
    case Token.LBRACKET:
    case Token.LCURLY:
      {
        Expression eAST = parseExpression();
        finish(actualPos);
        actualAST = new ConstActualParameter(eAST, actualPos);
      }
      break;

    case Token.VAR:
      {
        acceptIt();
        Vname vAST = parseVname();
        finish(actualPos);
        actualAST = new VarActualParameter(vAST, actualPos);
      }
      break;

    case Token.PROC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new ProcActualParameter(iAST, actualPos);
      }
      break;

    case Token.FUNC:
      {
        acceptIt();
        Identifier iAST = parseIdentifier();
        finish(actualPos);
        actualAST = new FuncActualParameter(iAST, actualPos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start an actual parameter",
        currentToken.spelling);
      break;

    }
    return actualAST;
  }

///////////////////////////////////////////////////////////////////////////////
//
// TYPE-DENOTERS
//
///////////////////////////////////////////////////////////////////////////////

  TypeDenoter parseTypeDenoter() throws SyntaxError {
    TypeDenoter typeAST = null; // in case there's a syntactic error
    SourcePosition typePos = new SourcePosition();

    start(typePos);

    switch (currentToken.kind) {

    case Token.IDENTIFIER:
      {
        Identifier iAST = parseIdentifier();
        finish(typePos);
        typeAST = new SimpleTypeDenoter(iAST, typePos);
      }
      break;

    case Token.ARRAY:
      {
        acceptIt();
        IntegerLiteral ilAST = parseIntegerLiteral();
        accept(Token.OF);
        TypeDenoter tAST = parseTypeDenoter();
        finish(typePos);
        typeAST = new ArrayTypeDenoter(ilAST, tAST, typePos);
      }
      break;

    case Token.RECORD:
      {
        acceptIt();
        FieldTypeDenoter fAST = parseFieldTypeDenoter();
        accept(Token.END);
        finish(typePos);
        typeAST = new RecordTypeDenoter(fAST, typePos);
      }
      break;

    default:
      syntacticError("\"%\" cannot start a type denoter",
        currentToken.spelling);
      break;

    }
    return typeAST;
  }

  FieldTypeDenoter parseFieldTypeDenoter() throws SyntaxError {
    FieldTypeDenoter fieldAST = null; // in case there's a syntactic error

    SourcePosition fieldPos = new SourcePosition();

    start(fieldPos);
    Identifier iAST = parseIdentifier();
    accept(Token.COLON);
    TypeDenoter tAST = parseTypeDenoter();
    if (currentToken.kind == Token.COMMA) {
      acceptIt();
      FieldTypeDenoter fAST = parseFieldTypeDenoter();
      finish(fieldPos);
      fieldAST = new MultipleFieldTypeDenoter(iAST, tAST, fAST, fieldPos);
    } else {
      finish(fieldPos);
      fieldAST = new SingleFieldTypeDenoter(iAST, tAST, fieldPos);
    }
    return fieldAST;
  }
}
