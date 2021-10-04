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


public class SequentialProcFuncs extends ProcFuncs{

  public SequentialProcFuncs(ProcFuncs p1AST, ProcFuncs p2AST, SourcePosition thePosition) {
    super(thePosition);
    PF1 = p1AST;
    PF2 = p2AST ;      
  }

  public Object visit(Visitor v, Object o) {
    return v.visitSequentialProcFuncs(this, o);
  }
  
  public ProcFuncs PF1;
  public ProcFuncs PF2;

  @Override
  public Object visitPF(Visitor v, Object o) {
    return v.visitSequentialProcFuncsPF(this, o);
  }
  

}
