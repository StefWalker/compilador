/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.SyntacticAnalyzer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
import Triangle.AbstractSyntaxTrees.LocalDeclaration;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.ProcFuncs;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RecursiveDeclaration;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatForDoCommand;
import Triangle.AbstractSyntaxTrees.RepeatForInCommand;
import Triangle.AbstractSyntaxTrees.RepeatForUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatForWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialProcFuncs;
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
import Triangle.AbstractSyntaxTrees.VarBecomesDeclaration;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Visitor;
import Triangle.AbstractSyntaxTrees.Vname;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.WhileCommand;
/**
 *
 * @author Yosua Blanco Diaz
 */
public class Xml implements Visitor {
    
    public Xml(String fileURL){
        setFileWriter(fileURL);
    }

    @Override
    public Object visitAssignCommand(AssignCommand ast, Object o) {
        writeLine("<AssignCommand>");
        ast.V.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</AssignCommand>");
        return null;
    }

    @Override
    public Object visitCallCommand(CallCommand ast, Object o) {
        writeLine("<CallCommand>");
        ast.I.visit(this, null);
        ast.APS.visit(this, null);
        writeLine("</CallCommand>");
        return null;
    }

    @Override
    public Object visitEmptyCommand(EmptyCommand ast, Object o) {
        writeLine("<EmptyCommand/>");
        return null;
    }

    @Override
    public Object visitIfCommand(IfCommand ast, Object o) {
        writeLine("<IfCommand>");
        ast.E.visit(this, null);
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLine("</IfCommand>");
        return null;
    }

    @Override
    public Object visitLetCommand(LetCommand ast, Object o) {
        writeLine("<LetCommand>");
        ast.D.visit(this, null);
        ast.C.visit(this, null);
        writeLine("</LetCommand>");
        return null;
    }

    @Override
    public Object visitSequentialCommand(SequentialCommand ast, Object o) {
        writeLine("<SequentialCommand>");
        ast.C1.visit(this, null);
        ast.C2.visit(this, null);
        writeLine("</SequentialCommand>");
        return null;
    }

    @Override
    public Object visitWhileCommand(WhileCommand ast, Object o) {
        writeLine("<WhileCommand>");
        ast.E.visit(this, null);
        ast.C.visit(this, null);
        writeLine("</WhileCommand>");
        return null;
    }

    @Override
    public Object visitRepeatWhileCommand(RepeatWhileCommand ast, Object o) {
        writeLine("<RepeatWhileCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</RepeatWhileCommand>");
        return null;
    }

    @Override
    public Object visitRepeatUntilCommand(RepeatUntilCommand ast, Object o) {
        writeLine("<RepeatUntilCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</RepeatUntilCommand>");
        return null;
    }

    @Override
    public Object visitRepeatDoWhileCommand(RepeatDoWhileCommand ast, Object o) {
        writeLine("<RepeatDoWhileCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</RepeatDoWhileCommand>");
        return null;
    }

    @Override
    public Object visitRepeatDoUntilCommand(RepeatDoUntilCommand ast, Object o) {
        writeLine("<RepeatDoUntilCommand>");
        ast.C.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</RepeatDoUntilCommand>");
        return null;
    }

    @Override
    public Object visitRepeatForDoCommand(RepeatForDoCommand ast, Object o) {
        writeLine("<RepeatForDoCommand>");
        ast.C.visit(this, null);
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.I.visit(this, null);
        writeLine("</RepeatForDoCommand>");
        return null;
    }

    @Override
    public Object visitRepeatForWhileCommand(RepeatForWhileCommand ast, Object o) {
        writeLine("<RepeatForDoCommand>");
        ast.C.visit(this, null);
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.E3.visit(this, null);
        ast.I.visit(this, null);
        writeLine("</RepeatForDoCommand>");
        return null;
    }

