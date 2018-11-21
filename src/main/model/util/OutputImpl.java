package main.model.util;

import java.util.Iterator;

public class OutputImpl<T> extends Output<T> {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Output:\n");
        Iterator iterator = iterator();
        while (iterator.hasNext()) {
            stringBuilder
                    .append(iterator.next().toString())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
