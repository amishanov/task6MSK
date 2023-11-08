package com.sbt.controllers;

import com.sbt.services.CounterList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CounterController.class)
public class CounterControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CounterList counterList;

    @Test
    public void testCreateIfCreated() throws Exception {
        Mockito.when(counterList.create("newCounter")).thenReturn(true);
        String json = "{\"name\": \"newCounter\"}";
        mvc.perform(post("/counters")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateIfBadRequest() throws Exception {
        Mockito.when(counterList.create("ExistingCounter")).thenReturn(false);
        String json = "{\"name\": \"ExistingCounter\"}";
        mvc.perform(post("/counters")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testIncIfOk() throws Exception {
        Mockito.when(counterList.incByName("ExistingCounter")).thenReturn(true);
        String json = "{\"name\": \"ExistingCounter\"}";
        mvc.perform(post("/counters/inc")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testIncIfBadRequest() throws Exception{
        Mockito.when(counterList.incByName("NonExistingCounter")).thenReturn(false);
        String json = "{\"name\": \"NonExistingCounter\"}";
        mvc.perform(post("/counters/inc")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetCounterIfOk() throws Exception {
        Mockito.when(counterList.getByName("ExistingCounter")).thenReturn(1);
        mvc.perform(get("/counters/ExistingCounter"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCounterIfNotFound() throws Exception {
        Mockito.when(counterList.getByName("NonExistingCounter")).thenReturn(-1);
        mvc.perform(get("/counters/NonExistingCounter"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCounterIfOk() throws Exception {
        Mockito.when(counterList.delByName("ExistingCounter")).thenReturn(true);
        mvc.perform(delete("/counters/ExistingCounter"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCounterIfNotFound() throws Exception {
        Mockito.when(counterList.delByName("NonExistingCounter")).thenReturn(false);
        mvc.perform(delete("/counters/NonExistingCounter"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetSumIfOk() throws Exception {
        Mockito.when(counterList.getSum()).thenReturn(1L);
        mvc.perform(get("/counters/aggregation/sum"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSumIfNotFound()throws Exception  {
        Mockito.when(counterList.getSum()).thenReturn(-1L);
        mvc.perform(get("/counters/aggregation/sum"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetNames() throws Exception {
        Mockito.when(counterList.getNames()).thenReturn(new HashSet<>(Arrays.asList("C1", "C2")));
        mvc.perform(get("/counters"))
                .andExpect(status().isOk());
    }
}
