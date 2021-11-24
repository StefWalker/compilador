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
public class RepeatForRange extends Command {
    
    public RepeatForRange(RangeVarDecl rvdAST, Expression eAST, Command cAST, SourcePosition thePosition) {
        super(thePosition);
        R = rvdAST;
        E = eAST;
        C = cAST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRepeatForRange(this, o);
    }

    public Expression E;
    public Command C;
    public RangeVarDecl R;
}
