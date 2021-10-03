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

  public ProcFuncs(SourcePosition thePosition) {
    super(thePosition);
  }

  public abstract Object visitRec(Visitor v, Object o);
}
