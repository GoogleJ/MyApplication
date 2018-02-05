package wildcode.cuishou.ss.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/10/10.
 */

public abstract class Subject {

    public List<Observer> observers = new ArrayList();

    abstract void notifyOb(String state);

    public void add(Observer observer) {
        observers.add(observer);
        System.out.print("新交了女朋友");
    }

    public void remove(Observer observer) {
        observers.remove(observer);
        System.out.print("分手了");
    }
}
