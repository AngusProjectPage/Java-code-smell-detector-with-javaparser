# Java code smell detector with Javaparser


## Code Smell Checklist

### 1. Initialise local variables on declaration
- [x] Local variables should have initializers or be initialized immediately after declaration.
  - **Example:** `int counter;` instead of `int counter = 1;`
  - **Marks:** 5

### 2. Keep assignments simple
- [x] Avoid assigning several variables to the same value in a single statement.
  - **Example:** Avoid `a = b = 'c';`
  - **Marks:** 5

### 3. One variable per declaration
- [x] Each variable declaration (field or local) should declare only one variable.
  - **Example:** Avoid `int a, b;`
  - **Exception:** Multiple variable declarations are acceptable in the header of a `for` loop.
  - **Marks:** 3-6

### 4. Limit access to Instance and Class Variables
- [x] Do not make any instance or class variable `public` without a good reason, to maintain encapsulation.
  - **Example:**
    ```java
    public class classOne {
        public static int MaxValue = 255;
        public String password = "admin123";
    }
    ```
  - **Marks:** 3-6

### 5. Avoid local declarations that hide declarations at higher levels
- [x] Do not declare the same variable name in an inner block.
  - **Example:**
    ```java
    int count;
    if (condition) {
        int count; // AVOID!
    }
    ```
  - **Marks:** 8

### 6. Switch: Fall-through is commented
- [x] Within a switch block, comment each statement group that does not terminate abruptly (with break, continue, return, or throw).
  - **Example:**
    ```java
    switch (input) {
        case 1:
        case 2:
            doOneOrTwo();
            // fall through
        case 3:
            doOneTwoOrThree();
            break;
        default:
            doTheRest(input);
    }
    ```
  - **Marks:** 8

### 7. Avoid constants in code
- [x] Numerical constants (literals) should not be hardcoded directly.
  - **Example:**
    ```java
    if (a == 255 && b.equals("admin123")) { ... }
    ```
  - **Exception:** The values `-1`, `0`, and `1` can appear in a for loop as counter values.
  - **Marks:** 6-8

### 8. Don't ignore caught exceptions
- [x] When ignoring a caught exception, explain why with a comment.
  - **Example:**
    ```java
    try {
        int i = Integer.parseInt(val);
        return handleNumericVal(i);
    } catch (NumberFormatException ok) {
        // it's not numeric; that's fine, just continue
    }
    return handleTextVal(val);
    ```
  - **Exception:** In tests, a caught exception may be ignored without comment if its name is or begins with `expected`.
  - **Marks:** 6-8

### 9. Don't change a `for` loop iteration variable in the body of the loop
- [x] Avoid changing the iteration variable in the body of the loop.
  - **Example:**
    ```java
    for (int i = 0; i < 10; i++) {
        System.out.print(i);
        i++; // Bad!
    }
    ```
  - **Marks:** 8

### 10. Accessors and Mutators should be named appropriately
- [x] Getters and setters should be named using the `getVariableName`/`setVariableName` convention.
  - **Example:**
    ```java
    public class CheckingAccount {
        private int balance;
        public int getBalance() { return this.balance; }
        public void setBalance(int newBal) { this.balance = newBal; }
    }
    ```
  - **Marks:** 8

### 11. Switch: default label is included
- [x] Each `switch` statement includes a `default` statement, even if it contains no code, and it should be the last option.
  - **Example:**
    ```java
    switch (input) {
        case 1:
        case 2:
            doOneOrTwo();
        case 3:
            doOneTwoOrThree();
            break;
        default:
            doTheRest(input);
    }
    ```
  - **Exception:** A switch statement for an enum type may omit the default statement group if it covers all possible enum values.
  - **Marks:** 6-10

### 12. Do not return references to private mutable class members
- [x] Returning references to private mutable members can compromise encapsulation and security.
  - **Example:**
    ```java
    public class MutableClass {
        private Date d;
        public Date getDate() { return d; }
    }
    ```
  - **Solution:** Return a copy of the mutable object instead of the actual reference.
  - **Marks:** 10

### 13. Do not expose private members of an outer class from within a nested class
- [x] The nested class should not expose private members of the outer class to external classes.
  - **Example:**
    ```java
    class Coordinates {
        private int x;
        private int y;
        public class Point {
            public void getPoint() {
                System.out.println("(" + x + "," + y + ")");
            }
        }
    }
    ```
  - **Marks:** 10



