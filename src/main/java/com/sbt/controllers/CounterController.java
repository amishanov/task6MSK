package com.sbt.controllers;

import com.sbt.dto.Name;
import com.sbt.services.CounterList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Контроллер реализует все методы из задания
 * В возвращаемых типах используется массив char, чтобы ответ явно отображался в json формате
 */
@Tag(name = "Счётчики", description = "Основной контроллер системы, предоставляющий доступ к счётчикам")
@RestController
@RequestMapping("/counters")
public class CounterController {
    // Можно было бы вместо вывода строк переводить найденную запись в мапе
    // в нормальный объект Counter(name->count) и возвращать его
    final CounterList counterList;

    @Autowired
    public CounterController(CounterList counterList) {
        this.counterList = counterList;
    }

    @Operation(summary = "Создание счётчика",
            description = "Создаёт счётчик с переданным именем")
    @PostMapping
    public ResponseEntity<char[]> createCounter(@RequestBody Name name) {
        if (checkName(name) && counterList.create(name.getName()))
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(String.format("Счётчик %s успешно создан", name.getName()).toCharArray());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(String.format("Что-то пошло не так. Возможно счётчик %s уже присутствует в системе",
                        name.getName()).toCharArray());
    }

    @Operation(summary = "Инкрементирование счётчика",
            description = "Инкрементирует счётчик с переданным именем")
    @PostMapping("/inc")
    public ResponseEntity<char[]> incrementCounter(@RequestBody Name name) {
        if (counterList.incByName(name.getName()))
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Счётчик %s успешно обновлён", name.getName()).toCharArray());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Что-то пошло не так. Возможно счётчик %s не представлен в системе",
                        name.getName()).toCharArray());
    }

    @Operation(summary = "Получить счётчик",
            description = "Получает значение счётчика с переданным именем")
    @GetMapping("/{name}")
    public ResponseEntity<char[]> getCounter(@PathVariable String name) {
        Integer counter = counterList.getByName(name);
        if (counter != -1)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Счётчик %s: %d", name, counter).toCharArray());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Счётчик %s не представлен в системе", name).toCharArray());
    }

    @Operation(summary = "Удаление счётчика",
            description = "Удаляет счётчик с переданным именем")
    @DeleteMapping("/{name}")
    public ResponseEntity<char[]> deleteCounter(@PathVariable String name) {
        if (counterList.delByName(name))
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Счётчик %s удалён из системы", name).toCharArray());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Счётчик %s не представлен в системе", name).toCharArray());
    }

    @Operation(summary = "Получение суммы счётчиков",
            description = "Передаёт сумму всех счётчиков, созданных в системе")
    @GetMapping("/aggregation/sum")
    public ResponseEntity<char[]> getSum() {
        Long sum = counterList.getSum();
        if (sum != -1)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Сумма счётчиков: %d", sum).toCharArray());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("В системе ещё не представлены счётчики".toCharArray());
    }

    @Operation(summary = "Получение названий счётчиков",
            description = "Передаёт названия всех счётчиков, представленных в системе")
    @GetMapping
    public ResponseEntity<Set<String>> getNames() {
        return ResponseEntity.ok(counterList.getNames());
    }

    private boolean checkName(Name name) {
        String strName = name.getName();
        return !strName.isBlank() && !strName.isEmpty();
    }
}
