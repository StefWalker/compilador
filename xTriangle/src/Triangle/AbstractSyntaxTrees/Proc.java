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
public abstract class Proc extends ProcFunc{
    
    public Proc(Identifier iAST, FormalParameterSequence fpsAST, Command cAST,SourcePosition thePosition){
        super(thePosition);
        I = iAST;
        FPS = fpsAST;
        C = cAST;
        
    }
    
    public abstract Object visitProc(Visitor v, Object o);
    
    public Identifier I;
    public FormalParameterSequence FPS;
    public Command C;
}
