package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

public class Cases extends Case {

  public Cases (Case case1AST, ElseCase case2AST,
               SourcePosition thePosition) {
    super (thePosition);
    CASE1 = case1AST;
    CASE2 = case2AST;
  }

  @Override
  public Object visit(Visitor v, Object o) {
    return v.visitCases(this, o);
  }

  public Case CASE1;
  public ElseCase CASE2;
}