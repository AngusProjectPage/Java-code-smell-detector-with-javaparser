public class SmellyJava {

    public static void main(String[] args) {
        // Local variable declaration without initialization
        int counter;
        counter = 1; // Initialized immediately after declaration, but not at the same time

        // More bad practice: Variables are not initialized immediately, creating potential confusion
        int a, b;
        a = 'c';
        b = 'c';

        // Even worse: Making assignments unnecessarily complex
        int x;
        int y;
        // Avoid assigning multiple variables to the same value in one statement
        x = (y = 5); // Bad practice: assigning in nested manner

        // Smelly logic using uninitialized or redundant assignments
        int smellyValue;
        smellyValue = 0;  // Setting the value after declaring
        smellyValue += 10;  // Incrementing in a less obvious manner

        System.out.println("Counter: " + counter);
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("x: " + x);
        System.out.println("y: " + y);
        System.out.println("Smelly value: " + smellyValue);
    }
}
