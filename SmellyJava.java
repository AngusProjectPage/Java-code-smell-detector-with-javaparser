public class SmellyJava {
        int a, b, c;
        int counter;
        int x;
        int y;
        int smellyValue;
        public SmellyJava() {
                counter = 1;
                a = b = c = 'c';
                x = (y = 5);
                System.out.println(y);
                System.out.println(x);
                smellyValue += 10;
                smellyValue = 0;
        }
}
