//Used to represent a character literal in a command.
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class CaseLiteralCHAR extends CaseLiteral {

  public CaseLiteralCHAR (CharacterLiteral charAST, SourcePosition thePosition) {
    super (thePosition);
    CHARLIT = charAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitCaseLiteralCHAR(this, o);
  }

  public CharacterLiteral CHARLIT;
}