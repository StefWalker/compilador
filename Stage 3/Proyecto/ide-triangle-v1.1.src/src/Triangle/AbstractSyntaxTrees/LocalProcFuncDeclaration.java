/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Natalia
 */
public class LocalProcFuncDeclaration extends Declaration {
  
  public LocalProcFuncDeclaration (Declaration d1AST, Declaration d2AST, SourcePosition thePosition) {
    super (thePosition);
    D1 = d1AST;
    D2 = d2AST;
  }


  @Override
  public Object visit(Visitor v, Object o) {
    return v.visitLocalProcFuncDeclaration(this, o);
  }

  public Declaration D1, D2;


}
