package com.automobileapp.sotame.models;

public class Supplier {
    private int idSupplier;
    private String firstNameSupplier;
    private String lastNameSupplier;
    private String emailSupplier;
    private String phoneSupplier;
    private String addressSupplier;
    private String citySupplier;
    private String provinceSupplier;
    private String zipSupplier;
    private String countrySupplier;
    private String companyNameSupplier;

    public Supplier(int idSupplier,String firstNameSupplier, String lastNameSupplier, String emailSupplier, String phoneSupplier, String addressSupplier, String citySupplier, String stateSupplier, String zipSupplier, String countrySupplier, String companyNameSupplier){
        this.idSupplier = idSupplier;
        this.firstNameSupplier = firstNameSupplier;
        this.lastNameSupplier = lastNameSupplier;
        this.emailSupplier = emailSupplier;
        this.phoneSupplier = phoneSupplier;
        this.addressSupplier = addressSupplier;
        this.citySupplier = citySupplier;
        this.provinceSupplier = stateSupplier;
        this.zipSupplier = zipSupplier;
        this.countrySupplier = countrySupplier;
        this.companyNameSupplier = companyNameSupplier;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getFirstNameSupplier() {
        return firstNameSupplier;
    }

    public void setFirstNameSupplier(String firstNameSupplier) {
        this.firstNameSupplier = firstNameSupplier;
    }

    public String getLastNameSupplier() {
        return lastNameSupplier;
    }

    public void setLastNameSupplier(String lastNameSupplier) {
        this.lastNameSupplier = lastNameSupplier;
    }

    public String getEmailSupplier() {
        return emailSupplier;
    }

    public void setEmailSupplier(String emailSupplier) {
        this.emailSupplier = emailSupplier;
    }

    public String getPhoneSupplier() {
        return phoneSupplier;
    }

    public void setPhoneSupplier(String phoneSupplier) {
        this.phoneSupplier = phoneSupplier;
    }

    public String getAddressSupplier() {
        return addressSupplier;
    }

    public void setAddressSupplier(String addressSupplier) {
        this.addressSupplier = addressSupplier;
    }

    public String getCitySupplier() {
        return citySupplier;
    }

    public void setCitySupplier(String citySupplier) {
        this.citySupplier = citySupplier;
    }

    public String getProvinceSupplier() {
        return provinceSupplier;
    }

    public void setProvinceSupplier(String provinceSupplier) {
        this.provinceSupplier = provinceSupplier;
    }

    public String getZipSupplier() {
        return zipSupplier;
    }

    public void setZipSupplier(String zipSupplier) {
        this.zipSupplier = zipSupplier;
    }

    public String getCountrySupplier() {
        return countrySupplier;
    }

    public void setCountrySupplier(String countrySupplier) {
        this.countrySupplier = countrySupplier;
    }

    public String getCompanyNameSupplier() {
        return companyNameSupplier;
    }

    public void setCompanyNameSupplier(String companyNameSupplier) {
        this.companyNameSupplier = companyNameSupplier;
    }
}
