package main.model.util;

public class FileTableImpl<K, E> extends FileTable<K, E> {
    @Override
    public String toString() {
        return new StringBuilder()
                .append("File table:\n")
                .append(super.toString())
                .toString();
    }
}
