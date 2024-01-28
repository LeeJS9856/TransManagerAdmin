package org.techtown.transmanageradmin;

public class PriceData {
    private String agency, price;
    public PriceData(String agency, String price) {
        this.agency = agency;
        this.price = price;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
