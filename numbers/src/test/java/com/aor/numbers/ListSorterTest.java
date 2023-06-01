package com.aor.numbers;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListSorterTest {
    private List<Integer> list;

    public List<Integer> sortHelper() {

        List<Integer> list = Arrays.asList(3,2,6,1,4,5,7);

        return list;
    }

    @Test
    public void sort() {
        List<Integer> list = sortHelper();
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        ListSorter sorter = new ListSorter(list);
        List<Integer> sorted = sorter.sort();

        assertEquals(expected, sorted);
    }
}
