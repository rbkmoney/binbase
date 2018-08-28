package com.rbkmoney.binbase.domain;

import java.util.Objects;

public class BinData {

    private String paymentSystem;

    private String bankName;

    private CountryCode isoCountryCode;

    private CardType cardType;

    public BinData() {
    }

    public BinData(String paymentSystem, String bankName, CountryCode isoCountryCode, CardType cardType) {
        this.paymentSystem = paymentSystem;
        this.bankName = bankName;
        this.isoCountryCode = isoCountryCode;
        this.cardType = cardType;
    }

    public String getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(String paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public CountryCode getIsoCountryCode() {
        return isoCountryCode;
    }

    public void setIsoCountryCode(CountryCode isoCountryCode) {
        this.isoCountryCode = isoCountryCode;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinData binData = (BinData) o;
        return Objects.equals(paymentSystem, binData.paymentSystem) &&
                Objects.equals(bankName, binData.bankName) &&
                Objects.equals(isoCountryCode, binData.isoCountryCode) &&
                Objects.equals(cardType, binData.cardType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentSystem, bankName, isoCountryCode, cardType);
    }

    @Override
    public String toString() {
        return "BinData{" +
                "paymentSystem='" + paymentSystem + '\'' +
                ", bankName='" + bankName + '\'' +
                ", isoCountryCode='" + isoCountryCode + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
