package com.lbet.models.domain;

import javax.persistence.*;

@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userid;

    String firstname;
    String lastname;
    String emailUser;
    String passwordUser;
    String expirationDateMounth;
    String expirationDateYear;
    String ownerCard;
    String cvvCode;
    String numberCard;
    int role;
    @Lob
    byte[] imageAvatar;


    public UserInfo() {
    }

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


    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getExpirationDateMounth() {
        return expirationDateMounth;
    }

    public void setExpirationDateMounth(String expirationDateMounth) {
        this.expirationDateMounth = expirationDateMounth;
    }

    public String getExpirationDateYear() {
        return expirationDateYear;
    }

    public void setExpirationDateYear(String expirationDateYear) {
        this.expirationDateYear = expirationDateYear;
    }

    public String getOwnerCard() {
        return ownerCard;
    }

    public void setOwnerCard(String ownerCard) {
        this.ownerCard = ownerCard;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public byte[] getImageAvatar() {
        return imageAvatar;
    }

    public void setImageAvatar(byte[] imageAvatar) {
        this.imageAvatar = imageAvatar;
    }
}
