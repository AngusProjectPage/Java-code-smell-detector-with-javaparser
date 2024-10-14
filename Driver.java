import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
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

        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object args) {

            super.visit(n, args);
        }

        @Override
        public void visit(FieldDeclaration n, Object args) {
            super.visit(n, args);
        }

        @Override
        public void visit(AssignExpr n, Object args) {
            simpleAssignment(n);
            super.visit(n, args);
        }

        public void simpleAssignment(AssignExpr n) {
            if(n.getValue().isAssignExpr()) SmellyCodeFound("Keep assignments simple " + n.getValue() + " cannot be assigned to " + n.getTarget());
        }

        public void SmellyCodeFound(String str) {
            System.out.println(str);
        }
    }
}