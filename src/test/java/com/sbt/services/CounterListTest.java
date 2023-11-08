package com.sbt.services;


import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CounterListTest {
    private static CounterList counterList = new CounterList();

    @BeforeEach
    private void setUpCounterList() {
        HashMap<String, Integer> mockMap = new HashMap<>();
        mockMap.put("counter1", 1);
        mockMap.put("counter2", 2);
        counterList.setCounterList(mockMap);
    }

    @Test
    public void testCreateIfNotPresented() {
        Assertions.assertTrue(counterList.create("NewCounter"));
    }

    @Test
    public void testCreateIfPresented() {
        Assertions.assertFalse(counterList.create("counter1"));
    }

    @Test
    public void testIncByNameIfPresented() {
        Assertions.assertTrue(counterList.incByName("counter1"));
    }

    @Test
    public void testIncByNameIfNotPresented() {
        Assertions.assertFalse(counterList.incByName("counterNonExisting"));
    }

    @Test
    public void testGetByNameIfPresented() {
        Integer res = counterList.getByName("counter1");
        Assertions.assertEquals(Integer.valueOf(1), res);
    }

    @Test
    public void testGetByNameIfNotPresented() {
        Integer expected = counterList.getByName("counterNonExisting");
        Assertions.assertEquals(Integer.valueOf(-1), expected);
    }

    @Test
    public void testDeleteByNameIfPresented() {
        Assertions.assertTrue(counterList.delByName("counter1"));
    }

    @Test
    public void testDeleteByNameIfNotPresented() {
        Assertions.assertFalse(counterList.delByName("counterNonExisting"));
    }

    @Test
    public void testSumIfPresented() {
        // counter1: 1, counter2: 2 -> sum: 3
        Long expected = 3L;
        Assertions.assertEquals(expected, counterList.getSum());
    }

    @Test
    public void testSumIfNotPresented() {
        counterList.setCounterList(new HashMap<>());
        Long expected = -1L;
        Assertions.assertEquals(expected, counterList.getSum());
    }

    @Test
    public void testGetNames() {
        Set<String> expected = new HashSet<>();
        expected.add("counter1");
        expected.add("counter2");
        Assertions.assertIterableEquals(expected, counterList.getNames());
    }

}
