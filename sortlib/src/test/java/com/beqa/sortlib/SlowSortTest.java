package com.beqa.sortlib;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.beqa.sortlib.slow.SlowSort;
import com.beqa.sortlib.testmodels.Person;

public class SlowSortTest {

    @Test
    void sanityCheck() {
        assertTrue(true);
    }
    
    @DisplayName("Bubble: sorts ascending (expected pass)")
    @Test
    void bubble_sortsAscending_shouldPass() {
    	Integer[] a = {3, 5, 7, 88, 9987, 1, 2, 453, 345};
    	SlowSort.BUBBLE.sort(a);
    	assertArrayEquals(new Integer[]{1,2,3,5,7,88,345,453,9987}, a);
    }
    
    @DisplayName("Bubble: intentionally failing test (verifies test validity)")
    @Disabled("Intentional failure to demonstrate test validity")
    @Test
    void bubble_sortsAscending_intentionalFailure_shouldFail() {
    	Integer[] a = {3, 5, 7, 88, 9987, 1, 2, 453, 345};
    	SlowSort.BUBBLE.sort(a);
    	assertArrayEquals(new Integer[]{1,2,5,7,88,345,453,9987}, a);
    }
    
 //_____________________________________________________________________________________________
    
    
    static SlowSort[] algorithms() {
        return SlowSort.values(); // all 4 sorts
    }
    
    @DisplayName("All slow sorts: sort ascending (expected pass)")
    @ParameterizedTest
    @MethodSource("algorithms")
    void sortsAscending_shouldPass(SlowSort algorithm) {
        Integer[] a = {3, 5, 7, 88, 9987, 1, 2, 453, 345};
        Integer[] expected = a.clone();
        java.util.Arrays.sort(expected); // reference
        
        algorithm.sort(a);
        assertArrayEquals(expected, a);
    }
    
    @DisplayName("Bubble: sorts Person objects by age, then name (record version)")
    @Test
    void bubble_sortsPersonsByAgeThenName() {
        var people = new Person[] {
            new Person("Alice", 32),
            new Person("Bob", 19),
            new Person("Charlie", 27),
            new Person("Bob", 27),
            new Person("Dana", 25),
            new Person("Aaron", 19)
        };

        // Sort by age first, then by name alphabetically if ages are equal
        SlowSort.BUBBLE.sort(people,
            Comparator.comparingInt(Person::age)
                      .thenComparing(Person::name)
        );

        System.out.println("Sorted result:");
        for (var p : people) {
            System.out.println(p);
        }

        var expected = new String[]{
            "Aaron(19)", "Bob(19)",  // same age, alphabetical by name
            "Dana(25)",
            "Bob(27)", "Charlie(27)", // same age, alphabetical by name
            "Alice(32)"
        };

        var actual = Arrays.stream(people)
            .map(p -> p.name() + "(" + p.age() + ")")
            .toArray(String[]::new);

        assertArrayEquals(expected, actual);
    }


}