    @Override
    public Object visitRepeatForUntilCommand(RepeatForUntilCommand ast, Object o) {
        writeLine("<RepeatForUntilCommand>");
        ast.I.visit(this, null);
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.E3.visit(this, null);
        ast.C.visit(this, null);
        writeLine("</RepeatForUntilCommand>");
        return null;
    }

    @Override
    public Object visitRepeatForInCommand(RepeatForInCommand ast, Object o) {
        writeLine("<RepeatForInCommand>");
        ast.C.visit(this, null);
        ast.E1.visit(this, null);
        ast.I.visit(this, null);
        writeLine("</RepeatForInCommand>");
        return null;
    }

    
    public Object visitProcFuncs(ProcFuncs ast, Object o) {
        writeLine("<ProcFuncs/>");
        return null;
    }


    @Override
    public Object visitVarBecomesDeclaration(VarBecomesDeclaration ast, Object o) {
        writeLine("<VarBecomesDeclaration>");
        ast.E.visit(this, null);
        ast.I.visit(this, null);
        writeLine("</VarBecomesDeclaration>");
        return null;
    }

    @Override
    public Object visitArrayExpression(ArrayExpression ast, Object o) {
        writeLine("<ArrayExpression>");
        ast.AA.visit(this, null);
        writeLine("</ArrayExpression>");
        return null;
    }

    @Override
    public Object visitBinaryExpression(BinaryExpression ast, Object o) {
        writeLine("<BinaryExpression>");
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.O.visit(this, null);
        writeLine("</BinaryExpression>");
        return null;
    }

    @Override
    public Object visitCallExpression(CallExpression ast, Object o) {
      writeLine("<CallExpression>");
      ast.I.visit(this, null);
      ast.APS.visit(this, null);
      writeLine("</CallExpression>");
      return null;
    }

    @Override
    public Object visitCharacterExpression(CharacterExpression ast, Object o) {
        writeLine("<CharacterExpression>");
        ast.CL.visit(this, null);
        writeLine("</CharacterExpression>");
        return null;
    }

    @Override
    public Object visitEmptyExpression(EmptyExpression ast, Object o) {
        writeLine("<EmptyExpression/>");
        return null;    
    }

    @Override
    public Object visitIfExpression(IfExpression ast, Object o) {
         writeLine("<IfExpression>");
        ast.E1.visit(this, null);
        ast.E2.visit(this, null);
        ast.E3.visit(this, null);
        writeLine("</IfExpression>");
        return null;
    }

    @Override
    public Object visitIntegerExpression(IntegerExpression ast, Object o) {
        writeLine("<IntegerExpression>");
        ast.IL.visit(this, null);
        writeLine("</IntegerExpression>");
        return null;
    }

    @Override
    public Object visitLetExpression(LetExpression ast, Object o) {
        writeLine("<LetExpression>");
        ast.D.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</LetExpression>");
        return null;
    }

    @Override
    public Object visitRecordExpression(RecordExpression ast, Object o) {
        writeLine("<RecordExpression>");
        ast.RA.visit(this, null);
        writeLine("</RecordExpression>");
        return null;
    }

    @Override
    public Object visitUnaryExpression(UnaryExpression ast, Object o) {
        writeLine("<UnaryExpression>");
        ast.O.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</UnaryExpression>");
        return null;
    }

    @Override
    public Object visitVnameExpression(VnameExpression ast, Object o) {
        writeLine("<VarNameExpression>");
        ast.V.visit(this, null);
        writeLine("</VarNameExpression>");
        return null;
    }

