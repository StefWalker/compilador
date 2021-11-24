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
public class RepeatIn extends Command {
    public RepeatIn(InVarDecl ivdAST,Command cAST, SourcePosition thePosition) {
        super(thePosition);
        I = ivdAST;
        C = cAST;
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRepeatIn(this, o);
    }

    public Command C;
    public InVarDecl I;
}
