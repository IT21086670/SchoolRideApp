package com.ex.schoolride;

public class Payment {

    private String Date;
    private String Fee;

    public Payment(){

    }

    public Payment(String date, String fee) {
        Date = date;
        Fee = fee;
    }

    public String getDate() {
        return Date;
    }

    public String getFee() {
        return Fee;
    }

    //setters

    public void setDate(String date) {
        Date = date;
    }

    public void setFee(String fee) {
        Fee = fee;
    }
}
