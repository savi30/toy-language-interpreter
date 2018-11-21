package main.model.util;

public class SymTableImpl<K, V> extends SymTable<K, V> {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SymTable:\n");
        for (K key : keySet()) {
            stringBuilder
                    .append(key)
                    .append("=")
                    .append(get(key))
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
