//Used to represent an integer literal in a command.
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class CaseLiteralINT extends CaseLiteral {

  public CaseLiteralINT (IntegerLiteral intAST, SourcePosition thePosition) {
    super (thePosition);
    INTLIT = intAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitCaseLiteralINT(this, o);
  }

  public IntegerLiteral INTLIT;
}