package org.techtown.transmanageradmin;

public class TransData {
    private String year, month, day, vihiclenumber, product, start, end, quantity, agency;
    private int id;

    public TransData(int id, String year, String month, String day, String vihiclenumber, String product,
                     String start, String end, String quantity, String agency) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.vihiclenumber = vihiclenumber;
        this.product = product;
        this.start = start;
        this.end = end;
        this.quantity = quantity;
        this.agency = agency;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getVihiclenumber() {
        return vihiclenumber;
    }

    public String getProduct() {
        return product;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setVihiclenumber(String vihiclenumber) {
        this.vihiclenumber = vihiclenumber;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
