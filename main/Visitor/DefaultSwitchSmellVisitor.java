package main.Visitor;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

public class DefaultSwitchSmellVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(SwitchStmt switchStmt, Void arg) {
        // Extract all entries (cases) from the switch statement
        NodeList<SwitchEntry> entries = switchStmt.getEntries();

        boolean hasDefault = false;
        boolean defaultAtEnd = false;

        // Iterate over the entries to detect the presence of 'default'
        for (int i = 0; i < entries.size(); i++) {
            SwitchEntry entry = entries.get(i);

            // Check if it's a 'default' case
            if (entry.getLabels().isEmpty()) {
                hasDefault = true;
                // Check if it's the last case
                defaultAtEnd = (i == entries.size() - 1);
            }
        }

        // Smell detection conditions
        if (!hasDefault) {
            Driver.smellyCodeFound("Switch statement missing 'default' case.");
        } else if (!defaultAtEnd) {
            Driver.smellyCodeFound("'Default' case is not the last case in the switch statement.");
        }

        super.visit(switchStmt, arg);
    }
}