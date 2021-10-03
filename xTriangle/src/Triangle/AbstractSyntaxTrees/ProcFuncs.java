/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;


///////////////////////////////////////////////////////////////////////////////
//
//  Yosua Andres Blanco Diaz
//  Dylan Stef Torres Walker 
//  Proc-Func  
//
///////////////////////////////////////////////////////////////////////////////


public abstract class ProcFuncs extends Declaration{

  public ProcFuncs(ProcFunc p1AST, ProcFunc p2AST, SourcePosition thePosition) {
    super(thePosition);
    PF1 = p1AST;
    PF2 = p2AST ;      
  }

  public Object visit(Visitor v, Object o) {
    return v.visitProcFuncs(this, o);
  }
  
  public ProcFunc PF1;
  public ProcFunc PF2;

}
