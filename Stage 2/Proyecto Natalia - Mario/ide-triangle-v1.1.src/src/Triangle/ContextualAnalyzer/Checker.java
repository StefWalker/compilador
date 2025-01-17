/*
 * @(#)Checker.java                        2.1 2003/10/07
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

package Triangle.ContextualAnalyzer;

import Triangle.ErrorReporter;
import Triangle.StdEnvironment;
import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CaseLiteralCHAR;
import Triangle.AbstractSyntaxTrees.CaseLiteralINT;
import Triangle.AbstractSyntaxTrees.CaseLiterals;
import Triangle.AbstractSyntaxTrees.CaseRangeCase;
import Triangle.AbstractSyntaxTrees.CaseWhen;
import Triangle.AbstractSyntaxTrees.Cases;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.ElseCase;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
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
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
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
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RangeVarDecl;
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
import Triangle.AbstractSyntaxTrees.Terminal;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarDeclarationExpression;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
import Triangle.SyntacticAnalyzer.SourcePosition;
import Utilities.SelectData;
import Triangle.AbstractSyntaxTrees.RecursiveDeclaration;
import Triangle.AbstractSyntaxTrees.RecursiveFunc;
import Triangle.AbstractSyntaxTrees.RecursiveProc;
import Triangle.AbstractSyntaxTrees.SequentialProcFuncs;


public final class Checker implements Visitor {

  // Commands

  // Always returns null. Does not use the given object.

    

  public Object visitAssignCommand(AssignCommand ast, Object o) {
    //Visitar para sacar los tipos
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    
    //Si no funciona como una variable. no la podemos asignar
    if (!ast.V.variable)
      reporter.reportError ("LHS of assignment is not a variable", "", ast.V.position);
    if (! eType.equals(vType))
      reporter.reportError ("assignment incompatibilty", "", ast.position);
    if (! eType.visit(this,null).equals(vType)) // Dylan Torres, Adaptacion para el Recursive verificando la incompatibilidad de datos
      reporter.reportError ("assignment incompatibilty", "", ast.position);
    return null;  // Si las partes internas están bien
  }


  public Object visitCallCommand(CallCommand ast, Object o) {

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared(ast.I);
    else if (binding instanceof ProcDeclaration) {
      ast.APS.visit(this, ((ProcDeclaration) binding).FPS);
    } else if (binding instanceof RecursiveProc) { // Dylan Torres, Agregado para lograr la implementacion del recursive, para verificar la instancia del recursive
      ast.APS.visit(this, ((RecursiveProc) binding).FPS);
    } else if (binding instanceof ProcFormalParameter) {
      ast.APS.visit(this, ((ProcFormalParameter) binding).FPS);
    } else
      reporter.reportError("\"%\" is not a procedure identifier",
                           ast.I.spelling, ast.I.position);
    return null;
  }

  public Object visitEmptyCommand(EmptyCommand ast, Object o) {
    return null;
  }

  public Object visitIfCommand(IfCommand ast, Object o) {
    //Con esto no hya que hacer b=nada pare el restofif, nuestro caso elseorEndOfIf
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (! eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }

  public Object visitLetCommand(LetCommand ast, Object o) {
    // Acá no se hace nada si lo hicimos bien en el contextual
    idTable.openScope();
    ast.D.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }

  public Object visitSequentialCommand(SequentialCommand ast, Object o) {
     // Acá no se hace nada si lo hicimos bien en el contextual
    ast.C1.visit(this, null);
    ast.C2.visit(this, null);
    return null;
  }

  public Object visitWhileCommand(WhileCommand ast, Object o) {
    //Repeat while do
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (! eType.equals(StdEnvironment.booleanType))
      reporter.reportError("Boolean expression expected here", "", ast.E.position);
    ast.C.visit(this, null);
    return null; //Todo bien
  }

  //Acá se agregan el resto de cosas del 
  
  
  
  
  // Expressions

  // Returns the TypeDenoter denoting the type of the expression. Does
  // not use the given object.

  public Object visitArrayExpression(ArrayExpression ast, Object o) {
    TypeDenoter elemType = (TypeDenoter) ast.AA.visit(this, null);
    
    if(!(elemType instanceof IntTypeDenoter))   
      reporter.reportError("Expected Integer type here", "", ast.position);  // Revision de cada componente del arreglo
    IntegerLiteral il = new IntegerLiteral(new Integer(ast.AA.elemCount).toString(),ast.position);  
    
    ast.type = new ArrayTypeDenoter(il, elemType, ast.position);
    StdEnvironment.arrayType = new ArrayTypeDenoter(il, elemType, ast.position);
    return ast.type;
  }

  public Object visitBinaryExpression(BinaryExpression ast, Object o) {

    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);

    if (binding == null)
      reportUndeclared(ast.O);
    else {
      if (! (binding instanceof BinaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a binary operator",
                              ast.O.spelling, ast.O.position);
      BinaryOperatorDeclaration bbinding = (BinaryOperatorDeclaration) binding;
      if (bbinding.ARG1 == StdEnvironment.anyType) {
        // this operator must be "=" or "\="
        if (! e1Type.equals(e2Type))
          reporter.reportError ("incompatible argument types for \"%\"",
                                ast.O.spelling, ast.position);
      } else if (! e1Type.visit(this,null).equals(bbinding.ARG1))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E1.position);
      else if (! e2Type.visit(this,null).equals(bbinding.ARG2))
          reporter.reportError ("wrong argument type for \"%\"",
                                ast.O.spelling, ast.E2.position);
      ast.type = bbinding.RES;
    }
    return ast.type;
  }

  public Object visitCallExpression(CallExpression ast, Object o) {
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null) {
      reportUndeclared(ast.I);
      ast.type = StdEnvironment.errorType;
    } else if (binding instanceof FuncDeclaration) {
      ast.APS.visit(this, ((FuncDeclaration) binding).FPS);
      ast.type = ((FuncDeclaration) binding).T;
    } else if (binding instanceof FuncFormalParameter) {
      ast.APS.visit(this, ((FuncFormalParameter) binding).FPS);
      ast.type = ((FuncFormalParameter) binding).T;
    }else if(binding instanceof  RecursiveFunc){ // Dylan Torres, Adicion del RecursiveFunc
      ast.APS.visit(this,((RecursiveFunc) binding).FPS);
      ast.type = ((RecursiveFunc) binding).T;
    }  
    else
      reporter.reportError("\"%\" is not a function identifier",
                           ast.I.spelling, ast.I.position);
    return ast.type;
  }

  public Object visitCharacterExpression(CharacterExpression ast, Object o) {
    ast.type = StdEnvironment.charType;
    return ast.type;
  }

  public Object visitEmptyExpression(EmptyExpression ast, Object o) {
    ast.type = null;
    return ast.type;
  }

  public Object visitIfExpression(IfExpression ast, Object o) {
    // IF E1 THEN E2 ELSE E3
    // Donde E1 es booleano y E2 Y E3 son del mismo tipo 
    // Algunos cambios
    TypeDenoter e1Type = (TypeDenoter) ast.E1.visit(this, null);
    if (! e1Type.equals(StdEnvironment.booleanType))
      reporter.reportError ("Boolean expression expected here", "",
                            ast.E1.position);
    TypeDenoter e2Type = (TypeDenoter) ast.E2.visit(this, null);
    TypeDenoter e3Type = (TypeDenoter) ast.E3.visit(this, null);
    
    TypeDenoter e4Type = (TypeDenoter) e2Type.visit(this, null);
    TypeDenoter e5Type = (TypeDenoter) e3Type.visit(this, null);
    if (! e4Type.equals(e5Type))
      reporter.reportError ("incompatible limbs in if-expression", "", ast.position);
    ast.type = e4Type;
    
//    if (! e2Type.equals(e3Type))
//      reporter.reportError ("incompatible limbs in if-expression", "", ast.position);
//    ast.type = e2Type;
    return ast.type;
  }

  public Object visitIntegerExpression(IntegerExpression ast, Object o) {
    ast.type = StdEnvironment.integerType;
    return ast.type;
  }

  public Object visitLetExpression(LetExpression ast, Object o) {
    idTable.openScope();
    ast.D.visit(this, null);
    ast.type = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    return ast.type;
  }

  public Object visitRecordExpression(RecordExpression ast, Object o) {
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    ast.type = new RecordTypeDenoter(rType, ast.position);
    return ast.type;
  }

  public Object visitUnaryExpression(UnaryExpression ast, Object o) {

    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    Declaration binding = (Declaration) ast.O.visit(this, null);
    if (binding == null) {
      reportUndeclared(ast.O);
      ast.type = StdEnvironment.errorType;
    } else if (! (binding instanceof UnaryOperatorDeclaration))
        reporter.reportError ("\"%\" is not a unary operator",
                              ast.O.spelling, ast.O.position);
    else {
      UnaryOperatorDeclaration ubinding = (UnaryOperatorDeclaration) binding;
      if (! eType.equals(ubinding.ARG))
        reporter.reportError ("wrong argument type for \"%\"",
                              ast.O.spelling, ast.O.position);
      ast.type = ubinding.RES;
    }
    return ast.type;
  }

  public Object visitVnameExpression(VnameExpression ast, Object o) {
    ast.type = (TypeDenoter) ast.V.visit(this, null);
    return ast.type;
  }

  // Declarations

  // Always returns null. Does not use the given object.
  public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
    return null;
  }

  public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);  
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    idTable.closeScope();
    if (! ast.T.equals(eType))
      reporter.reportError ("body of function \"%\" has wrong type",
                            ast.I.spelling, ast.E.position);
    return null;
  }
  
    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Johel Mora Calderon
    //  Declaration 
    //
    ///////////////////////////////////////////////////////////////////////////////
  @Override
  public Object visitRecursiveFunc(RecursiveFunc ast, Object o) {
    idTable.enter(ast.I.spelling, ast);
    if(ast.duplicated){
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    }
    return null;
  }

  public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
    //Pasa el nombre y el propio arbol de proc, lo que permite la recursion
    idTable.enter (ast.I.spelling, ast); // permits recursion
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    idTable.openScope();
    ast.FPS.visit(this, null);
    ast.C.visit(this, null);
    idTable.closeScope();
    return null;
  }
  
  @Override
  public Object visitRecursiveProc(RecursiveProc ast, Object o) {
    idTable.enter(ast.I.spelling, ast); //permits recursion
    if(ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
    ast.D1.visit(this, null);
    ast.D2.visit(this, null);
    return null;
  }

  public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
    return null;
  }

  public Object visitVarDeclaration(VarDeclaration ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("identifier \"%\" already declared",
                            ast.I.spelling, ast.position);

    return null;
  }

  // Array Aggregates

  // Returns the TypeDenoter for the Array Aggregate. Does not use the
  // given object.

  public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    TypeDenoter elemType = (TypeDenoter) ast.AA.visit(this, null);
    ast.elemCount = ast.AA.elemCount + 1;
    if (! eType.equals(elemType))
      reporter.reportError ("incompatible array-aggregate element", "", ast.E.position);
    return elemType;
  }

  public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
    TypeDenoter elemType = (TypeDenoter) ast.E.visit(this, null);
    ast.elemCount = 1;
    return elemType;
  }

  // Record Aggregates

  // Returns the TypeDenoter for the Record Aggregate. Does not use the
  // given object.

  public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    FieldTypeDenoter rType = (FieldTypeDenoter) ast.RA.visit(this, null);
    TypeDenoter fType = checkFieldIdentifier(rType, ast.I);
    if (fType != StdEnvironment.errorType)
      reporter.reportError ("duplicate field \"%\" in record",
                            ast.I.spelling, ast.I.position);
    ast.type = new MultipleFieldTypeDenoter(ast.I, eType, rType, ast.position);
    return ast.type;
  }

  public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    ast.type = new SingleFieldTypeDenoter(ast.I, eType, ast.position);
    return ast.type;
  }

  // Formal Parameters

  // Always returns null. Does not use the given object.

  public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter(ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
    idTable.openScope();
    ast.FPS.visit(this, null);
    idTable.closeScope();
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    idTable.enter (ast.I.spelling, ast);
    if (ast.duplicated)
      reporter.reportError ("duplicated formal parameter \"%\"",
                            ast.I.spelling, ast.position);
    return null;
  }

  public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
    return null;
  }

  public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    ast.FPS.visit(this, null);
    return null;
  }

  public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
    ast.FP.visit(this, null);
    return null;
  }

  // Actual Parameters

  // Always returns null. Uses the given FormalParameter.

  public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);

    if (! (fp instanceof ConstFormalParameter))
      reporter.reportError ("const actual parameter not expected here", "",
                            ast.position);
    else if (! eType.visit(this, null).equals(((ConstFormalParameter) fp).T.visit(this,null)))
      reporter.reportError ("wrong type for const actual parameter", "",
                            ast.E.position);
    return null;
  }

  public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared (ast.I);
    else if (! (binding instanceof FuncDeclaration ||
                binding instanceof FuncFormalParameter))
      reporter.reportError ("\"%\" is not a function identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof FuncFormalParameter))
      reporter.reportError ("func actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      TypeDenoter T = null;
      if (binding instanceof FuncDeclaration) {
        FPS = ((FuncDeclaration) binding).FPS;
        T = ((FuncDeclaration) binding).T;
      } else {
        FPS = ((FuncFormalParameter) binding).FPS;
        T = ((FuncFormalParameter) binding).T;
      }
      if (! FPS.equals(((FuncFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for function \"%\"",
                              ast.I.spelling, ast.I.position);
      else if (! T.equals(((FuncFormalParameter) fp).T))
        reporter.reportError ("wrong type for function \"%\"",
                              ast.I.spelling, ast.I.position);
    }
    return null;
  }

  public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null)
      reportUndeclared (ast.I);
    else if (! (binding instanceof ProcDeclaration ||
                binding instanceof ProcFormalParameter || binding instanceof RecursiveProc))
      reporter.reportError ("\"%\" is not a procedure identifier",
                            ast.I.spelling, ast.I.position);
    else if (! (fp instanceof ProcFormalParameter))
      reporter.reportError ("proc actual parameter not expected here", "",
                            ast.position);
    else {
      FormalParameterSequence FPS = null;
      if (binding instanceof ProcDeclaration)
        FPS = ((ProcDeclaration) binding).FPS;
      else
        FPS = ((ProcFormalParameter) binding).FPS;
      if (! FPS.equals(((ProcFormalParameter) fp).FPS))
        reporter.reportError ("wrong signature for procedure \"%\"",
                              ast.I.spelling, ast.I.position);
    }
    return null;
  }

  public Object visitVarActualParameter(VarActualParameter ast, Object o) {
    FormalParameter fp = (FormalParameter) o;

    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    if (! ast.V.variable)
      reporter.reportError ("actual parameter is not a variable", "",
                            ast.V.position);
    else if (! (fp instanceof VarFormalParameter))
      reporter.reportError ("var actual parameter not expected here", "",
                            ast.V.position);
    else if (! vType.equals(((VarFormalParameter) fp).T))
      reporter.reportError ("wrong type for var actual parameter", "",
                            ast.V.position);
    return null;
  }

  public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof EmptyFormalParameterSequence))
      reporter.reportError ("too few actual parameters", "", ast.position);
    return null;
  }

  public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof MultipleFormalParameterSequence))
      reporter.reportError ("too many actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((MultipleFormalParameterSequence) fps).FP);
      ast.APS.visit(this, ((MultipleFormalParameterSequence) fps).FPS);
    }
    return null;
  }

  public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
    FormalParameterSequence fps = (FormalParameterSequence) o;
    if (! (fps instanceof SingleFormalParameterSequence))
      reporter.reportError ("incorrect number of actual parameters", "", ast.position);
    else {
      ast.AP.visit(this, ((SingleFormalParameterSequence) fps).FP);
    }
    return null;
  }

  // Type Denoters

  // Returns the expanded version of the TypeDenoter. Does not
  // use the given object.

  public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
    return StdEnvironment.anyType;
  }

  public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    if ((Integer.valueOf(ast.IL.spelling).intValue()) == 0)
      reporter.reportError ("arrays must not be empty", "", ast.IL.position);
    return ast;
  }

  public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
    return StdEnvironment.booleanType;
  }

  public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
    return StdEnvironment.errorType;
  }

  public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null) {
      reportUndeclared (ast.I);
      return StdEnvironment.errorType;
    } else if (! (binding instanceof TypeDeclaration)) {
      reporter.reportError ("\"%\" is not a type identifier",
                            ast.I.spelling, ast.I.position);
      return StdEnvironment.errorType;
    }
    return ((TypeDeclaration) binding).T;
  }

  public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
    ast.FT = (FieldTypeDenoter) ast.FT.visit(this, null);
    return ast;
  }

  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    ast.FT.visit(this, null);
    return ast;
  }

  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
    ast.T = (TypeDenoter) ast.T.visit(this, null);
    return ast;
  }

  // Literals, Identifiers and Operators
  public Object visitCharacterLiteral(CharacterLiteral CL, Object o) {
    return StdEnvironment.charType;
  }

  public Object visitIdentifier(Identifier I, Object o) {
    Declaration binding = idTable.retrieve(I.spelling);
    if (binding != null)
      I.decl = binding;
    return binding;
  }

  public Object visitIntegerLiteral(IntegerLiteral IL, Object o) {
    return StdEnvironment.integerType;
  }

  public Object visitOperator(Operator O, Object o) {
    Declaration binding = idTable.retrieve(O.spelling);
    if (binding != null)
      O.decl = binding;
    return binding;
  }

  // Value-or-variable names

  // Determines the address of a named object (constant or variable).
  // This consists of a base object, to which 0 or more field-selection
  // or array-indexing operations may be applied (if it is a record or
  // array).  As much as possible of the address computation is done at
  // compile-time. Code is generated only when necessary to evaluate
  // index expressions at run-time.
  // currentLevel is the routine level where the v-name occurs.
  // frameSize is the anticipated size of the local stack frame when
  // the object is addressed at run-time.
  // It returns the description of the base object.
  // offset is set to the total of any field offsets (plus any offsets
  // due to index expressions that happen to be literals).
  // indexed is set to true iff there are any index expressions (other
  // than literals). In that case code is generated to compute the
  // offset due to these indexing operations at run-time.

  // Returns the TypeDenoter of the Vname. Does not use the
  // given object.

  public Object visitDotVname(DotVname ast, Object o) {
    ast.type = null;
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    if (! (vType instanceof RecordTypeDenoter))
      reporter.reportError ("record expected here", "", ast.V.position);
    else {
      ast.type = checkFieldIdentifier(((RecordTypeDenoter) vType).FT, ast.I);
      if (ast.type == StdEnvironment.errorType)
        reporter.reportError ("no field \"%\" in this record type",
                              ast.I.spelling, ast.I.position);
    }
    return ast.type;
  }

  public Object visitSimpleVname(SimpleVname ast, Object o) {
    ast.variable = false;
    ast.type = StdEnvironment.errorType;
    Declaration binding = (Declaration) ast.I.visit(this, null);
    if (binding == null){
      reportUndeclared(ast.I);
    }
    else
      if (binding instanceof ConstDeclaration) {
        ast.type = ((ConstDeclaration) binding).E.type;
        ast.variable = false;
      } else if (binding instanceof VarDeclaration) {
        ast.type = ((VarDeclaration) binding).T;
        ast.variable = true;
      } else if (binding instanceof ConstFormalParameter) {
        ast.type = ((ConstFormalParameter) binding).T;
        ast.variable = false;
      } else if (binding instanceof RangeVarDecl) {
        ast.type = ((RangeVarDecl) binding).E.type;
        ast.variable = true;
      } else if (binding instanceof VarFormalParameter) {
        ast.type = ((VarFormalParameter) binding).T; //Yosua Blanco Diaz 
        ast.variable = true;
      }else if (binding instanceof InVarDecl) {  //Yosua Blanco Diaz 
        ast.type = StdEnvironment.integerType;
        ast.variable = true;
      }else if (binding instanceof VarDeclarationExpression) { 
        ast.type = ((VarDeclarationExpression) binding).E.type;
        ast.variable = true;
      } 
      else
        reporter.reportError ("\"%\" is not a const or var identifier",
                              ast.I.spelling, ast.I.position);
    return ast.type;
  }

  public Object visitSubscriptVname(SubscriptVname ast, Object o) {
    TypeDenoter vType = (TypeDenoter) ast.V.visit(this, null);
    ast.variable = ast.V.variable;
    TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
    if (vType != StdEnvironment.errorType) {
      if (! (vType instanceof ArrayTypeDenoter))
        reporter.reportError ("array expected here", "", ast.V.position);
      else {
        if (! eType.equals(StdEnvironment.integerType))
          reporter.reportError ("Integer expression expected here", "",
				ast.E.position);
        ast.type = ((ArrayTypeDenoter) vType).T;
      }
    }
    return ast.type;
  }

  // Programs

  public Object visitProgram(Program ast, Object o) {
    ast.C.visit(this, null);
    return null;
  }

  // Checks whether the source program, represented by its AST, satisfies the
  // language's scope rules and type rules.
  // Also decorates the AST as follows:
  //  (a) Each applied occurrence of an identifier or operator is linked to
  //      the corresponding declaration of that identifier or operator.
  //  (b) Each expression and value-or-variable-name is decorated by its type.
  //  (c) Each type identifier is replaced by the type it denotes.
  // Types are represented by small ASTs.

  public void check(Program ast) {
    ast.visit(this, null);
  }

  /////////////////////////////////////////////////////////////////////////////

  public Checker (ErrorReporter reporter) {
    this.reporter = reporter;
    this.idTable = new IdentificationTable ();
    establishStdEnvironment();
  }

  private IdentificationTable idTable;
  private static SourcePosition dummyPos = new SourcePosition();
  private ErrorReporter reporter;

  // Reports that the identifier or operator used at a leaf of the AST
  // has not been declared.

  private void reportUndeclared (Terminal leaf) {
    reporter.reportError("\"%\" is not declared", leaf.spelling, leaf.position);
  }


  private static TypeDenoter checkFieldIdentifier(FieldTypeDenoter ast, Identifier I) {
    if (ast instanceof MultipleFieldTypeDenoter) {
      MultipleFieldTypeDenoter ft = (MultipleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      } else {
        return checkFieldIdentifier (ft.FT, I);
      }
    } else if (ast instanceof SingleFieldTypeDenoter) {
      SingleFieldTypeDenoter ft = (SingleFieldTypeDenoter) ast;
      if (ft.I.spelling.compareTo(I.spelling) == 0) {
        I.decl = ast;
        return ft.T;
      }
    }
    return StdEnvironment.errorType;
  }


  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private TypeDeclaration declareStdType (String id, TypeDenoter typedenoter) {

    TypeDeclaration binding;

    binding = new TypeDeclaration(new Identifier(id, dummyPos), typedenoter, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private ConstDeclaration declareStdConst (String id, TypeDenoter constType) {
 
    IntegerExpression constExpr;
    ConstDeclaration binding;

    // constExpr used only as a placeholder for constType
    constExpr = new IntegerExpression(null, dummyPos);
    constExpr.type = constType;
    binding = new ConstDeclaration(new Identifier(id, dummyPos), constExpr, dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private ProcDeclaration declareStdProc (String id, FormalParameterSequence fps) {

    ProcDeclaration binding;

    binding = new ProcDeclaration(new Identifier(id, dummyPos), fps,
                                  new EmptyCommand(dummyPos), dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a standard
  // type, and enters it in the identification table.

  private FuncDeclaration declareStdFunc (String id, FormalParameterSequence fps,
                                          TypeDenoter resultType) {

    FuncDeclaration binding;

    binding = new FuncDeclaration(new Identifier(id, dummyPos), fps, resultType,
                                  new EmptyExpression(dummyPos), dummyPos);
    idTable.enter(id, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // unary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private UnaryOperatorDeclaration declareStdUnaryOp
    (String op, TypeDenoter argType, TypeDenoter resultType) {

    UnaryOperatorDeclaration binding;

    binding = new UnaryOperatorDeclaration (new Operator(op, dummyPos),
                                            argType, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates a small AST to represent the "declaration" of a
  // binary operator, and enters it in the identification table.
  // This "declaration" summarises the operator's type info.

  private BinaryOperatorDeclaration declareStdBinaryOp
    (String op, TypeDenoter arg1Type, TypeDenoter arg2type, TypeDenoter resultType) {

    BinaryOperatorDeclaration binding;

    binding = new BinaryOperatorDeclaration (new Operator(op, dummyPos),
                                             arg1Type, arg2type, resultType, dummyPos);
    idTable.enter(op, binding);
    return binding;
  }

  // Creates small ASTs to represent the standard types.
  // Creates small ASTs to represent "declarations" of standard types,
  // constants, procedures, functions, and operators.
  // Enters these "declarations" in the identification table.

  private final static Identifier dummyI = new Identifier("", dummyPos);

  //Aqui tenemos todo lo del nivel 0, variables y funciones
  //StdEnvironment en el paquete de triangulo
  private void establishStdEnvironment () {
    
    // idTable.startIdentification();
    StdEnvironment.booleanType = new BoolTypeDenoter(dummyPos);
    StdEnvironment.integerType = new IntTypeDenoter(dummyPos);
    StdEnvironment.charType = new CharTypeDenoter(dummyPos);
    StdEnvironment.anyType = new AnyTypeDenoter(dummyPos);
    StdEnvironment.errorType = new ErrorTypeDenoter(dummyPos);
    
    StdEnvironment.arrayType = new ArrayTypeDenoter(new IntegerLiteral("",dummyPos), new IntTypeDenoter(dummyPos),dummyPos);

    StdEnvironment.booleanDecl = declareStdType("Boolean", StdEnvironment.booleanType);
    StdEnvironment.falseDecl = declareStdConst("false", StdEnvironment.booleanType);
    StdEnvironment.trueDecl = declareStdConst("true", StdEnvironment.booleanType);
    StdEnvironment.notDecl = declareStdUnaryOp("\\", StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.andDecl = declareStdBinaryOp("/\\", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);
    StdEnvironment.orDecl = declareStdBinaryOp("\\/", StdEnvironment.booleanType, StdEnvironment.booleanType, StdEnvironment.booleanType);

    StdEnvironment.integerDecl = declareStdType("Integer", StdEnvironment.integerType);
    StdEnvironment.maxintDecl = declareStdConst("maxint", StdEnvironment.integerType);
    StdEnvironment.addDecl = declareStdBinaryOp("+", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.subtractDecl = declareStdBinaryOp("-", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.multiplyDecl = declareStdBinaryOp("*", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.divideDecl = declareStdBinaryOp("/", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.moduloDecl = declareStdBinaryOp("//", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.integerType);
    StdEnvironment.lessDecl = declareStdBinaryOp("<", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notgreaterDecl = declareStdBinaryOp("<=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.greaterDecl = declareStdBinaryOp(">", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);
    StdEnvironment.notlessDecl = declareStdBinaryOp(">=", StdEnvironment.integerType, StdEnvironment.integerType, StdEnvironment.booleanType);

    StdEnvironment.charDecl = declareStdType("Char", StdEnvironment.charType);
    StdEnvironment.chrDecl = declareStdFunc("chr", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos), StdEnvironment.charType);
    StdEnvironment.ordDecl = declareStdFunc("ord", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos), StdEnvironment.integerType);
    StdEnvironment.eofDecl = declareStdFunc("eof", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.eolDecl = declareStdFunc("eol", new EmptyFormalParameterSequence(dummyPos), StdEnvironment.booleanType);
    StdEnvironment.getDecl = declareStdProc("get", new SingleFormalParameterSequence(
                                      new VarFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.putDecl = declareStdProc("put", new SingleFormalParameterSequence(
                                      new ConstFormalParameter(dummyI, StdEnvironment.charType, dummyPos), dummyPos));
    StdEnvironment.getintDecl = declareStdProc("getint", new SingleFormalParameterSequence(
                                            new VarFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.putintDecl = declareStdProc("putint", new SingleFormalParameterSequence(
                                            new ConstFormalParameter(dummyI, StdEnvironment.integerType, dummyPos), dummyPos));
    StdEnvironment.geteolDecl = declareStdProc("geteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.puteolDecl = declareStdProc("puteol", new EmptyFormalParameterSequence(dummyPos));
    StdEnvironment.equalDecl = declareStdBinaryOp("=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);
    StdEnvironment.unequalDecl = declareStdBinaryOp("\\=", StdEnvironment.anyType, StdEnvironment.anyType, StdEnvironment.booleanType);

  }

    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Johel Mora Calderon
    //  Commands 
    //
    ///////////////////////////////////////////////////////////////////////////////
  // --------------Commands-------------------------------------------------------------
    // Yosua Blanco
    // Dilan Walker
    //
    //-------------------
    @Override
    public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o) {
        //Método implementado para "repeat" "while" Expression "do" Command "end"
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if(! eType.equals(StdEnvironment.booleanType))
          reporter.reportError("Boolean expression expected here", "", ast.E.position);
        ast.C.visit(this, null);
        return null;
    }
    // Yosua Blanco
    // Dilan Walker
    //
    //-------------------
    @Override
    public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o) {
        //Método implementado para "repeat" "until" Expression "do" Command "end"
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if (! eType.equals(StdEnvironment.booleanType))
            reporter.reportError("Boolean expression expected here", "", ast.E.position);
        ast.C.visit(this, null);
        return null;
    }
    // Yosua Blanco
    // Dilan Walker
    //
    //-------------------
    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o) {
        //Método implementado para "repeat" "do" Command "while" Expression "end"
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if(! eType.equals(StdEnvironment.booleanType))
          reporter.reportError("Boolean expression expected here", "", ast.E.position);
        ast.C.visit(this, null);
        return null;
    }
    // Yosua Blanco
    // Dilan Walker
    //
    //-------------------
    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o) {
       //Método implementado para "repeat" "do" Command "until" Expression "end"
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        if(! eType.equals(StdEnvironment.booleanType))
          reporter.reportError("Boolean expression expected here", "", ast.E.position);
        ast.C.visit(this, null);
        return null;
    }
    // Yosua Blanco
    // Dilan Walker
    //
    //-------------------
    @Override
    public Object visitRepeatForRangeWhile(RepeatForRangeWhile ast, Object o) {
        TypeDenoter eType1 = (TypeDenoter) ast.E1.visit(this, null);
        TypeDenoter eType3 = (TypeDenoter) ast.R.E.visit(this, null);
        
        if(! eType3.equals(StdEnvironment.integerType))
          reporter.reportError("Integer expression expected here", "", ast.R.E.position);
        if(! eType1.equals(StdEnvironment.integerType))
          reporter.reportError("Integer expression expected here", "", ast.E1.position);
        
        
        idTable.openScope();
            idTable.enter(ast.R.I.spelling, ast.R);
            
            if(ast.R.duplicated)
                reporter.reportError("identifier \"%\" already declared", ast.R.I.spelling, ast.position);
            
            TypeDenoter eType2 = (TypeDenoter) ast.E2.visit(this, null);
            if(! eType2.equals(StdEnvironment.booleanType))
                reporter.reportError("Boolean expression expected here", "", ast.E2.position);
          
            ast.C.visit(this, null);
        idTable.closeScope();
        
        return null; 
    }
    // Yosua Blanco
    // Dilan Walker
    // 
    //-------------------
    @Override 
    public Object visitRepeatForRange(RepeatForRange ast, Object o) {
        TypeDenoter eType3 = (TypeDenoter) ast.R.E.visit(this, null);
        
        if(! eType3.equals(StdEnvironment.integerType))
          reporter.reportError("Integer expression expected here", "", ast.R.E.position);
        idTable.openScope();
            idTable.enter(ast.R.I.spelling, ast.R);
            
            if(ast.R.duplicated)
                reporter.reportError("identifier \"%\" already declared", ast.R.I.spelling, ast.position);
            
            TypeDenoter eType2 = (TypeDenoter) ast.E.visit(this, null);
            if(! eType2.equals(StdEnvironment.integerType))
                reporter.reportError("Integer expression expected here", "", ast.E.position);
          
            ast.C.visit(this, null);
        idTable.closeScope();
        return null;
    }
    // Yosua Blanco
    // Dilan Walker
    // 
    //-------------------
    @Override
    public Object visitRepeatForRangeUntil(RepeatForRangeUntil ast, Object o) {
        TypeDenoter eType1 = (TypeDenoter) ast.E1.visit(this, null);
        TypeDenoter eType3 = (TypeDenoter) ast.R.E.visit(this, null);
        
        if(! eType3.equals(StdEnvironment.integerType))
          reporter.reportError("Integer expression expected here", "", ast.R.E.position);
        if(! eType1.equals(StdEnvironment.integerType))
          reporter.reportError("Integer expression expected here", "", ast.E1.position);
        
        
        idTable.openScope();
            idTable.enter(ast.R.I.spelling, ast.R);
            
            if(ast.R.duplicated)
                reporter.reportError("identifier \"%\" already declared", ast.R.I.spelling, ast.position);
            
            TypeDenoter eType2 = (TypeDenoter) ast.E2.visit(this, null);
            if(! eType2.equals(StdEnvironment.booleanType))
                reporter.reportError("Boolean expression expected here", "", ast.E2.position);
          
            ast.C.visit(this, null);
        idTable.closeScope();
        return null;
    }
    // Yosua Blanco
    // Dilan Walker
    //
    //-------------------
    @Override
    public Object visitRepeatIn(RepeatIn ast, Object o) {
        TypeDenoter eType2 = (TypeDenoter) ast.I.E.visit(this, null);
        if (!eType2.equals(StdEnvironment.arrayType)) {
            reporter.reportError("Array expression expected here", "", ast.I.E.position);
        }
        idTable.openScope();
            idTable.enter(ast.I.I.spelling, ast.I);
            if (ast.I.duplicated)
                reporter.reportError ("identifier \"%\" already declared", ast.I.I.spelling, ast.position);
            
            ast.C.visit(this, null);
        idTable.closeScope();
        return null;
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Johel Mora Calderon
    //  Declarations 
    //
    ///////////////////////////////////////////////////////////////////////////////
    //-----------------------------Declarations--------------------------------------------------
    @Override
    public Object visitVarDeclarationExpression(VarDeclarationExpression ast, Object o) {
      
      TypeDenoter eType1 = (TypeDenoter) ast.E.visit(this, null);
      idTable.enter (ast.I.spelling, ast);
      if (ast.duplicated)
        reporter.reportError ("identifier \"%\" already declared",
                              ast.I.spelling, ast.position);
      return ast.E.type;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitProcFuncsDeclaration(ProcFuncsDeclaration ast, Object o) {
        
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
      
        return null;
    }

    
    @Override
    public Object visitLocalProcFuncDeclaration(LocalProcFuncDeclaration ast, Object o) {
        
        idTable.pushToPublicStack();
        ast.D1.visit(this, null);
        
        idTable.pushToPrivateStack();
        ast.D2.visit(this, null);
        
        idTable.closePrivateStack();
        
        return null;
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Johel Mora Calderon
    //  Adition if RecursiveDeclaration
    //
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public Object visitRecursiveDeclaration(RecursiveDeclaration ast, Object o) {
        ast.PF.visit(this, null);
        ast.PF.visitRecursive(this, null);
        return null;
    }
    

    @Override
    public Object visitRangeVarDecl(RangeVarDecl ast, Object o) {
        
        idTable.enter(ast.I.spelling, ast);
        if (ast.duplicated) {
            reporter.reportError("identifier \"%\" already declared",
                    ast.I.spelling, ast.position);
        }
        
        return ast.E.type;
    }
    
    
    @Override
    public Object visitSelectCommand(SelectCommand ast, Object o) {
        TypeDenoter expressionType = (TypeDenoter) ast.EXP.visit(this, null);
        if( ! expressionType.equals( StdEnvironment.charType) && ! expressionType.equals( StdEnvironment.integerType) ){
            reporter.reportError("Integer or Char expression expected here", "", ast.EXP.position);
        }
        SelectData expressionData = new SelectData(expressionType);
        ast.COM.visit(this, expressionData);
        return null;
    }

    @Override
    public Object visitInVarDecl(InVarDecl ast, Object o) {
        idTable.enter(ast.I.spelling, ast);
        if (ast.duplicated) {
            reporter.reportError("identifier \"%\" already declared",
                    ast.I.spelling, ast.position);
        }
        return ast.E.type;
    }

    @Override
    public Object visitCases(Cases ast, Object typeExpression) {
        ast.CASE1.visit(this, typeExpression);
        if(ast.CASE2 != null)
            ast.CASE2.visit(this, typeExpression);
        return null;
    }

    @Override
    public Object visitElseCase(ElseCase ast, Object o) {
        ast.COM.visit(this, o);
        return null;
    }

    @Override
    public Object visitSequentialCase(SequentialCase ast, Object typeExpression) {
        ast.C1.visit(this, typeExpression);
        ast.C2.visit(this, typeExpression);
        return null;
    }
    
    @Override
    public Object visitCaseWhen(CaseWhen ast, Object typeExpression) {
        ast.CASELIT.visit(this, typeExpression);
        ast.COM.visit(this, null);
        return null;
    }

    @Override
    public Object visitCaseLiterals(CaseLiterals ast, Object typeExpression) {
        ast.CASERANGE.visit(this, typeExpression);
        return null;
    }

    @Override
    public Object visitSequentialCaseRange(SequentialCaseRange ast, Object typeExpression) {
        ast.C1.visit(this, typeExpression);
        ast.C2.visit(this, typeExpression);
        return null;
    }

    @Override
    public Object visitCaseLiteralCHAR(CaseLiteralCHAR ast, Object o) {
        return StdEnvironment.charType;
    }

    @Override
    public Object visitCaseLiteralINT(CaseLiteralINT ast, Object o) {
        return StdEnvironment.integerType;
    }

    @Override
public Object visitCaseRangeCase(CaseRangeCase ast, Object selectData) {
        SelectData valuesData = (SelectData) selectData;
        TypeDenoter typeExpression = ((SelectData) selectData).getType();
        TypeDenoter typeCaseLit1  = (TypeDenoter) ast.CASELIT.visit(this, null);
        TypeDenoter typeCaseLit2 = null;
        if( ast.CASELIT2 != null )
              typeCaseLit2 = (TypeDenoter) ast.CASELIT2.visit(this, null);
        if( typeExpression.equals(StdEnvironment.charType) ){
            if( ! typeCaseLit1.equals(typeExpression) ){
                 reporter.reportError ("Char expression expected here", "",
				ast.CASELIT.position);
            }
            if( typeCaseLit2 != null && ! typeCaseLit2.equals(typeExpression) ){
                 reporter.reportError ("Char expression expected here", "",
				ast.CASELIT2.position);
            }
        }
        else if( typeExpression.equals(StdEnvironment.integerType) ){
            if( ! typeCaseLit1.equals(typeExpression) ){
                 reporter.reportError ("Integer expression expected here", "",
				ast.CASELIT.position);
            }
            if(typeCaseLit2 != null && ! typeCaseLit2.equals(typeExpression) ){
                 reporter.reportError ("Integer expression expected here", "",
				ast.CASELIT2.position);
            }
        }
            if( ast.CASELIT instanceof CaseLiteralCHAR && ast.CASELIT2 == null ){
                if(!valuesData.exists( ((CharacterLiteral) ( ( (CaseLiteralCHAR) ast.CASELIT).CHARLIT)).spelling)){
                    valuesData.addData( ((CharacterLiteral) ( ( (CaseLiteralCHAR) ast.CASELIT).CHARLIT)).spelling );
                }
                else{
                 reporter.reportError ("Repeated Character Literal in Select Command", "",
				ast.CASELIT.position);
                }
            }
            else if( ast.CASELIT instanceof CaseLiteralINT && ast.CASELIT2 == null){
                if(!valuesData.exists(((IntegerLiteral) ( ( (CaseLiteralINT) ast.CASELIT).INTLIT)).spelling)){
                     valuesData.addData( ((IntegerLiteral) ( ( (CaseLiteralINT) ast.CASELIT).INTLIT)).spelling );
                }
                 else{
                 reporter.reportError ("Repeated Integer Literal in Select Command", "",
				ast.CASELIT.position);
                }
            }
             
            if(ast.CASELIT2 !=  null && typeExpression.equals(typeCaseLit2) ){
                if( ast.CASELIT instanceof CaseLiteralCHAR){
                    addCharactersValues(valuesData,
                            ((CharacterLiteral) ( ( (CaseLiteralCHAR) ast.CASELIT).CHARLIT)).spelling,
                            ((CharacterLiteral) ( ( (CaseLiteralCHAR) ast.CASELIT2).CHARLIT)).spelling,
                            ast.CASELIT.position);
                }
                else if(ast.CASELIT instanceof CaseLiteralINT){
                    addIntegerValues(valuesData,
                            ((IntegerLiteral) ( ( (CaseLiteralINT) ast.CASELIT).INTLIT)).spelling,
                            ((IntegerLiteral) ( ( (CaseLiteralINT) ast.CASELIT2).INTLIT)).spelling,
                            ast.CASELIT.position);
                }
            }
        return null;
    }

    private void addCharactersValues(SelectData actualValues,String value1,String value2,SourcePosition position ){
        char value1Char = value1.charAt(1);
        char value2Char = value2.charAt(1);
        char counter;
        for (int i = value1Char ; i < value2Char + 1; i++) {
            counter = (char)i;
            if(!actualValues.exists( "'" + Character.toString(counter) + "'" ) ){
                actualValues.addData( "'" + Character.toString(counter) + "'" );
            }
            else{
                reporter.reportError ("Repeated Character Literal in Choose Command", "", position);
            }
        }
    }
    
    private void addIntegerValues(SelectData actualValues,String value1,String value2,SourcePosition position ){
        int value1Int = Integer.parseInt(value1);
        int value2Int = Integer.parseInt(value2) + 1;
        
        for (int i = value1Int; i < value2Int; i++) { 
            if( actualValues.exists( Integer.toString(i) ) ){
                 reporter.reportError ("Repeated Integer Literal in Choose Command", "", position);
            }
            else{
                actualValues.addData( Integer.toString(i ) );
            }
        }
    }
    

    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Johel Mora Calderon
    //  Adicion de metodos variables recursivos 
    //
    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public Object visitRecursiveFuncVar(RecursiveFunc ast, Object o) {
        idTable.openScope();
        ast.FPS.visit(this, null);
        TypeDenoter eType = (TypeDenoter) ast.E.visit(this, null);
        idTable.closeScope();
        if (! ast.T.visit(this, null).equals(eType))
            reporter.reportError ("body of function \"%\" has wrong type",
                            ast.I.spelling, ast.E.position);
        return null;
    }

    @Override
    public Object visitRecursiveProcVar(RecursiveProc ast, Object o) {
        idTable.openScope();
        ast.FPS.visit(this, null);
        ast.C.visit(this, null);
        idTable.closeScope();
        return null;
    }

    @Override
    public Object visitSequentialProcFuncs(SequentialProcFuncs ast, Object o) {
        ast.PF1.visit(this, null);
        ast.PF2.visit(this, null);
        return null;
    }

    @Override
    public Object visitSequentialProcFuncsVar(SequentialProcFuncs ast, Object o) {
        ast.PF1.visitRecursive(this, null);
        ast.PF2.visitRecursive(this, null);
        return null;
    }
}