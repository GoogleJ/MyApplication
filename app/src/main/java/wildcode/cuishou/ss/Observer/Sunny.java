package wildcode.cuishou.ss.Observer;

/**
 * Created by John on 2017/10/10.
 */

public class Sunny implements Observer {
    @Override
    public void change(String state) {
        System.out.print("Sunny知道了我正在" + state);
    }
}
