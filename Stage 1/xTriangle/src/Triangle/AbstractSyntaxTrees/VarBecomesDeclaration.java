/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

/**
 *
 * @author Yosua Blanco Diaz
 */
import Triangle.SyntacticAnalyzer.SourcePosition;

public class VarBecomesDeclaration  extends Declaration {

  public VarBecomesDeclaration  (Identifier iAST, Expression eAST,
                         SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    E = eAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitVarBecomesDeclaration(this, o);
  }

  public Identifier I;
  public Expression E;
}
