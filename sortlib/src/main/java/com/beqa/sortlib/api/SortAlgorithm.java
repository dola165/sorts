package com.beqa.sortlib.api;

import java.util.Comparator;

public interface SortAlgorithm {
    <T extends Comparable<? super T>> void sort(T[] a);
    <T> void sort(T[] a, Comparator<? super T> cmp);
}
