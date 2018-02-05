package wildcode.cuishou.ss.Observer;

/**
 * Created by John on 2017/10/10.
 */

public class ObSubject extends Subject {
    @Override
    void notifyOb(String state) {
        for (Observer observer : observers) {
            observer.change(state);
        }
    }

}
