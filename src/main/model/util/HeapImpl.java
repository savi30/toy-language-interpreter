package main.model.util;

import java.util.Map;

public class HeapImpl extends Heap<Integer, Integer> {


    private Integer nextFree = 0;

    @Override
    public int allocate(Integer value) {
        this.put(++nextFree, value);
        return nextFree;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Heap:\n")
                .append(super.toString())
                .toString();
    }

    public void setContent(Map<Integer, Integer> newHeap) {
        this.clear();
        this.putAll(newHeap);
    }
}
