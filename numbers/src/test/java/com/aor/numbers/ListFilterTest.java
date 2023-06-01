package com.aor.numbers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ListFilterTest {
    @Test
    public void test(){
        IListFilter f = Mockito.mock(IListFilter.class);
        Mockito.when(f.accept(any(Integer.class))).thenReturn(false);
        Mockito.when(f.accept(1)).thenReturn(true);
        Mockito.when(f.accept(4)).thenReturn(true);
        Mockito.when(f.accept(5)).thenReturn(true);
        Mockito.when(f.accept(9)).thenReturn(true);
        Mockito.when(f.accept(16)).thenReturn(true);

        List<Integer> list = new ArrayList<Integer>(Arrays.asList(-1, 0, 1, 3, 4, 5, 6, 7, 17));

        ListFilterer listFilterer = new ListFilterer(list);

        List<Integer> result = listFilterer.filter(f);
        List<Integer> expected = new ArrayList<Integer>(Arrays.asList(1, 4, 5));
        assertEquals(result, expected);
    }
}