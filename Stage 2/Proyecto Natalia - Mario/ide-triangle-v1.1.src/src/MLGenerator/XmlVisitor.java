/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MLGenerator;

import Triangle.AbstractSyntaxTrees.AnyTypeDenoter;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.BinaryOperatorDeclaration;
import Triangle.AbstractSyntaxTrees.BoolTypeDenoter;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.CaseLiteralCHAR;
import Triangle.AbstractSyntaxTrees.CaseLiteralINT;
import Triangle.AbstractSyntaxTrees.CaseLiterals;
import Triangle.AbstractSyntaxTrees.CaseRangeCase;
import Triangle.AbstractSyntaxTrees.CaseWhen;
import Triangle.AbstractSyntaxTrees.Cases;
import Triangle.AbstractSyntaxTrees.CharTypeDenoter;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.ElseCase;
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
import Triangle.AbstractSyntaxTrees.InVarDecl;
import Triangle.AbstractSyntaxTrees.IntTypeDenoter;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.LocalProcFuncDeclaration;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.ProcFuncsDeclaration;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RangeVarDecl;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RecursiveDeclaration;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatForRange;
import Triangle.AbstractSyntaxTrees.RepeatForRangeUntil;
import Triangle.AbstractSyntaxTrees.RepeatForRangeWhile;
import Triangle.AbstractSyntaxTrees.RepeatIn;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.SelectCommand;
import Triangle.AbstractSyntaxTrees.SequentialCase;
import Triangle.AbstractSyntaxTrees.SequentialCaseRange;
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
import Triangle.AbstractSyntaxTrees.VarDeclarationExpression;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mario
 */
public class XmlVisitor implements Visitor {

    private FileWriter fileWriter;

    XmlVisitor(FileWriter fileWriter) {
        this.fileWriter = fileWriter;
    }

    // Commands
    public Object visitAssignCommand(AssignCommand ast, Object obj) {
        writeLineXML("<AssignCommand>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</AssignCommand>");
        return null;
    }

    public Object visitCallCommand(CallCommand ast, Object obj) {
        writeLineXML("<CallCommand>");
        ast.I.visit(this, null);
        ast.APS.visit(this, null);
        writeLineXML("</CallCommand>");
        return null;
    }

    public Object visitEmptyCommand(EmptyCommand ast, Object obj) {
        writeLineXML("<EmptyCommand/>");
        return null;
    }

    public Object visitIfCommand(IfCommand ast, Object obj) {
        writeLineXML("<IfCommand>");
        ast.E.visit(this, null);
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLineXML("</IfCommand>");
        return null;
    }

    public Object visitLetCommand(LetCommand ast, Object obj) {
        writeLineXML("<LetCommand>");
        ast.D.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</LetCommand>");
        return null;
    }

    public Object visitSequentialCommand(SequentialCommand ast, Object obj) {
        writeLineXML("<SequentialCommand>");
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLineXML("</SequentialCommand>");
        return null;
    }

    public Object visitWhileCommand(WhileCommand ast, Object obj) {
        writeLineXML("<WhileCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</WhileCommand>");
        return null;
    }

    // Expressions
    public Object visitArrayExpression(ArrayExpression ast, Object obj) {
        writeLineXML("<ArrayExpression>");
        ast.AA.visit(this, null);
        writeLineXML("</ArrayExpression>");
        return null;
    }

    public Object visitBinaryExpression(BinaryExpression ast, Object obj) {
        writeLineXML("<BinaryExpression>");
        ast.E1.visit(this, null);
        ast.O.visit(this, null);
        ast.E2.visit(this, null);
        writeLineXML("</BinaryExpression>");
        return null;
    }

    public Object visitCallExpression(CallExpression ast, Object obj) {
        writeLineXML("<CallExpression>");
        ast.I.visit(this, null);
        ast.APS.visit(this, null);
        writeLineXML("</CallExpression>");
        return null;
    }

