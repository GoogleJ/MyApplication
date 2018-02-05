package wildcode.cuishou.ss.Observer;

/**
 * Created by John on 2017/10/18.
 */

public class Test {
    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                if (j == i) {
                    System.out.println(i + " * " + j + " = " + i * j);
                } else {
                    System.out.print(i + " * " + j + " = " + i * j + " ");
                }
            }
        }
    }
}
