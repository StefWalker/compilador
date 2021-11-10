
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

///////////////////////////////////////////////////////////////////////////////
//
//  Yosua Andres Blanco Diaz
//  Dylan Stef Torres Walker 
//  Adition if RecursiveDeclaration
//
///////////////////////////////////////////////////////////////////////////////

public class RecursiveDeclaration extends Declaration{

  public RecursiveDeclaration(Declaration pfAST, SourcePosition thePosition) {
    super(thePosition);
    PF = pfAST;
  }

  @Override
  public Object visit(Visitor v, Object o) {
    return v.visitRecursiveDeclaration(this, o);
  }

  public Declaration PF;
}