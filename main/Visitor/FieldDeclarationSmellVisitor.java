package main.Visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

import java.util.HashMap;
import java.util.Map;

public class FieldDeclarationSmellVisitor extends VoidVisitorAdapter<Void> {

    private Map<String, Node> declaredVariables = new HashMap<>();

    @Override
    public void visit(FieldDeclaration n, Void args) {
        for (VariableDeclarator v : n.getVariables()) {
            checkForShadowing(v);
        }
        super.visit(n, args);
    }

    @Override
    public void visit(MethodDeclaration n, Void args) {
        declaredVariables.clear();
        super.visit(n, args);
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void args) {
        for (VariableDeclarator v : n.getVariables()) {
            checkForShadowing(v);
        }
        super.visit(n, args);
    }

    private void checkForShadowing(VariableDeclarator v) {
        String name = v.getNameAsString();
        if (declaredVariables.containsKey(name)) {
            Driver.smellyCodeFound("Variable '" + name + "' hides a declaration at a higher level.");
        } else {
            declaredVariables.put(name, v);
        }
    }
}
