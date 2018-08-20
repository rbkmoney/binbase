package com.rbkmoney.binbase.batch;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "record")
public class BinBaseData {

    private String bin;

    private String brand;

    private String bank;

    private String type;

    private String category;

    private String isoname;

    private String isoa2;

    private String isoa3;

    private String isonumber;

    private String url;

    private String phone;

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsoname() {
        return isoname;
    }

    public void setIsoname(String isoname) {
        this.isoname = isoname;
    }

    public String getIsoa2() {
        return isoa2;
    }

    public void setIsoa2(String isoa2) {
        this.isoa2 = isoa2;
    }

    public String getIsoa3() {
        return isoa3;
    }

    public void setIsoa3(String isoa3) {
        this.isoa3 = isoa3;
    }

    public String getIsonumber() {
        return isonumber;
    }

    public void setIsonumber(String isonumber) {
        this.isonumber = isonumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinBaseData that = (BinBaseData) o;
        return Objects.equals(bin, that.bin) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(bank, that.bank) &&
                Objects.equals(type, that.type) &&
                Objects.equals(category, that.category) &&
                Objects.equals(isoname, that.isoname) &&
                Objects.equals(isoa2, that.isoa2) &&
                Objects.equals(isoa3, that.isoa3) &&
                Objects.equals(isonumber, that.isonumber) &&
                Objects.equals(url, that.url) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bin, brand, bank, type, category, isoname, isoa2, isoa3, isonumber, url, phone);
    }

    @Override
    public String toString() {
        return "BinbaseData{" +
                "bin='" + bin + '\'' +
                ", brand='" + brand + '\'' +
                ", bank='" + bank + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", isoname='" + isoname + '\'' +
                ", isoa2='" + isoa2 + '\'' +
                ", isoa3='" + isoa3 + '\'' +
                ", isonumber='" + isonumber + '\'' +
                ", url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
