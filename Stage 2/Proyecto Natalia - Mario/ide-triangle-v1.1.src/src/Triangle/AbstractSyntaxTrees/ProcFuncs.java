/*
  Marcos Méndez Hidalgo 2021-04-10
*/

package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Johel Mora Calderon
    //  Implementacion de Proc Funcs con nueva declaracion para metodos 
    //    duplicados utilizados en el recursive
    //
    ///////////////////////////////////////////////////////////////////////////////

public abstract class ProcFuncs extends Declaration{

  public ProcFuncs(SourcePosition thePosition) {
    super(thePosition);
  }

  public abstract Object visitRecursive(Visitor v, Object o);
}
