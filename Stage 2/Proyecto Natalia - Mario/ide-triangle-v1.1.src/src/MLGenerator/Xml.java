/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MLGenerator;

import Triangle.AbstractSyntaxTrees.Program;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mario
 */
public class Xml {
    
    private String fileName;

    public Xml(String fileName) {
        this.fileName = fileName.replace(".tri", ".xml");
    }
    public void write(Program ast) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);

            //Xml header
            fileWriter.write("<?xml version=\"1.0\" standalone=\"yes\"?>\n");

            XmlVisitor layout = new XmlVisitor(fileWriter);
            ast.visit(layout, null);

            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error while creating file for print the AST");
            e.printStackTrace();
        }
    }
    
}
