package main.model.util;

import java.util.Iterator;

public class ExecutionStackImpl<T> extends ExecutionStack<T> {

    @Override
    public synchronized String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Execution stack:\n");
        Iterator iterator = iterator();
        while (iterator.hasNext()) {
            stringBuilder
                    .append(iterator.next().toString())
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
