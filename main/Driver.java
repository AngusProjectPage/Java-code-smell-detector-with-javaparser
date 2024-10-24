package main;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
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

//        System.out.println("1: Initialise local variables on declaration");
//        new UninitialisedVariableSmellVisitor().visit(cu, null);
//        System.out.println();
//
//        System.out.println("2: Keep assignments simple");
//        new SimpleAssignmentSmellVisitor().visit(cu, null);
//        System.out.println();
//
//        System.out.println("3: One variable per declaration");
//        new MultipleVariableDeclarationSmellVisitor().visit(cu, null);
//        System.out.println();
//
//        System.out.println("4: Limit access to instance and class variables");
//        checkLimitAccessSmells(cu);
//        System.out.println();

        // System.out.println("5: Avoid local declarations that hide declarations at higher levels");

//        System.out.println("6: Switch fall-through is commented");
//        new SwitchFallThroughSmellVisitor().visit(cu, null);
//        System.out.println();
//
//        System.out.println("7: Avoid constants in code");
//        new AvoidConstantsSmellVisitor().visit(cu, null);
//        System.out.println();

          System.out.println("8: Don't ignore caught exceptions");
          new CaughtExceptionsSmellVisitor().visit(cu, null);

//        System.out.println("9: Don't change a for loop iteration variable in the body of the loop");
//        System.out.println("10: Accessors and mutators should be appropriately named");

//        System.out.println("11: Switch default label is included");
//        new DefaultSwitchSmellVisitor().visit(cu, null);

//        System.out.println("12: Do not return references to private mutable class members");
//        new PrivateMutableClassVariablesSmellVisitor().visit(cu, null);
//        System.out.println();
//
//        System.out.println("13: Do not expose private members of an outer class from within a nested class");
//        checkOuterPrivateVariableExposedSmells(cu);
//        System.out.println();
    }

    public static void checkOuterPrivateVariableExposedSmells(CompilationUnit cu) {
        OuterPrivateVariableExposedDTO outerPrivateVariableExposedDTO = new OuterPrivateVariableExposedDTO();
        new OuterPrivateVariableExposedSmellVisitor().visit(cu, outerPrivateVariableExposedDTO);
        if(!outerPrivateVariableExposedDTO.getFieldDeclarations().isEmpty()) {
            if(!outerPrivateVariableExposedDTO.getInnerClasses().isEmpty()) {
                for(ClassOrInterfaceDeclaration ci : outerPrivateVariableExposedDTO.getInnerClasses()) {
                    smellyCodeFound(ci.getName() + " is an inner public class exposing private fields from outer class.");
                }
            }
            if(!outerPrivateVariableExposedDTO.getMethodDeclarations().isEmpty()) {
                for(MethodDeclaration md : outerPrivateVariableExposedDTO.getMethodDeclarations()) {
                    smellyCodeFound(md.getName() + " is a public method exposing private fields in it's outer class.");
                }
            }
            for(VariableDeclarator vd : outerPrivateVariableExposedDTO.getFieldDeclarations()) {
                smellyCodeFound(vd + " is a private field being exposed by inner class.");
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

