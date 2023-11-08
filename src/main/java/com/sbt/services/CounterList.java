package com.sbt.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

//TODO документирование
@Service
@Getter
@NoArgsConstructor
public class CounterList {

    private HashMap<String, Integer> counterList = new HashMap<>();

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

    public Long getSum() {
        if (counterList.size() == 0)
            return -1L;
        return counterList.values().stream().mapToLong(i -> i).sum();
    }

    public Set<String> getNames() {
        return counterList.keySet();
    }


    public void setCounterList(HashMap<String, Integer> counterList) {
        this.counterList = counterList;
    }

}
