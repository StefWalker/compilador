/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author Natalia
 */
public class RangeVarDecl extends Declaration {
    
    public RangeVarDecl(Identifier iAST,Expression eAST,SourcePosition thePosition) {
        super(thePosition);
        I = iAST;
        E = eAST;   
    }

    public Object visit(Visitor v, Object o) {
        return v.visitRangeVarDecl(this, o);
    }
    
    public Identifier I;
    public Expression E;
}
