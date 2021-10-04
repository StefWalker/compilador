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
public class Proc extends ProcFuncs{
    
    public Proc(Identifier iAST, FormalParameterSequence fpsAST, Command cAST,SourcePosition thePosition){
        super(thePosition);
        I = iAST;
        FPS = fpsAST;
        C = cAST;
        
    }
    
    @Override
    public Object visit(Visitor v, Object o){
        return v.visitProc(this, o);
    }
    
    public Identifier I;
    public FormalParameterSequence FPS;
    public Command C;
    
    @Override
    public Object visitPF(Visitor v, Object o){
        return v.visitProcPF(this, o);
    }
}
