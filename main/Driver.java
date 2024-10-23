package main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import main.Visitor.*;
import main.dto.LimitAccessSmellDTO;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import main.dto.OuterPrivateVariableExposedDTO;

import java.io.FileInputStream;

public class Driver {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("C:\\dev\\multipleBadCodeInstances.java");
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
//        new PrivateMutableClassVariablesSmellVisitor().visit(cu, null);
          checkOuterPrivateVariableExposedSmells(cu);
    }

    public static void checkOuterPrivateVariableExposedSmells(CompilationUnit cu) {
        OuterPrivateVariableExposedDTO outerPrivateVariableExposedDTO = new OuterPrivateVariableExposedDTO();
        new OuterPrivateVariableExposedSmellVisitor().visit(cu, outerPrivateVariableExposedDTO);
        if(!outerPrivateVariableExposedDTO.getFieldDeclarations().isEmpty()) {
            if(!outerPrivateVariableExposedDTO.getInnerClasses().isEmpty()) {
                for(ClassOrInterfaceDeclaration ci : outerPrivateVariableExposedDTO.getInnerClasses()) {
                    System.out.println(ci.getName() + " is an inner public class exposing private fields from outer class");
                }
            }
            if(!outerPrivateVariableExposedDTO.getMethodDeclarations().isEmpty()) {
                for(MethodDeclaration md : outerPrivateVariableExposedDTO.getMethodDeclarations()) {
                    System.out.println(md.getName() + " is a public method exposing private fields in it's outer class");
                }
            }
            for(VariableDeclarator vd : outerPrivateVariableExposedDTO.getFieldDeclarations()) {
                System.out.println(vd + " is a private field being exposed by inner class");
            }
        }
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

