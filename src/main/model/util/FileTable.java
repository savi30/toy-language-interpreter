package main.model.util;

import java.util.HashMap;

public abstract class FileTable<K, E> extends HashMap<K, E> {

    private static int INSTANCE_COUNT = 0;

    public static Integer getId() {
        return INSTANCE_COUNT++;
    }

    public abstract void setContent(HashMap<K, E> newTable);
}
