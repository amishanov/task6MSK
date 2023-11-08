package com.sbt;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;


/**
 * @author 21458608
 * Сервис для отслеживания счётчиков
 * Реализованы все заявленные в задании методы
 * Хранение счётчиков осущетсвляется в мапе, где ключом выступает имя счётчика, а значением - значение
 * Счётчики представлены сервисом CounterList
 * dto Name нужен для инкапсулирования String, чтобы контроллер корректно воспринимал Json (Мож
 */
@SpringBootApplication
public class Task6Application {
    public static void main(String[] args) {
        SpringApplication.run(Task6Application.class, args);
    }
}
