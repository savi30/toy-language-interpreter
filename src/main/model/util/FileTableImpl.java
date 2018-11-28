package main.model.util;

import java.util.HashMap;

public class FileTableImpl<K, E> extends FileTable<K, E> {
    @Override
    public String toString() {
        return new StringBuilder()
                .append("File table:\n")
                .append(super.toString())
                .toString();
    }

    @Override
    public void setContent(HashMap<K, E> newFileTable) {
        this.clear();
        this.putAll(newFileTable);
    }
}
