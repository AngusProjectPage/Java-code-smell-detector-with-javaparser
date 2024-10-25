package main.Visitor;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

public class GetterSetterNamingVisitor extends VoidVisitorAdapter<Object> {
    @Override
    public void visit(MethodDeclaration n, Object arg) {
        super.visit(n, arg);

        String methodName = n.getNameAsString();

        if (methodName.startsWith("get") && methodName.length() > 3) {
            String propertyName = methodName.substring(3);
            if (!checkGetterName(propertyName, n)) {
                Driver.smellyCodeFound("Getter method " + n.getNameAsString() + " should be named 'get" + propertyName + "'");
            }
        } else if (methodName.startsWith("set") && methodName.length() > 3) {
            String propertyName = methodName.substring(3);
            if (!checkSetterName(propertyName, n)) {
                Driver.smellyCodeFound("Setter method " + n.getNameAsString() + " should be named 'set" + propertyName + "'");
            }
        } else {
            // Check for missing getter or setter
            if (n.getType().asString().equals(capitalizeFirstLetter(methodName))) {
                Driver.smellyCodeFound("Missing getter method for property " + methodName);
            } else if (n.getParameters().size() == 1 && n.getParameters().get(0).getType().asString().equals(capitalizeFirstLetter(methodName))) {
                Driver.smellyCodeFound("Missing setter method for property " + methodName);
            }
        }
    }

    private boolean checkGetterName(String propertyName, MethodDeclaration n) {
        //Checks if return matches
        if (!n.getType().asString().equals(capitalizeFirstLetter(propertyName))) {
            return false;
        }

        //Heuristic
        return true;
    }

    private boolean checkSetterName(String propertyName, MethodDeclaration n) {
        return n.getParameters().size() == 1 && n.getParameters().get(0).getType().asString().equals(capitalizeFirstLetter(propertyName));
    }

    private String capitalizeFirstLetter(String str) {
        if (str.length() == 0) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
