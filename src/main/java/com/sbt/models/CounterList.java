package com.sbt.models;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

//TODO документирование
@Service
@Getter
public class CounterList {
    //TODO Заменить на Atomic?
    private static final HashMap<String, Integer> counterList = new HashMap<>();

    public boolean create(String name) {
        if (counterList.containsKey(name))
            return false;
        counterList.put(name, 0);
        return true;
    }

    public boolean incByName(String name) {
        if (counterList.containsKey(name)) {
            counterList.put(name, counterList.get(name) + 1);
            return true;
        }
        return false;
    }

    public Integer getByName(String name) {
        return counterList.getOrDefault(name, -1);
    }

    public boolean delByName(String name) {
        if (counterList.containsKey(name)) {
            counterList.remove(name);
            return true;
        }
        return false;
    }

    //TODO протестировать отсутствие элементов
    public Long getSum() {
        if (counterList.size() == 0)
            return -1L;
        return counterList.values().stream().mapToLong(i -> i).sum();
    }

    public Set<String> getNames() {
        return counterList.keySet();
    }
}
