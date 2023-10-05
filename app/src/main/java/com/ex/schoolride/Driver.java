package com.ex.schoolride;

public class Driver {

    private String DName, DNIC, DVehicle, DContactNo, DAge;
    private String Id;

    public Driver() {

    }

    public Driver(String DName, String DNIC, String DVehicle, String DContactNo, String DAge) {
        this.DName = DName;
        this.DNIC = DNIC;
        this.DVehicle = DVehicle;
        this.DContactNo = DContactNo;
        this.DAge = DAge;


    }

    //getters
    public String getDName() {
        return DName;
    }

    public String getDNIC() {
        return DNIC;
    }

    public String getDVehicle() {
        return DVehicle;
    }

    public String getDContactNo() {
        return DContactNo;
    }

    public String getDAge() {
        return DAge;
    }


    //setters


    public void setDName(String DName) {
        DName = DName;
    }

    public void setDNIC(String DNIC) {
        this.DNIC = DNIC;
    }

    public void setDVehicle(String DVehicle) {
        this.DVehicle = DVehicle;
    }

    public void setDContactNo(String DContactNo) {
        this.DContactNo = DContactNo;
    }

    public void setDAge(String DUsername) {
        this.DAge = DAge;
    }


    public String getId() {
        return Id;
    }
}
