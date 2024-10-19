package main.Visitor;

import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import main.Driver;

import java.util.ArrayList;
import java.util.List;

public class SwitchFallThroughSmellVisitor extends VoidVisitorAdapter<Void> {

    @Override
    public void visit(SwitchStmt switchStmt, Void arg) {
        // Get the raw content of the switch block
        String switchContent = switchStmt.toString();

        // Process the switch content manually
        processSwitch(switchContent);
    }

    // Method to manually process the switch block
    public void processSwitch(String switchContent) {
        // Split the content into lines
        String[] lines = switchContent.split("\n");
        List<String> caseBlocks = new ArrayList<>();
        StringBuilder currentBlock = new StringBuilder();
        String lastComment = null; // Track the last comment
        boolean insideCaseBlock = false;

        // Iterate through each line to find "case" statements and comments
        for (String line : lines) {
            line = line.trim(); // Trim the line to avoid extra spaces

            // Ignore the switch statement line itself
            if (line.startsWith("switch")) {
                continue;
            }

            // Check if the line is a comment and accumulate it
            if (line.startsWith("//")) {
                lastComment = line; // Store the last comment found
            }

            // Check if the line starts a new case or default block
            else if (line.startsWith("case") || line.startsWith("default")) {
                // If we're already building a block, process it
                if (currentBlock.length() > 0) {
                    // Attach any last comment to the previous block
                    if (lastComment != null) {
                        currentBlock.append(lastComment).append("\n");
                        lastComment = null; // Clear the comment after using it
                    }
                    caseBlocks.add(currentBlock.toString());
                }

                // Start a new block for this case
                currentBlock = new StringBuilder();
                currentBlock.append(line).append("\n");

                insideCaseBlock = true;
            } else if (insideCaseBlock) {
                // If it's a regular statement, just add it to the current block
                currentBlock.append(line).append("\n");
            }
        }

        // Add the final case block if there is one
        if (currentBlock.length() > 0) {
            caseBlocks.add(currentBlock.toString());
        }

        // Process the case blocks for smells
        processCaseBlocks(caseBlocks);
    }

    // Process each case block, looking for abrupt termination or fallthrough comments
    private void processCaseBlocks(List<String> caseBlocks) {
        for (String caseBlock : caseBlocks) {
            // Check if the case block has a valid fallthrough comment
            if (hasValidFallthroughComment(caseBlock)) {
                continue; // Skip processing since a valid fallthrough comment exists
            }

            // If no abrupt termination and no valid fallthrough comment, flag it as smelly
            if (!hasAbruptTermination(caseBlock)) {
                Driver.smellyCodeFound("Fallthrough detected without comment or abrupt termination in switch case block.");
            }
        }
    }

    // Check if a case block has an abrupt termination (break, return, continue, throw)
    private boolean hasAbruptTermination(String caseBlock) {
        return caseBlock.contains("break;") || caseBlock.contains("return;") ||
                caseBlock.contains("continue;") || caseBlock.contains("throw");
    }

    // Check if a case block has a valid fallthrough comment
    private boolean hasValidFallthroughComment(String caseBlock) {
        // We only care about the exact "fall through" comment, ignoring other comments
        return caseBlock.toLowerCase().contains("fall through");
    }
}
