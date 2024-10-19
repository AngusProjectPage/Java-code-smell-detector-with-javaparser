package main;

public class SmellyJava {
        // Field declarations with multiple variables in one statement (smelly)
        private int a, b, c;
        public String callum = "hi";

        public void methodExample() {
                // Uninitialized local variable (smelly)
                int x;

                // Multiple variable declaration in one statement (smelly)
                int y = 1, z = 2;

                // Simple but smelly assignment expression (a = b = 5)
                int d;
                d = b = 5;  // Should trigger "Keep assignments simple" smell
                int l;
                l = 4;

                // Variable that does not follow camelCase convention (smelly)
                int BadVariableName = 10;

                if(4 < -7) {
                        int h = 2;
                }

                // For loop with multiple variable declarations (acceptable)
                for (int i = 0, j = 0; i > -8; i++) {
                        System.out.println(i);
                        l++;
                }

                // Uninitialized variable later assigned (should still trigger the smell for x)
                x = 10;
        }
}
