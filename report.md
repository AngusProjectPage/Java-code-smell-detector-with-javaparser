# CS409 Assignment: Detecting Poor Coding Practices using JavaParser

## Team Members
- **Angus Duffy**
- **Harry Morton**
- **Callum Sergeant**

## Detected Coding Violations
This section outlines the coding violations that were attempted and which have been implemented.

- **1. Initialise Local Variables on Declaration** 
    - **Implemented**
    - ```UninitialisedVariableSmellVisitor()```
      - This smell detector runs as expected, no error found in its execution.

- **2. Keep Assignments Simple**
    - **Implemented**
    - ```SimpleAssignmentSmellVisitor()```

- **3. One Variable per Declaration**
    - **Implemented**
    - ```MultipleVariableDeclarationSmellVisitor()```

- **4. Limit Access to Instance and Class Variables**
    - **Implemented**
    - ```checkLimitAccessSmells()```

- **5. Avoid Local Declarations that Hide Declarations at Higher Levels**
    - **Not Implemented**

- **6. Switch: Fall-Through is Commented**
    - **Implemented**
    - ```SwitchFallThroughSmellVisitor()```
    - **Notes**: This smell detector was horrible, the way JavaParse handles comments remains a mystery to  me even after implementing this, hence why it gets the switch block and converts it to a string (I know it's not ideal, but it was this or nothing)

- **7. Avoid Constants in Code**
    - **Implemented**
    - ```AvoidConstantsSmellVisitor()```

- **8. Don't Ignore Caught Exceptions**
    - **Not Implemented**

- **9. Don't Change a For Loop Iteration Variable in the Body of the Loop**
    - **Not Implemented**

- **10. Accessors and Mutators Should Be Named Appropriately**
    - **Not Implemented**

- **11. Switch: Default Label is Included**
    - **Implemented**
    - ```DefaultSwitchSmellVisitor()```
      - This smell detector works to identify the missing default cases and incorrect positions of default cases correctly. It has not however implemented the enum exception, leading to some calls when run against the squeakyClean code.

- **12. Do Not Return References to Private Mutable Class Members**
    - **Not Implemented**

- **13. Do Not Expose Private Members of an Outer Class from within a Nested Class**
    - **Implemented**
    - ```checkOuterPrivateVariableExposedSmells()```

## Suggested Score
Based on the performance and completeness of the analyser, we think a score of **X out of 100** is fair.

## Contribution Breakdown
Provide a percentage contribution breakdown for each group member.
- **Angus Duffy**: 50%
- **Harry Morton**: 50%
- **Callum Sergeant**: 50%
