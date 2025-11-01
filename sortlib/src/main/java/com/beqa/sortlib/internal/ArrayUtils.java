package com.beqa.sortlib.internal;

public final class ArrayUtils {
    private ArrayUtils() {}
    
    public static <T> void swap(T[] a, int i, int j) {
        T t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
}
