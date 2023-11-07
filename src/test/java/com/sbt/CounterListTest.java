package com.sbt;


import com.sbt.models.CounterList;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

public class CounterListTest {
    private static CounterList counterList = new CounterList();

    @AfterEach
    private void clearUpCounterList() {
        counterList.clearAll();
    }

    @Test
    public void testCreateIfNotPresented() {
        Assertions.assertTrue(counterList.create("name"));
    }

    @Test
    public void testCreateIfPresented() {
        counterList.create("name");
        Assertions.assertFalse(counterList.create("name"));
    }

    @Test
    public void testIncByNameIfPresented() {
        counterList.create("name");
        Assertions.assertTrue(counterList.incByName("name"));
    }

    @Test
    public void testIncByNameIfNotPresented() {
        Assertions.assertFalse(counterList.incByName("name"));
    }

    @Test
    public void testGetByNameIfPresented() {
        counterList.create("name");
        counterList.incByName("name");
        Integer res = counterList.getByName("name");
        Assertions.assertEquals(Integer.valueOf(1), res);
    }

    @Test
    public void testGetByNameIfNotPresented() {
        Integer expected = counterList.getByName("name");
        Assertions.assertEquals(Integer.valueOf(-1), expected);
    }

    @Test
    public void testDeleteByNameIfPresented() {
        counterList.create("name");
        Assertions.assertTrue(counterList.delByName("name"));
    }

    @Test
    public void testDeleteByNameIfNotPresented() {
        Assertions.assertFalse(counterList.delByName("name"));
    }

    @Test
    public void testSumIfPresented() {
        // name1: 1, name2: 2 -> sum: 3
        counterList.create("name1");
        counterList.incByName("name1");
        counterList.create("name2");
        counterList.incByName("name2");
        counterList.incByName("name2");
        Long expected = 3L;
        Assertions.assertEquals(expected, counterList.getSum());
    }

    @Test
    public void testSumIfNotPresented() {
        Long expected = -1L;
        Assertions.assertEquals(expected, counterList.getSum());
    }

    @Test
    public void testGetNames() {
        Set<String> expected = new HashSet<>();
        expected.add("name1");
        expected.add("name2");
        counterList.create("name1");
        counterList.create("name2");
        Assertions.assertIterableEquals(expected, counterList.getNames());
    }

}
