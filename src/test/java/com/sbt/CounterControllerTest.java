package com.sbt;

import com.sbt.controllers.CounterController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CounterControllerTest {

    @Autowired
    private CounterController counterController;

    @AfterEach
    private void clearUpCounterList() {
        counterController.clearCounterList();
    }

    @Test
    public void testCreateIfCreated() {
        ResponseEntity<String> actual = counterController.createCounter("name");
        Assertions.assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        Assertions.assertEquals("Счётчик name успешно создан", actual.getBody());
    }

    @Test
    public void testCreateIfBadRequest() {
        counterController.createCounter("name");
        ResponseEntity<String> actual = counterController.createCounter("name");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void testIncIfOk() {
        counterController.createCounter("name");
        ResponseEntity<String> actual = counterController.incrementCounter("name");
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals("Счётчик name успешно обновлён", actual.getBody());
    }

    @Test
    public void testIncIfBadRequest() {
        ResponseEntity<String> actual = counterController.incrementCounter("name");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void testGetCounterIfOk() {
        counterController.createCounter("name");
        ResponseEntity<String> actual = counterController.getCounter("name");
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals("Счётчик name: 0", actual.getBody());
    }

    @Test
    public void testGetCounterIfNotFound() {
        ResponseEntity<String> actual = counterController.getCounter("name");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    public void testDeleteCounterIfOk() {
        counterController.createCounter("name");
        ResponseEntity<String> actual = counterController.deleteCounter("name");
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals("Счётчик name удалён из системы", actual.getBody());
    }

    @Test
    public void testDeleteCounterIfNotFound() {
        ResponseEntity<String> actual = counterController.deleteCounter("name");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    public void testGetSumIfOk() {
        counterController.createCounter("name");
        counterController.incrementCounter("name"); counterController.incrementCounter("name");
        counterController.createCounter("name2"); counterController.incrementCounter("name2");
        ResponseEntity<String> actual = counterController.getSum();
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertEquals("Сумма счётчиков: 3", actual.getBody());
    }

    @Test
    public void testGetSumIfNotFound() {
        ResponseEntity<String> actual = counterController.getSum();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
    }

    @Test
    public void testGetNames() {
        Set<String> expected = new HashSet<>();
        expected.add("name1");
        expected.add("name2");
        counterController.createCounter("name1");
        counterController.createCounter("name2");
        ResponseEntity<Set<String>> actual = counterController.getNames();
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Assertions.assertIterableEquals(expected, actual.getBody());
    }
}
