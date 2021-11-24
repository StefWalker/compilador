
//Used to represent two case range when they are in the same expression

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class SequentialCaseRange extends CaseRange {

  public SequentialCaseRange (CaseRange c1AST, CaseRange c2AST, SourcePosition thePosition) {
    super (thePosition);
    C1 = c1AST;
    C2 = c2AST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitSequentialCaseRange(this, o);
  }

  public CaseRange C1, C2;
}