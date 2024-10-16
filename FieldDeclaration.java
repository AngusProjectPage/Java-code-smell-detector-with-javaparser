import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;
import java.util.Map;

public class FieldDeclaration extends VoidVisitorAdapter<Object> {

    public void visit(com.github.javaparser.ast.body.FieldDeclaration n, Object args) {

        Map<String, Node> declaredVariables = new HashMap<>();

        for (VariableDeclarator v : n.getVariables()) { //iterate through each var declaration
            String name = v.getNameAsString();
            if (declaredVariables.containsKey(name)) {
                Driver.smellyCodeFound("Variable '" + name + "' hides a declaration at a higher level");
            }
            declaredVariables.put(name, v);
        }
        super.visit(n, args);
        }

}


