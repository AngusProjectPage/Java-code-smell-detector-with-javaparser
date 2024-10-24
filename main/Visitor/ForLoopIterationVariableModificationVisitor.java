package main.Visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;


public class ForLoopIterationVariableModificationVisitor extends VoidVisitorAdapter<Object> {

    private boolean errorFound = false;
    private String offendingVariableName;

    @Override
    public void visit(ForStmt n, Object arg) {
        super.visit(n, arg);

        // Get the initialization expression (e.g., "int i = 0")
        NodeList<Expression> initializationList = n.getInitialization();

        // Extract the iteration variable name from the initialization expression
        String iterationVariableName = extractIterationVariableName(initializationList);

        // Check if the iteration variable is modified within the loop body
        if (iterationVariableName != null) {
            n.getBody().accept(new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(AssignExpr n, Object arg) {
                    super.visit(n, arg);

                    if (n.getTarget().isNameExpr() && n.getTarget().asNameExpr().getNameAsString().equals(iterationVariableName)) {
                        errorFound = true;
                        offendingVariableName = iterationVariableName;
                        // Call Driver.smellyCodeFound()
                        Driver.smellyCodeFound("Iteration variable '" + offendingVariableName + "' is modified within the loop body.");
                    }
                }
            }, null);
        }
    }

    private String extractIterationVariableName(NodeList<Expression> initializationList) {
        for (Expression expr : initializationList) {
            if (expr instanceof VariableDeclarationExpr) {
                VariableDeclarationExpr variableDeclaration = (VariableDeclarationExpr) expr;
                if (variableDeclaration.getVariables().size() == 1) {
                    VariableDeclarator variableDeclarator = variableDeclaration.getVariables().get(0);
                    return variableDeclarator.getNameAsString();
                }
            }
        }
        return null;
    }
}