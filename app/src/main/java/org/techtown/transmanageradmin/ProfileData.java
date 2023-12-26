package org.techtown.transmanageradmin;

public class ProfileData {
    private String username, phonenumber, vihiclenumber;

    public ProfileData(String username, String phonenumber,String vihiclenumber) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.vihiclenumber = vihiclenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getVihiclenumber() {
        return vihiclenumber;
    }

    public void setVihiclenumber(String vihiclenumber) {
        this.vihiclenumber = vihiclenumber;
    }
}