    @Override
    public Object visitBinaryOperatorDeclaration(BinaryOperatorDeclaration ast, Object o) {
        writeLine("<BinaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG1.visit(this, null);
        ast.ARG2.visit(this, null);
        ast.RES.visit(this, null);
        writeLine("</BinaryOperatorDeclaration>");
        return null;
    }

    @Override
    public Object visitConstDeclaration(ConstDeclaration ast, Object o) {
        writeLine("<ConstDeclaration>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</ConstDeclaration>");
        return null;
    }

    @Override
    public Object visitFuncDeclaration(FuncDeclaration ast, Object o) {
        writeLine("<FuncDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</FuncDeclaration>");
        return null;
    }

    @Override
    public Object visitProcDeclaration(ProcDeclaration ast, Object o) {
        writeLine("<ProcDeclaration>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.C.visit(this, null);
        writeLine("</ProcDeclaration>");
        return null;
    }

    @Override
    public Object visitSequentialDeclaration(SequentialDeclaration ast, Object o) {
        writeLine("<SequentialDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeLine("</SequentialDeclaration>");
        return null;
    }

    @Override
    public Object visitTypeDeclaration(TypeDeclaration ast, Object o) {
         writeLine("<TypeDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLine("</TypeDeclaration>");
        return null;
    }

    @Override
    public Object visitUnaryOperatorDeclaration(UnaryOperatorDeclaration ast, Object o) {
         writeLine("<UnaryOperatorDeclaration>");
        ast.O.visit(this, null);
        ast.ARG.visit(this, null);
        ast.RES.visit(this, null);
        writeLine("</UnaryOperatorDeclaration>");
        return null;
    }

    @Override
    public Object visitVarDeclaration(VarDeclaration ast, Object o) {
        writeLine("<VarDeclaration>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLine("</VarDeclaration>");
        return null;
    }

    @Override
    public Object visitMultipleArrayAggregate(MultipleArrayAggregate ast, Object o) {
        writeLine("<MultipleArrayAggregate>");
        ast.E.visit(this, null);
        ast.AA.visit(this, null);
        writeLine("</MultipleArrayAggregate>");
        return null;
    }

    @Override
    public Object visitSingleArrayAggregate(SingleArrayAggregate ast, Object o) {
        writeLine("<SingleArrayAggregate>");
        ast.E.visit(this, null);
        writeLine("</SingleArrayAggregate>");
        return null;
    }

    @Override
    public Object visitMultipleRecordAggregate(MultipleRecordAggregate ast, Object o) {
        writeLine("<MultipleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        ast.RA.visit(this, null);
        writeLine("</MultipleRecordAggregate>");
        return null;
    }

    @Override
    public Object visitSingleRecordAggregate(SingleRecordAggregate ast, Object o) {
        writeLine("<SingleRecordAggregate>");
        ast.I.visit(this, null);
        ast.E.visit(this, null);
        writeLine("</SingleRecordAggregate>");
        return null;
    }

    @Override
    public Object visitConstFormalParameter(ConstFormalParameter ast, Object o) {
        writeLine("<ConstFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLine("</ConstFormalParameter>");
        return null;
    }

    @Override
    public Object visitFuncFormalParameter(FuncFormalParameter ast, Object o) {
        writeLine("<FuncFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        ast.T.visit(this, null);
        writeLine("</FuncFormalParameter>");
        return null;
    }

    @Override
    public Object visitProcFormalParameter(ProcFormalParameter ast, Object o) {
        writeLine("<ProcFormalParameter>");
        ast.I.visit(this, null);
        ast.FPS.visit(this, null);
        writeLine("</ProcFormalParameter>");
        return null;
    }

    @Override
    public Object visitVarFormalParameter(VarFormalParameter ast, Object o) {
        writeLine("<VarFormalParameter>");
        ast.I.visit(this, null);
        ast.T.visit(this, null);
        writeLine("</VarFormalParameter>");
        return null;
      }

      @Override
      public Object visitEmptyFormalParameterSequence(EmptyFormalParameterSequence ast, Object o) {
        writeLine("<EmptyFormalParameterSequence/>");
        return null;
      }

      @Override
      public Object visitMultipleFormalParameterSequence(MultipleFormalParameterSequence ast, Object o) {
        writeLine("<MultipleFormalParameterSequence>");
        ast.FP.visit(this, null);
        ast.FPS.visit(this, null);
        writeLine("</MultipleFormalParameterSequence>");
        return null;
      }
      
   @Override
    public Object visitSingleFormalParameterSequence(SingleFormalParameterSequence ast, Object o) {
      writeLine("<SingleFormalParameterSequence>");
      ast.FP.visit(this, null);
      writeLine("</SingleFormalParameterSequence>");
      return null;
    }

    @Override
    public Object visitConstActualParameter(ConstActualParameter ast, Object o) {
      writeLine("<ConstActualParameter>");
      ast.E.visit(this, null);
      writeLine("</ConstActualParameter>");
      return null;
    }

    @Override
    public Object visitFuncActualParameter(FuncActualParameter ast, Object o) {
      writeLine("<FuncActualParameter>");
      ast.I.visit(this, null);
      writeLine("</FuncActualParameter>");
      return null;
    }

    @Override
    public Object visitProcActualParameter(ProcActualParameter ast, Object o) {
      writeLine("<ProcActualParameter>");
      ast.I.visit(this, null);
      writeLine("</ProcActualParameter>");
      return null;
    }

    @Override
    public Object visitVarActualParameter(VarActualParameter ast, Object o) {
      writeLine("<VarActualParameter>");
      ast.V.visit(this, null);
      writeLine("</VarActualParameter>");
      return null;
    }

    @Override
    public Object visitEmptyActualParameterSequence(EmptyActualParameterSequence ast, Object o) {
      writeLine("<EmptyActualParameterSequence/>");
      return null;
    }

    @Override
    public Object visitMultipleActualParameterSequence(MultipleActualParameterSequence ast, Object o) {
      writeLine("<MultipleActualParameterSequence>");
      ast.AP.visit(this, null);
      ast.APS.visit(this, null);
      writeLine("</MultipleActualParameterSequence>");
      return null;
    }

    @Override
    public Object visitSingleActualParameterSequence(SingleActualParameterSequence ast, Object o) {
      writeLine("<SingleActualParameterSequence>");
      ast.AP.visit(this, null);
      writeLine("</SingleActualParameterSequence>");
      return null;
    }

    @Override
    public Object visitAnyTypeDenoter(AnyTypeDenoter ast, Object o) {
      writeLine("<AnyTypeDenoter/>");
      return null;
    }

    @Override
    public Object visitArrayTypeDenoter(ArrayTypeDenoter ast, Object o) {
      writeLine("<RecordTypeDenoter>");
      ast.IL.visit(this, null);
      ast.T.visit(this, null);
      writeLine("</RecordTypeDenoter>");
      return null;
    }

    @Override
    public Object visitBoolTypeDenoter(BoolTypeDenoter ast, Object o) {
      writeLine("<BoolTypeDenoter/>");
      return null;
    }

    @Override
    public Object visitCharTypeDenoter(CharTypeDenoter ast, Object o) {
      writeLine("<CharTypeDenoter/>");
      return null;
    }

    @Override
    public Object visitErrorTypeDenoter(ErrorTypeDenoter ast, Object o) {
      writeLine("<ErrorTypeDenoter/>");
      return null;
    }

    @Override
    public Object visitSimpleTypeDenoter(SimpleTypeDenoter ast, Object o) {
      writeLine("<RecordTypeDenoter>");
      ast.I.visit(this, null);
      writeLine("</RecordTypeDenoter>");
      return null;
    }

    @Override
    public Object visitIntTypeDenoter(IntTypeDenoter ast, Object o) {
      writeLine("<IntTypeDenoter/>");
      return null;
    }

    @Override
    public Object visitRecordTypeDenoter(RecordTypeDenoter ast, Object o) {
      writeLine("<RecordTypeDenoter>");
      ast.FT.visit(this, null);
      writeLine("</RecordTypeDenoter>");
      return null;
    }

     @Override
  public Object visitMultipleFieldTypeDenoter(MultipleFieldTypeDenoter ast, Object o) {
    writeLine("<MultipleFieldTypeDenoter>");
    ast.I.visit(this, null);
    ast.T.visit(this, null);
    ast.FT.visit(this, null);
    writeLine("</MultipleFieldTypeDenoter>");
    return null;
  }

  @Override
  public Object visitSingleFieldTypeDenoter(SingleFieldTypeDenoter ast, Object o) {
    writeLine("<SingleFieldTypeDenoter>");
    ast.I.visit(this, null);
    ast.T.visit(this, null);
    writeLine("</SingleFieldTypeDenoter>");
    return null;
  }

  @Override
  public Object visitCharacterLiteral(CharacterLiteral ast, Object o) {
    writeLine("<Identifier value=\""+ast.spelling+"\"/>");
    return null;
  }

  @Override
  public Object visitIdentifier(Identifier ast, Object o) {
    writeLine("<IntegerLiteral value=\""+ast.spelling+"\"/>");
    return null;
  }

  @Override
  public Object visitIntegerLiteral(IntegerLiteral ast, Object o) {
    writeLine("<IntegerLiteral value=\""+ast.spelling+"\"/>");
    return null;
  }

  @Override
  public Object visitOperator(Operator ast, Object o) {
    writeLine("<Operator value=\""+ast.spelling+"\"/>");
    return null;
  }
  
  @Override
  public Object visitProgram(Program ast, Object o) {
    writeLine("<Program>");
    ast.C.visit(this, null);
    writeLine("</Program>");
    return null;
  }
    
    
    

    @Override
    public Object visitSequentialProcFuncs(SequentialProcFuncs ast, Object o) {
        writeLine("<SequentialProcFuncs>");
        ast.PF1.visit(this, null);
        ast.PF2.visit(this, null);
        writeLine("</SequentialProcFuncs>");
        return null;
    }


    @Override
    public Object visitRecursiveDeclaration(RecursiveDeclaration ast, Object o) {
        writeLine("<RecursiveDeclaration>");
        ast.PF.visit(this, null);
        writeLine("</RecursiveDeclaration>");
        return null;
    }

    @Override
    public Object visitLocalDeclaration(LocalDeclaration ast, Object o) {
        writeLine("<LocalDeclaration>");
        ast.D1.visit(this, null);
        ast.D2.visit(this, null);
        writeLine("</LocalDeclaration>");
        return null;
    }

    public Object visitVname(Vname ast, Object o) {
      writeLine("<Vname/>");
      return null;
    }

    @Override
    public Object visitSimpleVname(SimpleVname ast, Object o) {
       writeLine("<SimpleVname>");
        ast.I.visit(this, null);
        writeLine("</SimpleVname>");
        return null;
    }

    @Override
    public Object visitSubscriptVname(SubscriptVname ast, Object o) {
        writeLine("<SubscriptVname>");
        ast.E.visit(this, null);
        ast.V.visit(this, null);
        writeLine("</SubscriptVname>");
        return null;
    }
    
    @Override
    public Object visitDotVname(DotVname ast, Object o) {
        writeLine("<DotVname>");
        ast.I.visit(this, null);
        ast.V.visit(this, null);
        writeLine("</DotVname>");
        return null;
    }
  //File functions
    public void writeLine(String text){
        try{
          fileWriter.write(text+"\n");
        }catch(IOException e){
          System.out.println(e.getMessage());
        }
      }
    
    private void setFileWriter(String fileURL){
        try {
          fileWriter = new FileWriter(new File(fileURL));
          fileWriter.write(HEADER);
          this.fileURL = fileURL;
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
    }
    
    public void end(){
        try {
            fileWriter.close();
            System.out.println("Archivo Xml generado en el siguiente directorio: " + fileURL + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
          }
        }
    
     
    FileWriter fileWriter;
    final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private String fileURL;

    
}
