
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
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
        public void visit(ClassOrInterfaceDeclaration n, Object args) {
            super.visit(n, args);
        }

        public void visit(FieldDeclaration n, Object args) {
            InitLocal(n, args); // Initialise local variables on declaration
            super.visit(n, args);
        }

        // Method to detect lack if initialisation for local variables on declaration
        public void InitLocal(FieldDeclaration n, Object args) {
            for(VariableDeclarator v : n.getVariables()) {
                if (v.getInitializer().isEmpty()) {
                    SmellyCodeFound(v.getNameAsString());
                }
            }
        }

        // Method called when smelly code is found
        public void SmellyCodeFound(String error){
            System.out.println("Smelly Code Found! Reason:" + error);
        }
    }
}
