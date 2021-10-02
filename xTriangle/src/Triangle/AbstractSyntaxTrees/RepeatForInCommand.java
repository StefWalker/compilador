/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;
import Triangle.SyntacticAnalyzer.SourcePosition;
/**
 *
 * @author Yosua Blanco Diaz
 */
public class RepeatForInCommand    extends Command {

  public RepeatForInCommand  (Expression e1AST, Command cAST, SourcePosition thePosition) {
    super (thePosition);
    E1 = e1AST;
    C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitRepeatForInCommand(this, o);
  }

  public Expression E1;
  public Command C;
}