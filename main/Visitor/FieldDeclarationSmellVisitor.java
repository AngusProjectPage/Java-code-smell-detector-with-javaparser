package main.Visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

import java.util.HashMap;
import java.util.Map;

public class FieldDeclarationSmellVisitor extends VoidVisitorAdapter<Object> {

    private Map<String, Node> declaredVariables = new HashMap<>();

    @Override
    public void visit(FieldDeclaration n, Object args) {
        super.visit(n, args);
        for (VariableDeclarator v : n.getVariables()) {
            checkForShadowing(v);
        }
    }

    @Override
    public void visit(MethodDeclaration n, Object args) {
        super.visit(n, args);
        for (Node child : n.getChildNodes()) {
            if (child instanceof VariableDeclarator) {
                VariableDeclarator v = (VariableDeclarator) child;
                checkForShadowing(v);
            }
        }
    }

    private void checkForShadowing(VariableDeclarator v) {
        String name = v.getNameAsString();
        if (declaredVariables.containsKey(name)) {
            System.out.println("Variable '" + name + "' hides a declaration at a higher level");
        } else {
            declaredVariables.put(name, v);
        }
    }
}
