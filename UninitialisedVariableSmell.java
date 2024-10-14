import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Smell detector for uninitialised local variables.
 */
public class UninitialisedVariableSmell extends VoidVisitorAdapter<Object> {

    public void visit(VariableDeclarationExpr n, Object args) {
        for (VariableDeclarator v : n.getVariables()) {
            if (v.getInitializer().isEmpty()) {
                Driver.smellyCodeFound("Uninitialized local variable: " + v.getNameAsString());
            }
        }
        super.visit(n, args);
    }
}
