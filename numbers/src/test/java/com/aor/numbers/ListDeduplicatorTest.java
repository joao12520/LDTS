package com.aor.numbers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListDeduplicatorTest {

    private List<Integer> deduplicatorHelper() {

        List<Integer> list = Arrays.asList(1,2,4,2,5);

        return list;
    }

    @Test
    public void deduplicate() {
        List<Integer> list = deduplicatorHelper();
        List<Integer> expected = Arrays.asList(1,2,4,5);

        ListDeduplicator deduplicator = new ListDeduplicator(list);

        IListSorter sorter = Mockito.mock(IListSorter.class);
        Mockito.when(sorter.sort()).thenReturn(new ArrayList<>(Arrays.asList(1,2,4,5)));

        List<Integer> distinct = deduplicator.deduplicate(sorter);

        assertEquals(expected, distinct);
    }
}
