package com.aor.numbers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DivisibleByFilterTest {

    @Test
    public void test(){
        DivisibleByFilter f = new DivisibleByFilter(7);
        assertEquals(f.accept(-7*12345), true);
        assertEquals(f.accept(-7), true);
        assertEquals(f.accept(-1), false);
        assertEquals(f.accept(0), true);
        assertEquals(f.accept(1), false);
        assertEquals(f.accept(3), false);
        assertEquals(f.accept(4), false);
        assertEquals(f.accept(6), false);
        assertEquals(f.accept(7), true);
        assertEquals(f.accept(8), false);
        assertEquals(f.accept(13), false);
        assertEquals(f.accept(14), true);
        assertEquals(f.accept(7*12345), true);
    }
}