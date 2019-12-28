package com.axel.akcosmetics.Sellers;

public class Seller
{
    private String nameSeller, phoneSeller, passwordSeller, provinceSeller, citySeller, quarterSeller;

    public Seller(String nameSeller, String phoneSeller, String passwordSeller, String provinceSeller, String citySeller, String quarterSeller)
    {
        this.nameSeller = nameSeller;
        this.phoneSeller = phoneSeller;
        this.passwordSeller = passwordSeller;
        this.provinceSeller = provinceSeller;
        this.citySeller = citySeller;
        this.quarterSeller = quarterSeller;
    }

    public String getNameSeller() {
        return nameSeller;
    }

    public void setNameSeller(String nameSeller) {
        this.nameSeller = nameSeller;
    }

    public String getPhoneSeller() {
        return phoneSeller;
    }

    public void setPhoneSeller(String phoneSeller) {
        this.phoneSeller = phoneSeller;
    }

    public String getPasswordSeller() {
        return passwordSeller;
    }

    public void setPasswordSeller(String passwordSeller) {
        this.passwordSeller = passwordSeller;
    }

    public String getProvinceSeller() {
        return provinceSeller;
    }

    public void setProvinceSeller(String provinceSeller) {
        this.provinceSeller = provinceSeller;
    }

    public String getCitySeller() {
        return citySeller;
    }

    public void setCitySeller(String citySeller) {
        this.citySeller = citySeller;
    }

    public String getQuarterSeller() {
        return quarterSeller;
    }

    public void setQuarterSeller(String quarterSeller) {
        this.quarterSeller = quarterSeller;
    }
}
