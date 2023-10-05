package com.ex.schoolride;

public class Student {

    private String sName, sSchool, sAddress, sContactNo, sAge;

    public Student(){

    }

    public Student(String sName, String sSchool, String sAddress, String sContactNo, String sAge) {
        this.sName = sName;
        this.sSchool = sSchool;
        this.sAddress = sAddress;
        this.sContactNo = sContactNo;
        this.sAge = sAge;
    }

    //getters

    public String getsName() {
        return sName;
    }

    public String getsSchool() {
        return sSchool;
    }

    public String getsAddress() {
        return sAddress;
    }

    public String getsContactNo() {
        return sContactNo;
    }

    public String getsAge() {
        return sAge;
    }



    //setters

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsSchool(String sSchool) {
        this.sSchool = sSchool;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public void setsContactNo(String sContactNo) {
        this.sContactNo = sContactNo;
    }

    public void setsAge(String sAge) {
        this.sAge = sAge;
    }


}

