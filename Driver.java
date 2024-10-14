import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

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

        new UninitialisedVariableSmell().visit(cu, null);
        new MultipleVariableDeclarationSmell().visit(cu, null);
    }

    /**
     * Report smelly code with a message explaining why.
     */
    public static void smellyCodeFound(String message) {
        System.out.println("Smelly Code Found! Reason: " + message);
    }

}

