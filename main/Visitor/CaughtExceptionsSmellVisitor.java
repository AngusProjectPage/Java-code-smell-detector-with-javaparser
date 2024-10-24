package main.Visitor;

import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

public class CaughtExceptionsSmellVisitor extends VoidVisitorAdapter<Void> {
    @Override
    public void visit(CatchClause n, Void args) {
        if(n.getBody().isEmpty()) {
            if(n.getParameter().getName().asString().startsWith("expected")) {
                return;
            }
            if(n.getBody().getAllContainedComments().isEmpty()) {
                Driver.smellyCodeFound("Catch block " + n + " should not be left empty");
            }
        }
    }
}
