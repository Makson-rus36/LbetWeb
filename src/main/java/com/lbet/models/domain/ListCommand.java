package com.lbet.models.domain;

/**
 * Класс для хранения данных о  командах
 * @author Elena Romanova
 * @version 1.0.1
 */
public class ListCommand {
    /**
     * Поле имя команды
     */
    String nameCommand;

    /**
     * Создание объекта {@link ListCommand}
     * @param nameCommand-имя команды
     */
    public ListCommand(String nameCommand) {
        this.nameCommand = nameCommand;
    }

    /**
     * Функция получения значения поля {@link ListCommand#nameCommand}
     * @return возвращает название команды
     */
    public String getNameCommand() {
        return nameCommand;
    }
}
