/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;
import Triangle.SyntacticAnalyzer.SourcePosition;
/**
 *
 * @author johel
 */
public class SelectCommand extends Command {
    public SelectCommand (Expression expAST, Command comAST, SourcePosition thePosition) {
        super (thePosition);
        EXP = expAST;
        COM = comAST;
  }

  @Override
  public Object visit(Visitor v, Object o) {
    return v.visitSelectCommand(this, o);
  }
  public Expression EXP;
  public Command COM;
}
