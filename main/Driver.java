package main;

import main.Visitor.*;
import main.dto.LimitAccessSmellDTO;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import java.io.FileInputStream;

public class Driver {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("C:\\dev\\mutableReferenceExposer.java");
        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(in);
        } finally {
            in.close();
        }
//        new UninitialisedVariableSmellVisitor().visit(cu, null);
//        new MultipleVariableDeclarationSmellVisitor().visit(cu, null);
//        new SimpleAssignmentSmellVisitor().visit(cu, null);
//        new AvoidConstantsSmellVisitor().visit(cu, null);
//        checkLimitAccessSmells(cu);
//        new SwitchFallThroughSmellVisitor().visit(cu, null);
        new PrivateMutableClassVariablesSmellVisitor().visit(cu, null);
    }

    public static void checkLimitAccessSmells(CompilationUnit cu) {
        LimitAccessSmellDTO limitAccessSmellDTO = new LimitAccessSmellDTO();
        new LimitAccessSmellVisitor().visit(cu, limitAccessSmellDTO);
        if(limitAccessSmellDTO.methodFound()) {
            for(VariableDeclarator n : limitAccessSmellDTO.getVariables()) {
                smellyCodeFound("Field " + n.getType().asString() + " " + n.getNameAsString() + " is of scope public within a class that contains methods");
            }
        }
    }
    /**
     * Report smelly code with a message explaining why.
     */
    public static void smellyCodeFound(String message) {
        System.out.println("Smelly Code Found! Reason: " + message);
    }

}