    public Object visitCharacterExpression(CharacterExpression ast, Object obj) {
        writeLineXML("<CharacterExpression>");
        ast.CL.visit(this, null);
        writeLineXML("</CharacterExpression>");
        return null;
    }

    public Object visitEmptyExpression(EmptyExpression ast, Object obj) {
        writeLineXML("<EmptyExpression/>");
        return null;
    }

    public Object visitIfExpression(IfExpression ast, Object obj) {
        writeLineXML("<IfExpression>");
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.E3.visit(this, null);
        writeLineXML("</IfExpression>");
        return null;
    }

    public Object visitIntegerExpression(IntegerExpression ast, Object obj) {
        writeLineXML("<IntegerExpression>");
        ast.IL.visit(this, null);
        writeLineXML("</IntegerExpression>");
        return null;
    }

    public Object visitLetExpression(LetExpression ast, Object obj) {
        writeLineXML("<LetExpression>");
        ast.D.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</LetExpression>");
        return null;
    }

    public Object visitRecordExpression(RecordExpression ast, Object obj) {
        writeLineXML("<RecordExpression>");
        ast.RA.visit(this, null);
        writeLineXML("</RecordExpression>");
        return null;
    }

    public Object visitUnaryExpression(UnaryExpression ast, Object obj) {
        writeLineXML("<UnaryExpression>");
        ast.O.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</UnaryExpression>");
        return null;
    }

    public Object visitVnameExpression(VnameExpression ast, Object obj) {
        writeLineXML("<VnameExpression>");
        ast.V.visit(this, null);
        writeLineXML("</VnameExpression>");
        return null;
    }

