/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MLGenerator;

import Triangle.SyntacticAnalyzer.Scanner;
import Triangle.SyntacticAnalyzer.Token;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author USUARIO
 */
public class Html {

    private String fileName;

    // Pruebas en el generador HTML
    private Scanner lexicalAnalyser;
    private Token currentToken;

    private String buffer;  // html var
    private String buffercomment; // html var
    private String spaces;  // html var 
    private int cont; // html var

    public Html(String fileName, Scanner lexer) {
        this.fileName = fileName.replace(".tri", ".html");
        lexicalAnalyser = lexer;
        buffer = "";
        buffercomment = "";
        spaces = "";
        cont = 0;
    }

    public String getBuffer() {
        return buffer;
    }

    public boolean checkLexic() {
        currentToken = lexicalAnalyser.scanHTML();
        boolean hasLexicError = false;
        while (currentToken.kind != Token.EOT) {
            if (currentToken.kind == Token.ERROR) {
                hasLexicError = true;
                break;
            }
            switch (currentToken.kind) {
                case Token.CHARLITERAL:
                case Token.INTLITERAL:
                    addLiteral(currentToken.spelling);
                    break;
                case Token.IDENTIFIER:
                    if (currentToken.spelling.contains("!")) {
                        addComment(currentToken.spelling);
                        htmljmpLine();
                    } else if ("\n".equals(currentToken.spelling)) {
                        htmljmpLine();
                    } else if ("\r".equals(currentToken.spelling)) {
                        addSpace(currentToken.spelling);
                    } else if (" ".equals(currentToken.spelling)) {
                        spaces += "&nbsp;";
                    } else if ("\t".equals(currentToken.spelling)) {
                        addTab();
                    } else {
                        addIdentifier(currentToken.spelling);
                    }   break;
                default:
                    addIdentifier(currentToken.spelling);
                    break;
            }
            currentToken = lexicalAnalyser.scanHTML();
        }
        
        return hasLexicError;
        

    }

    public void write(String data) {
        // Prepare the file to write
        try {
            FileWriter fileWriter = new FileWriter(fileName);

            //HTML header
            fileWriter.write("<html><body>" + data + "</body></html>");
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("Error while creating file for print the AST");
            e.printStackTrace();
        }
    }

    private void htmljmpLine() {
        buffer += "<br/>";
        spaces = "";
        cont++;
    }

    private void addSpace(String spelling) {
        buffer += "<FONT FACE=COURIER \"monospace\" SIZE =5 COLOR=#000000>" + spaces + spelling + "</FONT>";
        spaces = "";
        cont++;
    }

    private void addTab() {
        buffer += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    }

    private void addIdentifier(String token) {
        if (Token.isReservedWord(token)) {
            addReservedWord(token);
        } else {
            buffer += "<FONT FACE=COURIER \"monospace\" SIZE =5 COLOR=#000000>" + spaces + token + "</FONT>";
            spaces = "";
            cont++;
        }
    }

    private void addReservedWord(String token) {
        buffer += "<FONT FACE=COURIER \"monospace\" SIZE =5 COLOR=#000000><strong>" + spaces + token + "</strong></FONT>";
        spaces = "";
        cont++;
    }

    private void addLiteral(String spelling) {
        buffer += "<FONT FACE=COURIER \"monospace\" SIZE =5 COLOR=#0000FF>" + spaces + spelling + "</FONT>";
        spaces = "";
        cont++;
    }

    private void addComment(String comment) {
        buffer += "<FONT FACE=COURIER \"monospace\" SIZE =5 COLOR=#008000>" + spaces + comment + "</FONT>";
        buffercomment = "";
        spaces = "";
        cont++;
    }

}
