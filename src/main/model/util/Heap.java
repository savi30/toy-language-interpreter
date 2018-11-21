package main.model.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Heap<K, V> extends HashMap<K, V> {
    public abstract int allocate(V value);

    public abstract void setContent(Map<K, V> newHeap);
}
