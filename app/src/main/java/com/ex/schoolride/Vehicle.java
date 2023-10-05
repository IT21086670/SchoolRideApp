package com.ex.schoolride;

public class Vehicle {

    private  String name, type, insuranceD, vLicenseNo, vehicleNo ;

    public Vehicle(String name, String type, String insuranceD, String vLicenseNo, String vehicleNo) {
        this.name = name;
        this.type = type;
        this.insuranceD = insuranceD;
        this.vLicenseNo = vLicenseNo;
        this.vehicleNo = vehicleNo;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getinsuranceD() {
        return insuranceD;
    }

    public String getvLicenseNo() {
        return vLicenseNo;
    }

    public String getvehicleNo() {
        return vehicleNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInsuranceD(String insuranceD) {
        this.insuranceD = insuranceD;
    }

    public void setvLicenseNo(String vLicenseNo) {
        this.vLicenseNo = vLicenseNo;
    }

    public void setvehicleNo(String vehicleNo) {
        vehicleNo = vehicleNo;
    }
}
