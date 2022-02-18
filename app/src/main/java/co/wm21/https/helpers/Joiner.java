package co.wm21.https.helpers;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Joiner<T> {

    private final CharSequence deliminator;
    private CharSequence prefix;
    private CharSequence suffix;
    private ArrayList<T> items;

    public Joiner(CharSequence deliminator) {
        this.deliminator = deliminator;
    }

    public Joiner(CharSequence deliminator, CharSequence prefix, CharSequence suffix) {
        this(deliminator);
        this.prefix = prefix;
        this.suffix = suffix;
        items = new ArrayList<>();
    }

    public Joiner(CharSequence deliminator, CharSequence prefix, CharSequence suffix, ArrayList<T> items) {
        this(deliminator, prefix, suffix);
        this.items = items;
    }

    public Joiner(CharSequence deliminator, CharSequence prefix, CharSequence suffix, T... items) {
        this(deliminator, prefix, suffix);
        this.items = new ArrayList<>();
        addAll(items);
    }

    @NonNull
    @Override
    public String toString() {
        T[] t = (T[]) items.toArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < t.length; i++) {
            result.append(t[i].toString()).append(deliminator);
        }
        result.setLength(result.length() - 1);
        return prefix + result.toString() + suffix;
    }

    public Joiner<T> add(T item) {
        items.add(item);
        return this;
    }

    @SafeVarargs
    public final Joiner<T> addAll(T... items) {
        for (T item : items)
            add(item);
        return this;
    }

    public Joiner<T> addAll(ArrayList<T> items) {
        addAll((T[]) items.toArray());
        return this;
    }

    public Joiner<T> merge(Joiner<T> own) {
        addAll(own.items);
        return this;
    }

    public int length() { return items.size(); }
}
