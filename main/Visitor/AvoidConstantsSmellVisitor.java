package main.Visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

import javax.swing.text.html.Option;
import java.util.Optional;

public class AvoidConstantsSmellVisitor extends VoidVisitorAdapter<Void> {

    private void processLiterals(LiteralExpr n) {
        Node updatedNode = n;
        if(updatedNode.getParentNode().isPresent() && updatedNode.getParentNode().get() instanceof UnaryExpr) {
            updatedNode = updatedNode.getParentNode().get();
        }
        Optional<Node> parent = updatedNode.getParentNode();
        if(!(parent.isPresent() && parent.get() instanceof AssignExpr || parent.get() instanceof VariableDeclarator)) {
            Optional<Node> greatGrandparent = updatedNode.getParentNode().get().getParentNode();
            if((updatedNode.toString().equals("0") || updatedNode.toString().equals("1") || updatedNode.toString().equals("-1")) && greatGrandparent.isPresent() && greatGrandparent.get() instanceof ForStmt) {
                ForStmt fs = (ForStmt) greatGrandparent.get();
                if(fs.getCompare().isPresent() && fs.getCompare().get().isBinaryExpr()) {
                    BinaryExpr be = (BinaryExpr) fs.getCompare().get();
                    if(be.getRight().toString().equals(updatedNode.toString()) || be.getLeft().toString().equals(updatedNode.toString())) {
                        return;
                    }
                }
            }
            Driver.smellyCodeFound("In expression " + parent.get() + ", " + updatedNode + " should be a private final field");
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
}
