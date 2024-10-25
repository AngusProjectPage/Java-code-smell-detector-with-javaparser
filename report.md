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
    - Correctly identified `c`, `f`, `lc`, `a`, `b`, `d`, `lc1`, `lc2`, `x`, `y`, and `z` as uninitialised.

- **2. Keep Assignments Simple**
    - **Implemented**
    - ```SimpleAssignmentSmellVisitor()```
    - Correctly identified `y = z = 123`, and `a = b = c` as non-simple assignments.

- **3. One Variable per Declaration**
    - **Implemented**
    - ```MultipleVariableDeclarationSmellVisitor()```
    - Correctly identified `[fldi1, fldi2]`, `[fldc1, fldc2]`, `[fls1, fls2]`, `[a, b, d]`, `[lc1, lc2]`, `[x, y, z]` as multiple variable declarations in one statement.

- **4. Limit Access to Instance and Class Variables**
    - **Implemented**
    - ```checkLimitAccessSmells()```
    - Correctly identified `aVar`, `fldd`, `rn`, `fls1`, and `fls2` as public instance variables in a class that changes state.

- **5. Avoid Local Declarations that Hide Declarations at Higher Levels**
    - **Implemented**
    - `FieldDeclarationSmellVisitor()`
    - Correctly identified `d`, `x`, `dx` as hiding a declaration at a higher level.

- **6. Switch: Fall-Through is Commented**
    - **Implemented**
    - ```SwitchFallThroughSmellVisitor()```
    - **Notes**: This smell detector was horrible, the way JavaParse handles comments remains a mystery to  me even after implementing this, hence why it gets the switch block and converts it to a string (I know it's not ideal, but it was this or nothing)
    - Correctly identified `result = 1`, `result = 2`, `{`, `case 1:`, and `result = 3` as falling through a switch statement without a comment or abrupt termination.

- **7. Avoid Constants in Code**
  - **Implemented**
  - ```AvoidConstantsSmellVisitor()```
  - Correctly identified the following:
    <pre>
    In expression x > 0, 0 should be a private final field
    In expression result + 1, 1 should be a private final field
    In expression result + 1, 1 should be a private final field
    In expression case 1:, 1 should be a private final field
    In expression case 2:result = result + response;break;, 2 should be a private final field
    In expression case 3:result = result / response;result = 3;, 3 should be a private final field
    In expression case 4:result = result * response;break;, 4 should be a private final field
    In expression a != 10, 10 should be a private final field
    In expression j > -10, -10 should be a private final field
    In expression i < 100, 100 should be a private final field
    </pre>

- **8. Don't Ignore Caught Exceptions**
    - **Implemented**
    - `CaughtExceptionsSmellVisitor()`
    - Correctly identified that catch block  `catch (FileNotFoundException e) {
      }` should not be left empty

- **9. Don't Change a For Loop Iteration Variable in the Body of the Loop**
    - **Implemented**
    - `ForLoopIterationVariableModificationVisitor()`
    - Correctly identified iteration variable `j` as being modified within the loop body.

- **10. Accessors and Mutators Should Be Named Appropriately**
    - **Implemented**
    - `GetterSetterNamingVisitor()`
    - Identified the following reasons:
      <pre>
      Getter method getVar should be named 'getVar'
      Getter method getterForfls1 should be named 'getterForfls1'
      Setter method setterForfls1 should be named 'setterForfls1'
      </pre>
    - **Notes**: Some of the cases were flagged as a false positive, see output above.

- **11. Switch: Default Label is Included**
    - **Implemented**
    - ```DefaultSwitchSmellVisitor()```
      - This smell detector works to identify the missing default cases and incorrect positions of default cases correctly. 
  - Correctly identified the following:
    <pre>
    Switch statement missing 'default' case.
    'Default' case is not the last case in the switch statement.
    Switch statement missing 'default' case.
    </pre>
  - It has not however implemented the enum exception, leading to some calls when run against the squeakyClean code.
  
- **12. Do Not Return References to Private Mutable Class Members**
    - **Implemented**
    - `PrivateMutableClassVariablesSmellVisitor()`
    -  Correctly identified the following:
       <pre>
       data is a mutable private field within class mutableObject referenced by class mutableReferenceExposer that is mutated in assignment "data = val"
       data is a mutable private field within class mutableObject referenced by class mutableReferenceExposer that is mutated in assignment "this.data = data"
       </pre>  
    
- **13. Do Not Expose Private Members of an Outer Class from within a Nested Class**
    - **Implemented**
    - ```checkOuterPrivateVariableExposedSmells()```
    - Correctly identified `roomNumber`, `accessPoint`, `fldi1`, `fldi2`, `fldc1`, `fldc2`, `veryPrivateData` are private fields being exposed by inner classes.

## Suggested Score
Based on the performance and completeness of the analyser, we think a score of **91 out of 100** is fair. This takes into consideration the small discrepancies between our code and what is requested in the marking scheme.

## Contribution Breakdown
Each member contributed equally to the project.
- **Angus Duffy**: 33%
- **Harry Morton**: 33%
- **Callum Sergeant**: 33%
