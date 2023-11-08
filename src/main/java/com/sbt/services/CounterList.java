package com.sbt.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

/**
 * Класс для отслеживания работы счётчика. Инкапсулирует в себя обычную мапу
 */
@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CounterList {

    // Можно вынести в репозиторий и заинджектить
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

    /**
     * @param name название счётчика
     * @return значение счётчика, если он представлен или -1 в случае, если счётчиков в системе нет
     */
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

    /**
     * @return Возвращает Long значение суммы или -1 в случае, если счётчиков в системе нет
     */
    public Long getSum() {
        if (counterList.size() == 0)
            return -1L;
        return counterList.values().stream().mapToLong(i -> i).sum();
    }

    public Set<String> getNames() {
        return counterList.keySet();
    }

}
