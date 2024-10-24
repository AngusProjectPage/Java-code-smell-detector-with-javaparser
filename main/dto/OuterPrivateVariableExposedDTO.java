package main.dto;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;

import java.util.ArrayList;
import java.util.List;

// Needs to be changed to look for field
public class OuterPrivateVariableExposedDTO {

    private List<VariableDeclarator> fieldDeclarations;
    private List<ClassOrInterfaceDeclaration> innerClasses;
    private List<MethodDeclaration> methodDeclarations;

    public OuterPrivateVariableExposedDTO() {
        this.fieldDeclarations = new ArrayList<>();
        this.innerClasses = new ArrayList<>();
        this.methodDeclarations = new ArrayList<>();
    }

    public void setFieldDeclarations(List<VariableDeclarator> fieldDeclarations) {
        this.fieldDeclarations.addAll(fieldDeclarations);
    }
    public void setInnerClasses(ClassOrInterfaceDeclaration innerClass) {
        this.innerClasses.add(innerClass);
    }
    public void setMethodDeclarations(MethodDeclaration methodDeclaration) { this.methodDeclarations.add(methodDeclaration);}
    public List<VariableDeclarator> getFieldDeclarations() {
        return this.fieldDeclarations;
    }
    public List<MethodDeclaration> getMethodDeclarations() {
        return this.methodDeclarations;
    }
    public List<ClassOrInterfaceDeclaration> getInnerClasses() {
        return this.innerClasses;
    }

}
