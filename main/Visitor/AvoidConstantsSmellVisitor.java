package main.Visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.dto.AvoidConstantsSmellDTO;

public class AvoidConstantsSmellVisitor extends VoidVisitorAdapter<Void> {

    private void processLiterals(LiteralExpr n) {
        // If it's parent is not a variable declarator then add
        if(!(n.getParentNode().isPresent() && n.getParentNode().get() instanceof AssignExpr || n.getParentNode().get() instanceof VariableDeclarator)) {
            System.out.println("In expression " + n.getParentNode().get() + ", " + n + " should be a private final field");
        }
    }
    @Override
    public void visit(BooleanLiteralExpr expr, Void args) {
        super.visit(expr, args);
        processLiterals(expr);
    }

    @Override
    public void visit(CharLiteralExpr expr, Void args) {
        super.visit(expr, args);
        processLiterals(expr);
    }

    @Override
    public void visit(DoubleLiteralExpr expr, Void args) {
        super.visit(expr, args);
        processLiterals(expr);
    }

    @Override
    public void visit(IntegerLiteralExpr expr, Void args) {
        super.visit(expr, args);
        processLiterals(expr);
    }

    @Override
    public void visit(LongLiteralExpr expr, Void args) {
        super.visit(expr, args);
        processLiterals(expr);
    }

    @Override
    public void visit(NullLiteralExpr expr, Void args) {
        super.visit(expr, args);
        processLiterals(expr);
    }

    @Override
    public void visit(StringLiteralExpr expr, Void args) {
        super.visit(expr, args);
        processLiterals(expr);
    }

    @Override void visit(ForStmt )


}
