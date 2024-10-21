package main.Visitor;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class PrivateMutableClassVariablesSmellVisitor extends VoidVisitorAdapter<Void> {

    // Check that the return signature is of type reference.
    // Open the class of the reference and begin parsing.
    // Check if the modifier is private on the class that is a reference.
    // Check if the class has a setter.

    public void visit(MethodDeclaration methodDeclaration, Void args) {
        // If the type is of some form of object
        if(methodDeclaration.getType().isReferenceType()) {
            Type type = methodDeclaration.getType();
            try {
                if(mutableObject(type)) {
                    Driver.smellyCodeFound(type.asString() + " contains a private field that is being mutated from another class" );
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean mutableObject(Type type) throws IOException {
        FileInputStream in = new FileInputStream("C:\\dev\\multipleBadCodeInstances.java");
        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(in);
            
        } finally {
            in.close();
        }
        return false;
    }
}
