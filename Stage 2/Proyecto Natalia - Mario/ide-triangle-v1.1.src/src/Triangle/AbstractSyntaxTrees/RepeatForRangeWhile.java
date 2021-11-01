/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Natalia Vargas
 */
public class RepeatForRangeWhile extends Command {

     public RepeatForRangeWhile(RangeVarDecl rvdAST, Expression e1AST, Command cAST, Expression e2AST, SourcePosition thePosition) {
        super(thePosition);
        R = rvdAST;
        E1 = e1AST;
        C = cAST;
        E2 = e2AST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRepeatForRangeWhile(this, o);
    }

    public Expression E1;
    public Command C;
    public RangeVarDecl R;
    public Expression E2;
}
