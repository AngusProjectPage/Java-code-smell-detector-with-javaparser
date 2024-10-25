package main.Visitor;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;
import main.dto.PrivateVariableAndSetterDTO;

import java.io.FileInputStream;
import java.io.IOException;

// Class first checks for reference types and if found will then scan the referenced class for private fields that are then later mutaed.
public class PrivateMutableClassVariablesSmellVisitor extends VoidVisitorAdapter<Void> {

    public class PrivateVariableAndSetterVisitor extends VoidVisitorAdapter<PrivateVariableAndSetterDTO> {
        @Override
        public void visit(FieldDeclaration n, PrivateVariableAndSetterDTO privateVariableAndSetterDTO) {
            super.visit(n, privateVariableAndSetterDTO);
            for(Modifier mod : n.getModifiers()) {
                if(mod.getKeyword() != Modifier.publicModifier().getKeyword()) {
                    privateVariableAndSetterDTO.setVariables(n.getVariables());
                }
            }
        }

        @Override
        public void visit(AssignExpr n, PrivateVariableAndSetterDTO privateVariableAndSetterDTO) {
            super.visit(n, privateVariableAndSetterDTO);
            privateVariableAndSetterDTO.setAssignment(n);
        }
    }

    public void visit(MethodDeclaration methodDeclaration, Void args) {
        super.visit(methodDeclaration, args);
        // If the type is of some form of object
        if(methodDeclaration.getType().isReferenceType()) {
            Type type = methodDeclaration.getType();
            try {
                String classname = methodDeclaration.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString();
                mutableObject(type, classname);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void mutableObject(Type type, String classname) throws IOException {
        try {
            FileInputStream in = new FileInputStream("C:\\dev\\" + type.asString() + ".java");
            CompilationUnit cu;
            cu = StaticJavaParser.parse(in);
            PrivateVariableAndSetterDTO privateVariableAndSetterDTO = new PrivateVariableAndSetterDTO();
            new PrivateVariableAndSetterVisitor().visit(cu, privateVariableAndSetterDTO);
            for(AssignExpr ae : privateVariableAndSetterDTO.getAssignments()) {
                for(VariableDeclarator vd : privateVariableAndSetterDTO.getVariables()) {
                    if(vd.getName().asString().equals(ae.getTarget().toString()) || ("this." + vd.getName().asString()).equals(ae.getTarget().toString())) {
                        Driver.smellyCodeFound(vd.getName().asString() + " is a mutable private field within class " + type + " referenced by class " + classname + " that is mutated in assignment \"" + ae + "\"");
                    }
                }
            }
            in.close();
        } catch(IOException e) {
            System.out.println("Reference not found " + e.getMessage());
        }
    }
}
