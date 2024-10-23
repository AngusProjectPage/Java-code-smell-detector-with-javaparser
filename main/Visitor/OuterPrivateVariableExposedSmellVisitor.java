package main.Visitor;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.dto.OuterPrivateVariableExposedDTO;

public class OuterPrivateVariableExposedSmellVisitor extends VoidVisitorAdapter<OuterPrivateVariableExposedDTO> {

    @Override
    public void visit(FieldDeclaration n, OuterPrivateVariableExposedDTO outerPrivateVariableExposedDTO) {
        super.visit(n, outerPrivateVariableExposedDTO);
        // If the field declaration is from an inner class then return
        if(n.findAncestor(ClassOrInterfaceDeclaration.class).isPresent() && n.findAncestor(ClassOrInterfaceDeclaration.class).get().isInnerClass()) {
            return;
        }
        for(Modifier mod : n.getModifiers()) {
            if(mod.getKeyword() != Modifier.publicModifier().getKeyword()) {
                outerPrivateVariableExposedDTO.setFieldDeclarations(n.getVariables());
            }
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, OuterPrivateVariableExposedDTO outerPrivateVariableExposedDTO) {
        super.visit(n, outerPrivateVariableExposedDTO);
        if(n.isInnerClass()) {
            if(n.isPublic()) {
                outerPrivateVariableExposedDTO.setInnerClasses(n);
            }
            else {
                n.findAll(MethodDeclaration.class).forEach(method -> {
                    if (method.isPublic()) {
                        outerPrivateVariableExposedDTO.setMethodDeclarations(method);
                    }
                });
            }
        }
    }
}
