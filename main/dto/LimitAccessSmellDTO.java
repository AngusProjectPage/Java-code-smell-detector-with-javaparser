package main.dto;

import com.github.javaparser.ast.body.VariableDeclarator;

import java.util.ArrayList;
import java.util.List;

public class LimitAccessSmellDTO {
    private boolean methodFound;
    private List<VariableDeclarator> variables;
    public LimitAccessSmellDTO() {
        variables = new ArrayList<>();
        methodFound = false;
    }

    public List<VariableDeclarator> getVariables() {
        return this.variables;
    }

    public boolean methodFound() {
        return methodFound;
    }

    public void setMethodFound(boolean methodFound) {
        this.methodFound = methodFound;
    }

    public void setVariables(List<VariableDeclarator> variables) {
        this.variables.addAll(variables);
    }
}
