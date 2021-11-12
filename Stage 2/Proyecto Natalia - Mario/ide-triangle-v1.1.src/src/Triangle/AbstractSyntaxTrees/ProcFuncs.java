/*
  Marcos Méndez Hidalgo 2021-04-10
*/

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public abstract class ProcFuncs extends Declaration{

  public ProcFuncs(SourcePosition thePosition) {
    super(thePosition);
  }

  public abstract Object visitRecursive(Visitor v, Object o);
}
