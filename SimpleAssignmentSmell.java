import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Smell detector for simple assignments.
 */
public class SimpleAssignmentSmell extends VoidVisitorAdapter<Object> {

    public void visit(AssignExpr n, Object args) {
        if(n.getValue().isAssignExpr()) Driver.smellyCodeFound("Keep assignments simple " + n.getValue() + " cannot be assigned to " + n.getTarget());
        super.visit(n, args);
    }

}