    // Declarations
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object obj) {
        writeLineXML("<BinaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG1.visit(this, null);
        ast.ARG2.visit(this, null);
        ast.RES.visit(this, null);
        writeLineXML("</BinaryOperatorDeclaration>");
        return null;
    }
    
    // Yosua Blanco Diaz 
    @Override
    public Object visitRecursiveDeclaration(RecursiveDeclaration ast, Object o) {
        writeLineXML("<RecursiveDeclaration>");
        ast.PF.visit(this, null);
        writeLineXML("</RecursiveDeclaration>");
        return null;
    }

    public Object visitConstDeclaration(ConstDeclaration ast, Object obj) {
        writeLineXML("<ConstDeclaration>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</ConstDeclaration>");
        return null;
    }

    public Object visitFuncDeclaration(FuncDeclaration ast, Object obj) {
        writeLineXML("<FuncDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</FuncDeclaration>");
        return null;
    }

    public Object visitProcDeclaration(ProcDeclaration ast, Object obj) {
        writeLineXML("<ProcDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.C.visit(this, null);
        writeLineXML("</ProcDeclaration>");
        return null;
    }

    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object obj) {
        writeLineXML("<SequentialDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeLineXML("</SequentialDeclaration>");
        return null;
    }

    public Object visitTypeDeclaration(TypeDeclaration ast, Object obj) {
        writeLineXML("<TypeDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</TypeDeclaration>");
        return null;
    }

    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object obj) {
        writeLineXML("<UnaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG.visit(this, null);
        ast.RES.visit(this, null);
        writeLineXML("</UnaryOperatorDeclaration>");
        return null;
    }

    public Object visitVarDeclaration(VarDeclaration ast, Object obj) {
        writeLineXML("<VarDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</VarDeclaration>");
        return null;
    }
    
    // Repeat
    public Object visitRepeatForRangeWhile(RepeatForRangeWhile ast, Object o){
        writeLineXML("<RepeatForRangeWhile>");
        ast.C.visit(this, null);
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.R.visit(this, null);
        writeLineXML("</RepeatForRangeWhile>");
        return null;
    }
    public Object visitRepeatForRange(RepeatForRange ast, Object o){
        writeLineXML("<RepeatForRange>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        ast.R.visit(this, null);
        writeLineXML("</RepeatForRange>");
        return null;
    }
    public Object visitRepeatForRangeUntil(RepeatForRangeUntil ast, Object o){
        writeLineXML("<RepeatForRangeUntil>");
        ast.C.visit(this, null);
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.R.visit(this, null);
        writeLineXML("</RepeatForRangeUntil>");
        return null;
    }
    public Object visitRepeatIn(RepeatIn ast, Object o){
        writeLineXML("<RepeatIn>");
        ast.C.visit(this, null);
        ast.I.visit(this, null);
        writeLineXML("</RepeatIn>");
        return null;
    }
    public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o){
        writeLineXML("<RepeatWhileCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</RepeatWhileCommand>");
        return null;
    }
    public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o){
        writeLineXML("<RepeatUntilCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</RepeatUntilCommand>");
        return null;
    }
    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o){
        writeLineXML("<RepeatDoWhileCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</RepeatDoWhileCommand>");
        return null;
    }
    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o){
        writeLineXML("<RepeatDoUntilCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</RepeatDoUntilCommand>");
        return null;
    }
    
    public Object visitVarDeclarationExpression(VarDeclarationExpression ast, Object o){
        writeLineXML("<VarDeclarationExpression>");
        ast.E.visit(this, null);
        ast.I.visit(this, null);
        writeLineXML("</VarDeclarationExpression>");
        return null;
    }
    public Object visitProcFuncsDeclaration(ProcFuncsDeclaration ast, Object o){
        writeLineXML("<ProcFuncsDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeLineXML("</ProcFuncsDeclaration>");
        return null;
    }
    public Object visitLocalProcFuncDeclaration(LocalProcFuncDeclaration ast, Object o){
        writeLineXML("<LocalProcFuncDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeLineXML("</LocalProcFuncDeclaration>");
        return null;
    }
    public Object visitRangeVarDecl(RangeVarDecl ast, Object o){
        writeLineXML("<RangeVarDecl>");
        ast.E.visit(this, null);
        ast.I.visit(this, null);
        writeLineXML("</RangeVarDecl>");
        return null;
    }
    public Object visitInVarDecl(InVarDecl ast, Object o){
        writeLineXML("<InVarDecl>");
        ast.E.visit(this, null);
        ast.I.visit(this, null);
        writeLineXML("</InVarDecl>");
        return null;
    }

    // Array Aggregates
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object obj) {
        writeLineXML("<MultipleArrayAggregate>");
        ast.E.visit(this, null);
        ast.AA.visit(this, null);
        writeLineXML("</MultipleArrayAggregate>");
        return null;
    }

    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object obj) {
        writeLineXML("<SingleArrayAggregate>");
        ast.E.visit(this, null);
        writeLineXML("</SingleArrayAggregate>");
        return null;
    }

    // Record Aggregates
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object obj) {
        writeLineXML("<MultipleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        ast.RA.visit(this, null);
        writeLineXML("</MultipleRecordAggregate>");
        return null;
    }

    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object obj) {
        writeLineXML("<SingleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</SingleRecordAggregate>");
        return null;
    }

    // Formal Parameters
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object obj) {
        writeLineXML("<ConstFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</ConstFormalParameter>");
        return null;
    }

    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object obj) {
        writeLineXML("<FuncFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("<FuncFormalParameter>");
        return null;
    }

    public Object visitProcFormalParameter(ProcFormalParameter ast, Object obj) {
        writeLineXML("<ProcFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        writeLineXML("</ProcFormalParameter>");
        return null;
    }

    public Object visitVarFormalParameter(VarFormalParameter ast, Object obj) {
        writeLineXML("<VarFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</VarFormalParameter>");
        return null;
    }

    public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object obj) {
        writeLineXML("<EmptyFormalParameterSequence/>");
        return null;
    }

    public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object obj) {
        writeLineXML("<MultipleFormalParameterSequence>");
        ast.FP.visit(this, null);
        ast.FPS.visit(this, null);
        writeLineXML("</MultipleFormalParameterSequence>");
        return null;
    }

    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object obj) {
        writeLineXML("<SingleFormalParameterSequence>");
        ast.FP.visit(this, null);
        writeLineXML("</SingleFormalParameterSequence>");
        return null;
    }

    // Actual Parameters
    public Object visitConstActualParameter(ConstActualParameter ast, Object obj) {
        writeLineXML("<ConstActualParameter>");
        ast.E.visit(this, null);
        writeLineXML("</ConstActualParameter>");
        return null;
    }

    public Object visitFuncActualParameter(FuncActualParameter ast, Object obj) {
        writeLineXML("<FuncActualParameter>");
        ast.I.visit(this, null);
        writeLineXML("</FuncActualParameter>");
        return null;
    }

    public Object visitProcActualParameter(ProcActualParameter ast, Object obj) {
        writeLineXML("<ProcActualParameter>");
        ast.I.visit(this, null);
        writeLineXML("</ProcActualParameter>");
        return null;
    }

    public Object visitVarActualParameter(VarActualParameter ast, Object obj) {
        writeLineXML("<VarActualParameter>");
        ast.V.visit(this, null);
        writeLineXML("</VarActualParameter>");
        return null;
    }

    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object obj) {
        writeLineXML("<EmptyActualParameterSequence/>");
        return null;
    }

    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object obj) {
        writeLineXML("<MultipleActualParameterSequence>");
        ast.AP.visit(this, null);
        ast.APS.visit(this, null);
        writeLineXML("</MultipleActualParameterSequence>");
        return null;
    }

    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object obj) {
        writeLineXML("<SingleActualParameterSequence>");
        ast.AP.visit(this, null);
        writeLineXML("</SingleActualParameterSequence>");
        return null;
    }

    // Type Denoters
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object obj) {
        writeLineXML("<AnyTypeDenoter/>");
        return null;
    }

    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object obj) {
        writeLineXML("<ArrayTypeDenoter>");
        ast.IL.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</ArrayTypeDenoter>");
        return null;
    }

    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object obj) {
        writeLineXML("<BoolTypeDenoter/>");
        return null;
    }

    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object obj) {
        writeLineXML("<CharTypeDenoter/>");
        return null;
    }

    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object obj) {
        writeLineXML("<ErrorTypeDenoter/>");
        return null;
    }

    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object obj) {
        writeLineXML("<SimpleTypeDenoter>");
        ast.I.visit(this, null);
        writeLineXML("</SimpleTypeDenoter>");
        return null;
    }

    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object obj) {
        writeLineXML("<IntTypeDenoter/>");
        return null;
    }

    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object obj) {
        writeLineXML("<RecordTypeDenoter>");
        ast.FT.visit(this, null);
        writeLineXML("</RecordTypeDenoter>");
        return null;
    }

    public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object obj) {
        writeLineXML("<MultipleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        ast.FT.visit(this, null);
        writeLineXML("</MultipleFieldTypeDenoter>");
        return null;
    }

    public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object obj) {
        writeLineXML("<SingleFieldTypeDenoter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLineXML("</SingleFieldTypeDenoter>");
        return null;
    }

    // Literals, Identifiers and Operators
    public Object visitCharacterLiteral(CharacterLiteral ast, Object obj) {
        writeLineXML("<CharacterLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitIdentifier(Identifier ast, Object obj) {
        writeLineXML("<Identifier value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitIntegerLiteral(IntegerLiteral ast, Object obj) {
        writeLineXML("<IntegerLiteral value=\"" + ast.spelling + "\"/>");
        return null;
    }

    public Object visitOperator(Operator ast, Object obj) {
        writeLineXML("<Operator value=\"" + transformOperator(ast.spelling) + "\"/>");
        return null;
    }

    // Value-or-variable names
    public Object visitDotVname(DotVname ast, Object obj) {
        writeLineXML("<DotVname>");
        ast.V.visit(this, null);
        ast.I.visit(this, null);
        writeLineXML("</DotVname>");
        return null;
    }

    public Object visitSimpleVname(SimpleVname ast, Object obj) {
        writeLineXML("<SimpleVname>");
        ast.I.visit(this, null);
        writeLineXML("</SimpleVname>");
        return null;
    }

    public Object visitSubscriptVname(SubscriptVname ast, Object obj) {
        writeLineXML("<SubscriptVname>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        writeLineXML("</SubscriptVname>");
        return null;
    }

    // Programs
    public Object visitProgram(Program ast, Object obj) {
        writeLineXML("<Program>");
        ast.C.visit(this, null);
        writeLineXML("</Program>");
        return null;
    }

    private void writeLineXML(String line) {
        try {
            fileWriter.write(line);
            fileWriter.write('\n');
        } catch (IOException e) {
            System.err.println("Error while writing file for print the AST");
            e.printStackTrace();
        }
    }

    /*
     * Convert the characters "<" & "<=" to their equivalents in html
     */
    private String transformOperator(String operator) {
        if (operator.compareTo("<") == 0) {
            return "&lt;";
        } else if (operator.compareTo("<=") == 0) {
            return "&lt;=";
        } else {
            return operator;
        }
    }

    @Override
    public Object visitSelectCommand(SelectCommand ast, Object o) {
        writeLineXML("<SelectCommand>");
        ast.EXP.visit(this, null);
        ast.COM.visit(this, null);
        writeLineXML("</SelectCommand>");
        return null;
    }

    @Override
    public Object visitCases(Cases ast, Object o) {
        writeLineXML("<Cases>");
        if(ast.CASE2 == null){
            ast.CASE1.visit(this, null);
        }
        else{
            ast.CASE1.visit(this, null);
            ast.CASE2.visit(this, null);
        }
        writeLineXML("</Cases>");
        return null;
    }

    @Override
    public Object visitElseCase(ElseCase ast, Object o) {
        writeLineXML("<ElseCase>");
        ast.COM.visit(this, null);
        writeLineXML("</ElseCase>");
        return null;
    }

    @Override
    public Object visitSequentialCase(SequentialCase ast, Object o) {
        writeLineXML("<SequentialCase>");
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLineXML("</SequentialCase>");
        return null;
    }

    @Override
    public Object visitCaseWhen(CaseWhen ast, Object o) {
        writeLineXML("<CaseWhen>");
        ast.CASELIT.visit(this, null);
        ast.COM.visit(this, null);
        writeLineXML("</CaseWhen>");
        return null;
    }

    @Override
    public Object visitCaseLiterals(CaseLiterals ast, Object o) {
        writeLineXML("<CaseLiterals>");
        ast.CASERANGE.visit(this, null);
        writeLineXML("</CaseLiterals>");
        return null;
    }

    @Override
    public Object visitCaseRangeCase(CaseRangeCase ast, Object o) {
        writeLineXML("<CaseRangeCase>");
        if(ast.CASELIT2 == null){
            ast.CASELIT.visit(this, null);
        }
        else{
            ast.CASELIT.visit(this, null);
            ast.CASELIT2.visit(this, null);
        }
        writeLineXML("</CaseRangeCase>");
        return null;
    }

    @Override
    public Object visitSequentialCaseRange(SequentialCaseRange ast, Object o) {
        writeLineXML("<SequentialCaseRange>");
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLineXML("</SequentialCaseRange>");
        return null;
    }

    @Override
    public Object visitCaseLiteralCHAR(CaseLiteralCHAR ast, Object o) {
        writeLineXML("<CaseLiteralCHAR>");
        ast.CHARLIT.visit(this, null);
        writeLineXML("</CaseLiteralCHAR>");
        return null;
    }

    @Override
    public Object visitCaseLiteralINT(CaseLiteralINT ast, Object o) {
        writeLineXML("<CaseLiteralINT>");
        ast.INTLIT.visit(this, null);
        writeLineXML("</CaseLiteralINT>");
        return null;
    }

}
