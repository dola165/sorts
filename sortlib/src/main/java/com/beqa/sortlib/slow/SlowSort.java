package com.beqa.sortlib.slow;

import java.util.Comparator;
import java.util.Objects;

import com.beqa.sortlib.api.SortAlgorithm;
import com.beqa.sortlib.internal.ArrayUtils;

public enum SlowSort implements SortAlgorithm{
	BUBBLE(SlowSort::bubble),
	SELECTION(SlowSort::selection),
	COCKTAIL(SlowSort::cocktail),
	GNOME(SlowSort::gnome);
	
	
	@FunctionalInterface
	private interface Impl{
		<T> void sort (T[] a, Comparator<? super T> c);
	}
	
	private final Impl impl;
	SlowSort(Impl impl){
		this.impl = impl;
	}
	
	@Override
    public <T extends Comparable<? super T>> void sort(T[] a) {
		sort(a, Comparator.naturalOrder());
	}
	
    @Override
    public <T> void sort(T[] a, Comparator<? super T> c) {
        Objects.requireNonNull(a, "array");
        Objects.requireNonNull(c, "comparator");
        if (a.length < 2) return;
        impl.sort(a, c);
    }
	
    
    
    private static <T> void bubble(T[] a, Comparator<? super T> c) {
    	
        int n = a.length;
        
        while (n > 1) {
            int lastSwap = 0;
            for (int j = 1; j < n; j++) {
                if (c.compare(a[j - 1], a[j]) > 0) {
                    ArrayUtils.swap(a, j - 1, j);
                    lastSwap = j;
                }
            }
            if (lastSwap == 0) break;
            n = lastSwap;
        }
    }
    
    
    private static <T> void selection(T[] a, Comparator<? super T> c) {
    	
        int n = a.length;
        
        for(int i = 0; i < n - 1; i++) {
        	int minIndex = i;
        	for(int j = i + 1; j < n; j++) {
        		if (c.compare(a[j], a[minIndex]) < 0) {
        			minIndex = j;
        		}
        	}
            if (minIndex != i) {
            	ArrayUtils.swap(a, i, minIndex);
            }        	
        }
    }
    
    private static <T> void cocktail(T[] a, Comparator<? super T> c) {
    	
    	int start = 0;
    	int end = a.length - 1;
    	
    	boolean swapped = true;
    	
    	while(swapped) {
    		swapped = false;
    		
    		for(int i = start; i < end; i++) {
    			if(c.compare(a[i], a[i + 1]) > 0) {
    				ArrayUtils.swap(a, i, i + 1);
    				swapped = true;
    			}
    		}
    		
    		if (!swapped) break;
    		
    		swapped = false;
    		end--;
    		
    		for(int i = end - 1; i >= start; i--) {
    			if(c.compare(a[i], a[i + 1]) > 0) {
    				ArrayUtils.swap(a, i, i + 1);
    				swapped = true;
    			}	    			
    		}
    		
    		start++;
    	}
    }
    
    private static <T> void gnome(T[] a, Comparator<? super T> c) {
    	
        int n = a.length;
        int i = 1; 
        
        while (i < n) {
            if (c.compare(a[i - 1], a[i]) <= 0) {
                i++;
            } else {
                ArrayUtils.swap(a, i - 1, i);
                if (i > 1) i--; else i = 1; 
            }
        }
    }
    
}
