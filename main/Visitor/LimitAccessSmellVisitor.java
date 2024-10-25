package main.Visitor;

import main.dto.LimitAccessSmellDTO;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LimitAccessSmellVisitor extends VoidVisitorAdapter<LimitAccessSmellDTO> {

    @Override
    public void visit(FieldDeclaration n, LimitAccessSmellDTO limitAccessSmellDTO) {
        if(n.isPublic()) {
            limitAccessSmellDTO.setVariables(n.getVariables());
        }
    }

    @Override
    public void visit(MethodDeclaration n, LimitAccessSmellDTO args) {
        args.setMethodFound(true);
    }
}
