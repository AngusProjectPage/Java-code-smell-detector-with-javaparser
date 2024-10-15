package main.Visitor;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

/**
 * Smell detector for uninitialised local variables.
 */
public class UninitialisedVariableSmellVisitor extends VoidVisitorAdapter<Void> {

    public void visit(VariableDeclarationExpr n, Void args) {
        for (VariableDeclarator v : n.getVariables()) {
            if (v.getInitializer().isEmpty()) {
                Driver.smellyCodeFound("Uninitialized local variable: " + v.getNameAsString());
            }
        }
        super.visit(n, args);
    }
}
