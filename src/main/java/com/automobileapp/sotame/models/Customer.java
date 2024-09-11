package com.automobileapp.sotame.models;

import java.time.LocalDate;

public class Customer {
    // Class variables
    private int idCustomer;
    private String firstNameCustomer;
    private String lastNameCustomer;
    private String emailCustomer;
    private String phoneCustomer;
    private String addressCustomer;
    private String cityCustomer;
    private String provinceCustomer;
    private String zipCustomer;
    private String countryCustomer;
    private CustomerType customerType;
    private LocalDate dateOfBirthCustomer;

    // Constructor

    /**
     * Default constructor
     */
    public Customer() {}

    /**
     * Customer constructor with all fields defined
     * @param idCustomer
     * @param firstNameCustomer
     * @param lastNameCustomer
     * @param emailCustomer
     * @param phoneCustomer
     * @param addressCustomer
     * @param cityCustomer
     * @param provinceCustomer
     * @param zipCustomer
     * @param countryCustomer
     * @param customerType
     * @param dateOfBirthCustomer
     */
    public Customer(int idCustomer,
                    String firstNameCustomer,
                    String lastNameCustomer,
                    String emailCustomer,
                    String phoneCustomer,
                    String addressCustomer,
                    String cityCustomer,
                    String provinceCustomer,
                    String zipCustomer,
                    String countryCustomer,
                    CustomerType customerType,
                    LocalDate dateOfBirthCustomer) {
        this.idCustomer = idCustomer;
        this.firstNameCustomer = firstNameCustomer;
        this.lastNameCustomer = lastNameCustomer;
        this.emailCustomer = emailCustomer;
        this.phoneCustomer = phoneCustomer;
        this.addressCustomer = addressCustomer;
        this.cityCustomer = cityCustomer;
        this.provinceCustomer = provinceCustomer;
        this.zipCustomer = zipCustomer;
        this.countryCustomer = countryCustomer;
        this.customerType = customerType;
        this.dateOfBirthCustomer = dateOfBirthCustomer;
    }

    public Customer(int idCustomer,String firstNameCustomer, String lastNameCustomer)
    {
        this.idCustomer = idCustomer;
        this.firstNameCustomer = firstNameCustomer;
        this.lastNameCustomer = lastNameCustomer;
    }

    // Getters and setters


    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getFirstNameCustomer() {
        return firstNameCustomer;
    }

    public void setFirstNameCustomer(String firstNameCustomer) {
        this.firstNameCustomer = firstNameCustomer;
    }

    public String getLastNameCustomer() {
        return lastNameCustomer;
    }

    public void setLastNameCustomer(String lastNameCustomer) {
        this.lastNameCustomer = lastNameCustomer;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public String getPhoneCustomer() {
        return phoneCustomer;
    }

    public void setPhoneCustomer(String phoneCustomer) {
        this.phoneCustomer = phoneCustomer;
    }

    public String getAddressCustomer() {
        return addressCustomer;
    }

    public void setAddressCustomer(String addressCustomer) {
        this.addressCustomer = addressCustomer;
    }

    public String getCityCustomer() {
        return cityCustomer;
    }

    public void setCityCustomer(String cityCustomer) {
        this.cityCustomer = cityCustomer;
    }

    public String getProvinceCustomer() {
        return provinceCustomer;
    }

    public void setProvinceCustomer(String provinceCustomer) {
        this.provinceCustomer = provinceCustomer;
    }

    public String getZipCustomer() {
        return zipCustomer;
    }

    public void setZipCustomer(String zipCustomer) {
        this.zipCustomer = zipCustomer;
    }

    public String getCountryCustomer() {
        return countryCustomer;
    }

    public void setCountryCustomer(String countryCustomer) {
        this.countryCustomer = countryCustomer;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public LocalDate getDateOfBirthCustomer() {
        return dateOfBirthCustomer;
    }

    public void setDateOfBirthCustomer(LocalDate dateOfBirthCustomer) {
        this.dateOfBirthCustomer = dateOfBirthCustomer;
    }
}

