package com.lbet.models.domain;

import javax.persistence.*;

@Entity
public class UserRates {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long rateId;
    Float coefficientWinFirst;
    Float coefficientNoWin;
    Float coefficientWinSecond;
    String nameFirstCommand;
    String nameSecondCommand;
    String typeSport;

    public UserRates() {
    }

    public UserRates(Float coefficientWinFirst, Float coefficientNoWin, Float coefficientWinSecond, String nameFirstCommand, String nameSecondCommand, String typeSport) {
        this.coefficientWinFirst = coefficientWinFirst;
        this.coefficientNoWin = coefficientNoWin;
        this.coefficientWinSecond = coefficientWinSecond;
        this.nameFirstCommand = nameFirstCommand;
        this.nameSecondCommand = nameSecondCommand;
        this.typeSport = typeSport;
    }

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    public Float getCoefficientWinFirst() {
        return coefficientWinFirst;
    }

    public void setCoefficientWinFirst(Float coefficientWinFirst) {
        this.coefficientWinFirst = coefficientWinFirst;
    }

    public Float getCoefficientNoWin() {
        return coefficientNoWin;
    }

    public void setCoefficientNoWin(Float coefficientNoWin) {
        this.coefficientNoWin = coefficientNoWin;
    }

    public Float getCoefficientWinSecond() {
        return coefficientWinSecond;
    }

    public void setCoefficientWinSecond(Float coefficientWinSecond) {
        this.coefficientWinSecond = coefficientWinSecond;
    }

    public String getNameFirstCommand() {
        return nameFirstCommand;
    }

    public void setNameFirstCommand(String nameFirstCommand) {
        this.nameFirstCommand = nameFirstCommand;
    }

    public String getNameSecondCommand() {
        return nameSecondCommand;
    }

    public void setNameSecondCommand(String nameSecondCommand) {
        this.nameSecondCommand = nameSecondCommand;
    }

    public String getTypeSport() {
        return typeSport;
    }

    public void setTypeSport(String typeSport) {
        this.typeSport = typeSport;
    }
}
