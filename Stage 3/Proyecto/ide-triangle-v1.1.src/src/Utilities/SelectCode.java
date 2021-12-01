/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;
import Triangle.AbstractSyntaxTrees.CaseWhen;
import Triangle.CodeGenerator.Frame;

/**
 *
 * @author johel
 */
public class SelectCode {
    public Frame frame;
    public int address;
    public CaseWhen ast;
    public SelectCode(Frame pFrame, Integer pAddress){
        frame = pFrame;
        address = pAddress;
    }
}