//This class is used to represent a literal and a command
//in a when statement
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class CaseWhen extends Case {

  public CaseWhen (CaseLiterals caselitAST, Command comAST, SourcePosition thePosition) {
    super (thePosition);
    CASELIT = caselitAST;
    COM = comAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitCaseWhen(this, o);
  }

  public Command COM;
  public CaseLiterals CASELIT;
}