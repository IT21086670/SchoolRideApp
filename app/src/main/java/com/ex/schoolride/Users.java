package com.ex.schoolride;

public class Users {

    String FullName;
    String PhoneNumber;
    String UserEmails;

    public Users(){

    }

    public Users(String fullName, String phoneNumber, String userEmails) {
        FullName = fullName;
        PhoneNumber = phoneNumber;
        UserEmails = userEmails;
    }

    public String getFullName() {
        return FullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getUserEmails() {
        return UserEmails;
    }


    //

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setUserEmails(String userEmails) {
        UserEmails = userEmails;
    }
}
