package main.Visitor;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;
import main.dto.PrivateVariableAndSetterDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

public class PrivateMutableClassVariablesSmellVisitor extends VoidVisitorAdapter<Void> {

    // This is a class that checks for the variable that was a reference.
    public class PrivateVariableAndSetterVisitor extends VoidVisitorAdapter<PrivateVariableAndSetterDTO> {

        @Override
        public void visit(VariableDeclarationExpr n, PrivateVariableAndSetterDTO privateVariableAndSetterDTO) {
            for(Modifier mod : n.getModifiers()) {
                if(mod.getKeyword() == Modifier.privateModifier().getKeyword()) {

                }
            }
        }
    }

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
            PrivateVariableAndSetterDTO privateVariableAndSetterDTO = new PrivateVariableAndSetterDTO();
            new PrivateVariableAndSetterVisitor().visit(cu, privateVariableAndSetterDTO);
            if(privateVariableAndSetterDTO.isMutable()) {
                return true;
            }
        } finally {
            in.close();
        }
        return false;
    }
}
