package com.sbt.controllers;

import com.sbt.dto.Name;
import com.sbt.services.CounterList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
//@RequestMapping("/counters")
public class CounterController {
    // Можно было бы вместо строк переводить найденную запись в мапе в нормальный объект Counter(name->count)
    // и возвращать его
    final CounterList counterList;

    @Autowired
    public CounterController(CounterList counterList) {
        this.counterList = counterList;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCounter(@RequestBody Name name) {
        if (counterList.create(name.getName()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("Счётчик %s успешно создан", name.getName()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(String.format("Что-то пошло не так. Возможно счётчик %s уже присутствует в системе",
                        name.getName()));
    }

    @PostMapping("/inc")
    public ResponseEntity<String> incrementCounter(@RequestBody Name name) {
        if (counterList.incByName(name.getName()))
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Счётчик %s успешно обновлён", name.getName()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Что-то пошло не так. Возможно счётчик %s не представлен в системе",
                        name.getName()));
    }

    @GetMapping("/counters/{name}")
    public ResponseEntity<String> getCounter(@PathVariable String name) {
        Integer counter = counterList.getByName(name);
        if (counter != -1)
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Счётчик %s: %d", name, counter));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Счётчик %s не представлен в системе", name));
    }

    @DeleteMapping("/counters/{name}")
    public ResponseEntity<String> deleteCounter(@PathVariable String name) {
        if (counterList.delByName(name))
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Счётчик %s удалён из системы", name));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Счётчик %s не представлен в системе", name));
    }

    @GetMapping("/sum")
    public ResponseEntity<String> getSum() {
        Long sum = counterList.getSum();
        if (sum != -1)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Сумма счётчиков: %d", sum));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("В системе ещё не представлены счётчики");
    }

    @GetMapping(path = "/counters")
    public ResponseEntity<Set<String>> getNames() {
        return ResponseEntity.ok(counterList.getNames());
    }

}
