package main.Visitor;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

/**
 * Smell detector for detecting multiple variables declared in one statement.
 */
public class MultipleVariableDeclarationSmellVisitor extends VoidVisitorAdapter<Void> {

    public void visit(FieldDeclaration n, Void args) {
        if (n.getVariables().size() > 1) {
            Driver.smellyCodeFound("Multiple variables declared in one statement: " + n.getVariables());
        }
        super.visit(n, args);
    }

    public void visit(VariableDeclarationExpr n, Void args) {
        if (n.getParentNode().isPresent() && !(n.getParentNode().get() instanceof ForStmt)) {
            if (n.getVariables().size() > 1) {
                Driver.smellyCodeFound("Multiple variables declared in one statement (local declaration): " + n.getVariables());
            }
        }
        super.visit(n, args);
    }
}
