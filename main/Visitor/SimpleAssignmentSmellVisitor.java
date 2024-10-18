package main.Visitor;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

/**
 * Smell detector for simple assignments.
 */
public class SimpleAssignmentSmellVisitor extends VoidVisitorAdapter<Void> {

    public void visit(AssignExpr n, Void args) {
        if(n.getValue().isAssignExpr()) Driver.smellyCodeFound("Keep assignments simple " + n.getValue() + " cannot be assigned to " + n.getTarget());
        super.visit(n, args);
    }

}
