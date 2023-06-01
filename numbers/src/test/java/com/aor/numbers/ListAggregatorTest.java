package com.aor.numbers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ListAggregatorTest {

    private List<Integer> aggregatorhelper() {

        List<Integer> list = Arrays.asList(1,2,4,2,5);

        return list;
    }

    @Test
    public void sum() {
        List<Integer> list = aggregatorhelper();

        ListAggregator aggregator = new ListAggregator(list);

        assertEquals(14, aggregator.sum());
    }

    @Test
    public void max() {
        List<Integer> list = aggregatorhelper();

        ListAggregator aggregator = new ListAggregator(list);

        assertEquals(5, aggregator.max());
    }

    @Test
    public void max_bug_7263() {
        List<Integer> list = Arrays.asList(-1,-4,-5);

        ListAggregator aggregator = new ListAggregator(list);

        assertEquals(-1, aggregator.max());
    }

    @Test
    public void min() {
        List<Integer> list = aggregatorhelper();

        ListAggregator aggregator = new ListAggregator(list);

        int min = aggregator.min();

        assertEquals(1, min);
    }

    @Test
    public void distinct() {
        List<Integer> list = aggregatorhelper();

        ListAggregator aggregator = new ListAggregator(list);

        IListDeduplicator deduplicator = Mockito.mock(IListDeduplicator.class);
        Mockito.when(deduplicator.deduplicate(any(IListSorter.class))).thenReturn(new ArrayList<>(Arrays.asList(1,2,4,5)));

        int distinct = aggregator.distinct(deduplicator);

        assertEquals(4, distinct);
    }

    @Test
    public void distinct_bug_8726() {
        List<Integer> list = Arrays.asList(1, 2, 4, 2);

        ListAggregator aggregator = new ListAggregator(list);

        IListDeduplicator deduplicator = Mockito.mock(IListDeduplicator.class);
        Mockito.when(deduplicator.deduplicate(any(IListSorter.class))).thenReturn(new ArrayList<>(Arrays.asList(1,2,4)));

        assertEquals(3, aggregator.distinct(deduplicator));
    }
}
