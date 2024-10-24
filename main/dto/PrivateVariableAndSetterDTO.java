package main.dto;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;

import java.util.ArrayList;
import java.util.List;

public class PrivateVariableAndSetterDTO {

    private List<VariableDeclarator> variables;
    private List<AssignExpr> assignments;

    public PrivateVariableAndSetterDTO() {
        this.variables = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public void setVariables(List<VariableDeclarator> variables) {
        this.variables.addAll(variables);
    }

    public void setAssignment(AssignExpr assignment) {
        this.assignments.add(assignment);
    }

    public List<VariableDeclarator> getVariables() {
        return this.variables;
    }

    public List<AssignExpr> getAssignments() {
        return this.assignments;
    }

}
