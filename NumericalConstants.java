import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class NumericalConstants extends VoidVisitorAdapter<Object> {
    private static final int[] ALLOWED_LITERALS = {-1,0,1};
    /**
    @Override
    public void visit(main.Visitor.FieldDeclaration n, Object args) {
        if (n.isPublic() && n.isStatic() && n.isFinal() && (n.getTokenRange().isPrimitiveType() || n.getTokenRange().isReferenceType())) {
            n.getVariable(0).getInitializer();
        }
        n.getVariable(0).getInitializer();
        ) {
            var literalValue = n.getVariable(0).getInitializer().toString();
            if (!isAllowedLiteral(literalValue)) {
                System.err.println("Error: Constant " + n.getName() + " has an invalid literal value: " + literalValue);
            }
        }
    }
    private boolean isAllowedLiteral(String literalValue) {
        for (int allowedLiteral : ALLOWED_LITERALS) {
            if (literalValue.equals(String.valueOf(allowedLiteral))) {
                return true;
            }
        }
        return false;
    } */
}
