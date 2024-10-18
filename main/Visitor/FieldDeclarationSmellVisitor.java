package main.Visitor;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

import java.util.HashMap;
import java.util.Map;

public class FieldDeclarationSmellVisitor extends VoidVisitorAdapter<Object> {

   /** public void visit(com.github.javaparser.ast.body.FieldDeclaration n, Object args) {

        Map<String, Node> declaredVariables = new HashMap<>();

        for (VariableDeclarator v : n.getVariables()) { //iterate through each var declaration
            String name = v.getNameAsString();
            if (declaredVariables.containsKey(name)) {
                Driver.smellyCodeFound("Variable '" + name + "' hides a declaration at a higher level");
            }
            declaredVariables.put(name, v);
        }
        super.visit(n, args);
        } */
        private Map<String, Node> declaredVariables = new HashMap<>();

        @Override
        public void visit(FieldDeclaration n, Object args) {
            super.visit(n, args);
            for (VariableDeclarator v : n.getVariables()) {
                checkForShadowing(v);
            }
        }

        @Override
        public void visit(MethodDeclaration n, Object args) {
            super.visit(n, args);
            //for (VariableDeclarator v : n.getVariables()) {
              //  checkForShadowing(v);
            //}
        }

        private void checkForShadowing(VariableDeclarator v) {
            String name = v.getNameAsString();
            if (declaredVariables.containsKey(name)) {
                System.out.println("Variable '" + name + "' hides a declaration at a higher level");
            } else {
                declaredVariables.put(name, v);
            }
        }
    }



