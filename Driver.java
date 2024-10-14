
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;

public class Driver {

    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("SmellyJava.java");
        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(in);
        } finally {
            in.close();
        }
        new SmellyCodeVisitor().visit(cu, null);
    }

    public static class SmellyCodeVisitor extends VoidVisitorAdapter {
        public void visit(ClassOrInterfaceDeclaration n, Object args) {
            super.visit(n, args);
        }

        public void visit(FieldDeclaration n, Object args) {
            checkOneVariablePerDeclaration(n);
            super.visit(n, args);
        }

        public void visit(VariableDeclarationExpr n, Object args) {
            checkUninitialisedVariables(n);
            checkOneVariablePerDeclaration(n);
            super.visit(n, args);
        }

        /**
         * Check if local variables are initialised upon declaration.
         */
        private void checkUninitialisedVariables(VariableDeclarationExpr n) {
            for (VariableDeclarator v : n.getVariables()) {
                if (v.getInitializer().isEmpty()) {
                    smellyCodeFound("Uninitialized local variable: " + v.getNameAsString());
                }
            }
        }

        /**
         * Check if each variable declaration declares only one variable.
         */
        private void checkOneVariablePerDeclaration(FieldDeclaration n) {
            if (n.getVariables().size() > 1) {
                smellyCodeFound("Multiple variables declared in one statement: " + n.getVariables());
            }
        }

        /**
         * Check if each variable declaration declares only one variable.
         */
        private void checkOneVariablePerDeclaration(VariableDeclarationExpr n) {
            if (n.getParentNode().isEmpty() && n.getParentNode().get() instanceof ForStmt) {
                if (n.getVariables().size() > 1) {
                    smellyCodeFound("Multiple variables declared in one statement (local declaration): " + n.getVariables());
                }
            }
        }

        /**
         * Report smelly code with a message.
         */
        private void smellyCodeFound(String message) {
            System.out.println("Smelly Code Found! Reason: " + message);
        }
    }
}

