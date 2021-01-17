package com.lbet.models.domain;

import javax.persistence.*;

/**
 * Класс для хранения данных о пользователе
 * @author Elena Romanova
 * @version 1.0.1
 */
@Entity
public class UserInfo {
    /**
     * Поле:уникальное значение пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userid;
    /**
     * Поле:Имя
     */
    String firstname;
    /**
     * Поле:Фамилия
     */
    String lastname;
    /**
     * Поле:Почта
     */
    String emailUser;
    /**
     * Поле:Пароль
     */
    String passwordUser;
    /**
     * Поле:месяц истечения срока действия карты
     */
    String expirationDateMounth;
    /**
     * Поле:год истечения срока действия карты
     */
    String expirationDateYear;
    /**
     * Поле:владец карты
     */
    String ownerCard;
    /**
     * Поле:трёхзначный код проверки подлинности карты
     */
    String cvvCode;
    /**
     * Поле:номер карты
     */
    String numberCard;
    /**
     * Поле:роль пользователя
     */
    int role;
    /**
     * Поле:аватар пользователя
     */
    @Lob
    byte[] imageAvatar;

    /**
     * Создание объекта {@link UserInfo}
     */
    public UserInfo() {
    }

    /**
     * Создание объекта {@link UserInfo}
     * @param firstname Имя пользователя
     * @param lastname Фамилия
     * @param emailUser Почта
     * @param passwordUser Пароль
     * @param expirationDateMounth месяц истечения срока действия карты
     * @param expirationDateYear год истечения срока действия карты
     * @param ownerCard владец карты
     * @param cvvCode трёхзначный код проверки подлинности карты
     * @param numberCard номер карты
     * @param role роль пользователя
     * @param imageAvatar аватар пользователя
     */
    public UserInfo(
            String firstname,
            String lastname,
            String emailUser,
            String passwordUser,
            String expirationDateMounth,
            String expirationDateYear,
            String ownerCard,
            String cvvCode,
            String numberCard,
            int role,
            byte[] imageAvatar
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
        this.expirationDateMounth = expirationDateMounth;
        this.expirationDateYear = expirationDateYear;
        this.ownerCard = ownerCard;
        this.cvvCode = cvvCode;
        this.numberCard = numberCard;
        this.role = role;
        this.imageAvatar = imageAvatar;
    }

    /**
     * Функция получения значения поля {@link UserInfo#userid}
     * @return возвращает уникальное значение пользователя
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * Функция определения значения поля {@link UserInfo#userid}
     * @param userid -уникальное значение пользователя
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * Функция получения значения поля {@link UserInfo#firstname}
     * @return возвращает имя пользователя
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Функция определения значения поля {@link UserInfo#firstname}
     * @param firstname имя пользователя
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Функция получения значения поля {@link UserInfo#lastname}
     * @return возвращает фамилию пользователя
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Функция определения значения поля {@link UserInfo#lastname}
     * @param lastname фамилия
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Функция получения значения поля {@link UserInfo#emailUser}
     * @return Почту пользователя
     */
    public String getEmailUser() {
        return emailUser;
    }

    /**
     * Функция определения значения поля {@link UserInfo#emailUser}
     * @param emailUser почта
     */
    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    /**
     * Функция получения значения поля {@link UserInfo#passwordUser}
     * @return возвращает пароль
     */
    public String getPasswordUser() {
        return passwordUser;
    }

    /**
     * Функция определения значения поля {@link UserInfo#passwordUser}
     * @param passwordUser пароль
     */
    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    /**
     * Функция получения значения поля {@link UserInfo#expirationDateMounth}
     * @return возвращает месяц истечения срока действия карты
     */
    public String getExpirationDateMounth() {
        return expirationDateMounth;
    }

    /**
     * Функция определения значения поля {@link UserInfo#expirationDateMounth}
     * @param expirationDateMounth месяц истечения срока действия карты
     */
    public void setExpirationDateMounth(String expirationDateMounth) {
        this.expirationDateMounth = expirationDateMounth;
    }

    /**
     * Функция получения значения поля {@link UserInfo#expirationDateYear}
     * @return возвращает год истечения срока действия карты
     */
    public String getExpirationDateYear() {
        return expirationDateYear;
    }

    /**
     * Функция определения значения поля {@link UserInfo#expirationDateYear}
     * @param expirationDateYear год истечения срока действия карты
     */
    public void setExpirationDateYear(String expirationDateYear) {
        this.expirationDateYear = expirationDateYear;
    }

    /**
     * Функция получения значения поля {@link UserInfo#ownerCard}
     * @return возвращает имя владельца карты
     */
    public String getOwnerCard() {
        return ownerCard;
    }

    /**
     * Функция определения значения поля {@link UserInfo#ownerCard}
     * @param ownerCard имя владельца карты
     */
    public void setOwnerCard(String ownerCard) {
        this.ownerCard = ownerCard;
    }

    /**
     * Функция получения значения поля {@link UserInfo#cvvCode}
     * @return трёхзначный код проверки подлинности карты
     */
    public String getCvvCode() {
        return cvvCode;
    }

    /**
     * Функция определения значения поля {@link UserInfo#cvvCode}
     * @param cvvCode трёхзначный код проверки подлинности карты
     */
    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    /**
     * Функция получения значения поля {@link UserInfo#numberCard}
     * @return возвращает номер карты
     */
    public String getNumberCard() {
        return numberCard;
    }

    /**
     * Функция определения значения поля {@link UserInfo#numberCard}
     * @param numberCard номер карты
     */
    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    /**
     * Функция получения значения поля {@link UserInfo#role}
     * @return возращает роль пользователя
     */
    public int getRole() {
        return role;
    }

    /**
     * Функция определения значения поля {@link UserInfo#role}
     * @param role роль пользователя
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Функция получения значения поля {@link UserInfo#imageAvatar}
     * @return аватар пользователя
     */
    public byte[] getImageAvatar() {
        return imageAvatar;
    }

    /**
     * Функция определения значения поля {@link UserInfo#imageAvatar}
     * @param imageAvatar массив байтов
     */
    public void setImageAvatar(byte[] imageAvatar) {
        this.imageAvatar = imageAvatar;
    }
}
