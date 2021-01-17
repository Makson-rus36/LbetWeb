package com.lbet.models.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AddedUsersRates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long uid;

    Long userId;
    Long rateId;
    String nameSelectTypeCommand;
    Float stakeAmount;

    public AddedUsersRates() {
    }

    public AddedUsersRates(Long userId, Long rateId, String nameSelectTypeCommand, Float stakeAmount) {
        this.userId = userId;
        this.rateId = rateId;
        this.nameSelectTypeCommand = nameSelectTypeCommand;
        this.stakeAmount = stakeAmount;
    }

    public String getNameSelectTypeCommand() {
        return nameSelectTypeCommand;
    }

    public void setNameSelectTypeCommand(String nameSelectTypeCommand) {
        this.nameSelectTypeCommand = nameSelectTypeCommand;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    public Float getStakeAmount() {
        return stakeAmount;
    }

    public void setStakeAmount(Float stakeAmount) {
        this.stakeAmount = stakeAmount;
    }
}
