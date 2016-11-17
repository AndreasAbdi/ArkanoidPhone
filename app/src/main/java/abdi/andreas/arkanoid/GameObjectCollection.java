package abdi.andreas.arkanoid;

import java.lang.reflect.Array;

public class GameObjectCollection<T extends GameObject> {
    private T[] t;
    int size;

    public GameObjectCollection(int size, Class<T> c) {
        final T[] array = (T[]) Array.newInstance(c, size);
        this.t = array;
        this.size = size;
    }

    public T getInstance(int index) {
        return t[index];
    }

    public void setInstance(int index, T object) {
        t[index] = object;
    }

}
