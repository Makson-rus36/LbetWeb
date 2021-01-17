package com.lbet.models.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Класс для хранения данных о добавленных пользователем ставках
 * @author Elena Romanova
 * @version 1.0.1
 */
@Entity
public class AddedUsersRates {
    /**
     * Поле уникальное значение поставленного матча
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long uid;
    /**
     * Поле уникальное значение пользователя
     */
    Long userId;
    /**
     * Поле уникальное значение ставки
     */
    Long rateId;
    /**
     * Поле имя выбранной комадны
     */
    String nameSelectTypeCommand;
    /**
     * Поле определение общей суммы ставки
     */
    Float stakeAmount;

    /**
     * Создание объекта {@link AddedUsersRates}
     */
    public AddedUsersRates() {
    }

    /**
     * Создание объекта {@link AddedUsersRates}
     * @param userId уникальное значение пользователя
     * @param rateId уникальное значение ставки
     * @param nameSelectTypeCommand имя выбранной комадны
     * @param stakeAmount общая сумма ставки
     */
    public AddedUsersRates(Long userId, Long rateId, String nameSelectTypeCommand, Float stakeAmount) {
        this.userId = userId;
        this.rateId = rateId;
        this.nameSelectTypeCommand = nameSelectTypeCommand;
        this.stakeAmount = stakeAmount;
    }

    /**
     * Функция получения значения поля {@link AddedUsersRates#nameSelectTypeCommand}
     * @return возвращает название выбранной команды
     */
    public String getNameSelectTypeCommand() {
        return nameSelectTypeCommand;
    }

    /**
     * Функция опрерделения значения поля {@link AddedUsersRates#nameSelectTypeCommand}
     * @param nameSelectTypeCommand-название выбранной комадны
     */
    public void setNameSelectTypeCommand(String nameSelectTypeCommand) {
        this.nameSelectTypeCommand = nameSelectTypeCommand;
    }

    /**
     * Функция получения значения поля {@link AddedUsersRates#uid}
     * @return возвращает уникальное значение матча
     */
    public Long getUid() {
        return uid;
    }

    /**
     * Функция опрерделения значения поля {@link AddedUsersRates#uid}
     * @param uid-уникальное значение матча
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * Функция получения значения поля {@link AddedUsersRates#userId}
     * @return  возвращает уникальное значение пользователя
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Функция опрерделения значения поля {@link AddedUsersRates#userId}
     * @param userId уникальное значение пользователя
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Функция получения значения поля {@link AddedUsersRates#rateId}
     * @return возвращает уникальное значение ставки
     */
    public Long getRateId() {
        return rateId;
    }

    /**
     * Функция опрерделения значения поля {@link AddedUsersRates#rateId}
     * @param rateId уникальное значения ставки
     */
    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    /**
     * Функция получения значения поля {@link AddedUsersRates#stakeAmount}
     * @return возвращает общую сумму ставки
     */
    public Float getStakeAmount() {
        return stakeAmount;
    }

    /**
     * Функция опрерделения значения поля {@link AddedUsersRates#stakeAmount}
     * @param stakeAmount общая сумма ставки
     */
    public void setStakeAmount(Float stakeAmount) {
        this.stakeAmount = stakeAmount;
    }
}
