package wildcode.cuishou.ss.Observer;

/**
 * Created by John on 2017/10/18.
 */

public class BubbleSortTest {
    private static void sort(int[] array) {
        int temp;
        for (int i = array.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j + 1] > array[j]) {
                    temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public static void main(String[] agrs) {
        int[] array = {1, 5, 9, 7, 5, 3, 4, 5, 6, 7, 2, 4, 1, 0, 6, 7};
        sort(array);
        for(int i = 0;i < array.length;i++) {
            System.out.print("" + array[i]);
        }
    }
}
