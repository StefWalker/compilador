/*
 * IDE-Triangle v1.0
 * TreeVisitor.java
 */

package Core.Visitors;
import Triangle.AbstractSyntaxTrees.AST;
import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyExpression;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.ErrorTypeDenoter;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.UnaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
import javax.swing.tree.DefaultMutableTreeNode;

// Llamadas a nuevas implementaciones 
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatForDoCommand;
import Triangle.AbstractSyntaxTrees.RepeatForWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatForUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatForInCommand;
import Triangle.AbstractSyntaxTrees.VarBecomesDeclaration;

import Triangle.AbstractSyntaxTrees.SequentialProcFuncs;
import Triangle.AbstractSyntaxTrees.RecursiveDeclaration;
import Triangle.AbstractSyntaxTrees.LocalDeclaration;


/**
 * Implements the Triangle Visitor interface, which is used to
 * visit an entire AST. 
 *
 * Generates DefaultMutableTreeNodes, used to draw a JTree.
 *
 * @author Luis Leopoldo P�rez <luiperpe@ns.isi.ulatina.ac.cr>
 */
public class TreeVisitor implements Visitor {
      
    /**
     * Creates a new instance of TreeVisitor.
     */
    public TreeVisitor() {
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Commands ">    
    // Commands  
    public Object visitAssignCommand(AssignCommand ast, Object o) {
        return(createBinary("Assign Command", ast.V, ast.E));
    }
    
    public Object visitCallCommand(CallCommand ast, Object o) {
        return(createBinary("Call Command", ast.I, ast.APS));
    }
    
    public Object visitEmptyCommand(EmptyCommand ast, Object o) {
        return(createNullary("Empty Command"));
    }
    
    public Object visitIfCommand(IfCommand ast, Object obj) {
        return(createTernary("If Command", ast.E, ast.C1, ast.C2));
    }
    
    public Object visitLetCommand(LetCommand ast, Object obj) {
        return(createBinary("Let Command", ast.D, ast.C));
    }
    
    public Object visitSequentialCommand(SequentialCommand ast, Object obj) {
        return(createBinary("Sequential Command", ast.C1, ast.C2));
    }
    
    public Object visitWhileCommand(WhileCommand ast, Object obj) {
        return(createBinary("While Command", ast.E, ast.C));
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Adition of the new commands
    //
    ///////////////////////////////////////////////////////////////////////////////
  
  public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o){
      return (createBinary("RepeatWhileCom.", ast.E, ast.C));
  }
  public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o){
      return (createBinary("RepeatUntilCom.", ast.E, ast.C));
  }
  public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o){
      return (createBinary("RepeatDoWhileCom.", ast.C, ast.E));
  }
  public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o){
      return (createBinary("RepeatDoUntilCom.", ast.C, ast.E));
  }
  public Object visitRepeatForDoCommand  (RepeatForDoCommand ast, Object o){
      return (createQuaternary("RepeatForDoCom.", ast.I, ast.E1, ast.E2, ast.C));
  }
  public Object visitRepeatForWhileCommand(RepeatForWhileCommand ast, Object o){
      return (createQuinary("RepeatForWhileCom.", ast.I, ast.E1, ast.E2, ast.E3, ast.C));
  }
  public Object visitRepeatForUntilCommand(RepeatForUntilCommand ast, Object o){
      return (createQuinary("RepeatForUntilCom.", ast.I, ast.E1, ast.E2, ast.E3, ast.C));
  }
  public Object visitRepeatForInCommand  (RepeatForInCommand ast, Object o){
      return (createTernary("RepeatForInCom.", ast.I, ast.E1, ast.C));
  }
  
  
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Expressions ">
    // Expressions
    public Object visitArrayExpression(ArrayExpression ast, Object obj) {
        return(createUnary("Array Expression", ast.AA));
    }
    
    public Object visitBinaryExpression(BinaryExpression ast, Object obj) {
        return(createTernary("Binary Expression", ast.E1, ast.O, ast.E2));
    }
    
    public Object visitCallExpression(CallExpression ast, Object obj) {
        return(createBinary("Call Expression", ast.I, ast.APS));
    }
    
    public Object visitCharacterExpression(CharacterExpression ast, Object obj) {
        return(createUnary("Character Expression", ast.CL));
    }
    
    public Object visitEmptyExpression(EmptyExpression ast, Object obj) {
        return(createNullary("Empty Expression"));
    }
    
    public Object visitIfExpression(IfExpression ast, Object obj) {
        return(createTernary("If Expression", ast.E1, ast.E2, ast.E3));
    }
    
    public Object visitIntegerExpression(IntegerExpression ast, Object obj) {
        return(createUnary("Integer Expression", ast.IL));
    }
    
    public Object visitLetExpression(LetExpression ast, Object obj) {
        return(createBinary("Let Expression", ast.D, ast.E));
    }
    
    public Object visitRecordExpression(RecordExpression ast, Object obj) {
        return(createUnary("Record Expression", ast.RA));
    }
    
    public Object visitUnaryExpression(UnaryExpression ast, Object obj) {
        return(createBinary("Unary Expression", ast.O, ast.E));
    }
    
    public Object visitVnameExpression(VnameExpression ast, Object obj) {
        return(createUnary("Vname Expression", ast.V));
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Declarations ">
    // Declarations
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object obj) {
        return(createQuaternary("Binary Operator Declaration", ast.O, ast.ARG1, ast.ARG2, ast.RES));
    }
    
    public Object visitConstDeclaration(ConstDeclaration ast, Object obj) {
        return(createBinary("Constant Declaration", ast.I, ast.E));
    }
    
    public Object visitFuncDeclaration(FuncDeclaration ast, Object obj) {
        return(createQuaternary("Function Declaration", ast.I, ast.FPS, ast.T, ast.E));
    }
    
    public Object visitProcDeclaration(ProcDeclaration ast, Object obj) {
        return(createTernary("Procedure Declaration", ast.I, ast.FPS, ast.C));        
    }
    
    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object obj) {
        return(createBinary("Sequential Declaration", ast.D1, ast.D2));
    }
    
    public Object visitTypeDeclaration(TypeDeclaration ast, Object obj) {
        return(createBinary("Type Declaration", ast.I, ast.T));
    }
    
    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object obj) {
        return(createTernary("Unary Operator Declaration", ast.O, ast.ARG, ast.RES));
    }
    
    public Object visitVarDeclaration(VarDeclaration ast, Object obj) {
        return(createBinary("Variable Declaration", ast.I, ast.T));
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Adition of the new commands
    //
    ///////////////////////////////////////////////////////////////////////////////
    public Object visitVarBecomesDeclaration(VarBecomesDeclaration ast, Object o){
        return (createBinary("VarBecomeDecla", ast.I, ast.E));
    }
    public Object visitSequentialProcFuncs(SequentialProcFuncs ast, Object o){
        return (createBinary("SequentialProcFuncs", ast.PF1, ast.PF2));
    }
    public Object visitSequentialProcFuncsPF(SequentialProcFuncs ast, Object o){
        return (createBinary("SequentialProcFuncsPF", ast.PF1, ast.PF2));
    }
    public Object visitRecursiveDeclaration(RecursiveDeclaration ast, Object obj) {
      return (createUnary("RecursiveDeclarationDecl.", ast.PF ));
    }
    public Object visitLocalDeclaration(LocalDeclaration ast, Object obj) {
      return (createBinary("LocalDeclarationDecl.", ast.D1, ast.D2));
    }
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Aggregates ">
    // Array Aggregates
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object obj) {
        return(createBinary("Multiple Array Aggregate", ast.E, ast.AA));
    }
    
    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object obj) {
        return(createUnary("Single Array Aggregate", ast.E));
    }
    
    // Record Aggregates
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object obj) {
        return(createTernary("Multiple Record Aggregate", ast.I, ast.E, ast.RA));
    }
    
    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object obj) {
        return(createBinary("Single Record Aggregate", ast.I, ast.E));
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Parameters ">
    // Formal Parameters   
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object obj) {
        return(createBinary("Constant Formal Parameter", ast.I, ast.T));
    }
    
    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object obj) {
        return(createTernary("Function Formal Parameter", ast.I, ast.FPS, ast.T));
    }
    
    public Object visitProcFormalParameter(ProcFormalParameter ast, Object obj) {
        return(createBinary("Procedure Formal Parameter", ast.I, ast.FPS));
    }
    
    public Object visitVarFormalParameter(VarFormalParameter ast, Object obj) {
        return(createBinary("Variable Formal Parameter", ast.I, ast.T));
    }
    
    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object obj) {
        return(createNullary("Empty Formal Parameter Sequence"));
    }
    
    public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object obj) {
        return(createBinary("Multiple Formal Parameter Sequence", ast.FP, ast.FPS));
    }
    
    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object obj) {
        return(createUnary("Single Formal Parameter Sequence", ast.FP));
    }
    
    // Actual Parameters
    public Object visitConstActualParameter(ConstActualParameter ast, Object obj) {
        return(createUnary("Constant Actual Parameter", ast.E));
    }
    
    public Object visitFuncActualParameter(FuncActualParameter ast, Object obj) {
        return(createUnary("Function Actual Parameter", ast.I));
    }
    
    public Object visitProcActualParameter(ProcActualParameter ast, Object obj) {
        return(createUnary("Procedure Actual Parameter", ast.I));
    }
    
    public Object visitVarActualParameter(VarActualParameter ast, Object obj) {
        return(createUnary("Variable Actual Parameter", ast.V));
    }
    
    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object obj) {
        return(createNullary("Empty Actual Parameter Sequence"));
    }
    
    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object obj) {
        return(createBinary("Multiple Actual Parameter Sequence", ast.AP, ast.APS));
    }
    
    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object obj) {
        return(createUnary("Single Actual Parameter Sequence", ast.AP));
    }
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc=" Type Denoters ">
    // Type Denoters
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object obj) {
        return(createNullary("any"));
    }
    
    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object obj) {
        return(createBinary("Array Type Denoter", ast.IL, ast.T));
    }
    
    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object obj) {
        return(createNullary("bool"));
    }
    
    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object obj) {
        return(createNullary("char"));
    }
    
    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object obj) {
        return(createNullary("error"));
    }
    
    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object obj) {
        return(createUnary("Simple Type Denoter", ast.I));
    }
    
    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object obj) {
        return(createNullary("int"));
    }
    
    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object obj) {
        return(createUnary("Record Type Denoter", ast.FT));
    }
    
    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object obj) {
        return(createTernary("Multiple Field Type Denoter", ast.I, ast.T, ast.FT));
    }
    
    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object obj) {
        return(createBinary("Single Field Type Denoter", ast.I, ast.T));
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Literals, Identifiers and Operators ">
    // Literals, Identifiers and Operators
    public Object visitCharacterLiteral(CharacterLiteral ast, Object obj) {
        return(createNullary(ast.spelling));
    }
    
    public Object visitIdentifier(Identifier ast, Object obj) {
        return(createNullary(ast.spelling));
    }
    
    public Object visitIntegerLiteral(IntegerLiteral ast, Object obj) {
        return(createNullary(ast.spelling));
    }
    
    public Object visitOperator(Operator ast, Object obj) {
        return(createNullary(ast.spelling));
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" Values or Variable Names ">
    // Values or Variable Names
    public Object visitDotVname(DotVname ast, Object obj) {
        return(createBinary("Dot Vname", ast.I, ast.V));
    }
    
    public Object visitSimpleVname(SimpleVname ast, Object obj) {
        return(createUnary("Simple Vname", ast.I));
    }
    
    public Object visitSubscriptVname(SubscriptVname ast, Object obj) {
        return(createBinary("Subscript Vname", ast.V, ast.E));
    }
    
    public Object visitProgram(Program ast, Object obj) {
        return(createUnary("Program", ast.C));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Tree Creation Methods ">
    
    /**
     * Creates a nullary tree node (doesn't have any children).
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @return The tree node.
     */
    public DefaultMutableTreeNode createNullary(String caption) {
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        
        return(t);
    }
    
    /**
     * Creates an unary tree node.
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1 The first children node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createUnary(String caption, AST child1) {
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode)child1.visit(this, null));
        
        return(t);
    }
    
    /**
     * Creates a binary tree node.
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1 The first children node.
     * @param child2 The second children node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createBinary(String caption, AST child1, AST child2) {
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode)child1.visit(this, null));
        t.add((DefaultMutableTreeNode)child2.visit(this, null));
        
        return(t);
    }
    
    /**
     * Creates a ternary tree node.
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1 The first children node.
     * @param child2 The second children node.
     * @param child3 The third children node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createTernary(String caption, AST child1, AST child2, AST child3) {
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode)child1.visit(this, null));
        t.add((DefaultMutableTreeNode)child2.visit(this, null));
        t.add((DefaultMutableTreeNode)child3.visit(this, null));
        
        return(t);        
    }
    
    /**
     * Creates a quaternary tree node.
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1 The first children node.
     * @param child2 The second children node.
     * @param child3 The third children node.
     * @param child4 The fourth children node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createQuaternary(String caption, AST child1, AST child2, AST child3, AST child4) {
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode)child1.visit(this, null));
        t.add((DefaultMutableTreeNode)child2.visit(this, null));
        t.add((DefaultMutableTreeNode)child3.visit(this, null));
        t.add((DefaultMutableTreeNode)child4.visit(this, null));
        
        return(t);             
    }
    
    ///////////////////////////////////////////////////////////////////////////////
    //
    //  Yosua Andres Blanco Diaz
    //  Dylan Stef Torres Walker 
    //  Adition of the new commands
    //
    ///////////////////////////////////////////////////////////////////////////////
    
    /**
     * Creates a quinary tree node.
     * @param caption The tree's caption (text to be shown when the tree is drawn).
     * @param child1 The first children node.
     * @param child2 The second children node.
     * @param child3 The third children node.
     * @param child4 The fourth children node.
     * @param child5 The fifth children node.
     * @return The tree node.
     */
    public DefaultMutableTreeNode createQuinary(String caption, AST child1, AST child2, AST child3, AST child4, AST child5) {
        DefaultMutableTreeNode t = new DefaultMutableTreeNode(caption);
        t.add((DefaultMutableTreeNode)child1.visit(this, null));
        t.add((DefaultMutableTreeNode)child2.visit(this, null));
        t.add((DefaultMutableTreeNode)child3.visit(this, null));
        t.add((DefaultMutableTreeNode)child4.visit(this, null));
        t.add((DefaultMutableTreeNode)child5.visit(this, null));
        
        return(t);             
    }
    // </editor-fold>
}
