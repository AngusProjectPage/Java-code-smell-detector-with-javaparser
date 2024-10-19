package main.dto;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.LiteralExpr;

import java.util.ArrayList;
import java.util.List;

public class AvoidConstantsSmellDTO {

    private List<VariableDeclarator> variables;
    private List<LiteralExpr> literals;
    AvoidConstantsSmellDTO() {
        this.variables = new ArrayList<>();
        this.literals = new ArrayList<>();
    }

    public List<VariableDeclarator> getVariables() {
        return this.variables;
    }

    public List<LiteralExpr> getLiterals() {
        return this.literals;
    }

    public void setVariable(List<VariableDeclarator> variables) {
        this.variables.addAll(variables);
    }

    public void setLiteral(LiteralExpr literal) {
        this.literals.add(literal);
    }
}
