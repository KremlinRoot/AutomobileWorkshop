package com.automobileapp.sotame.models;

import java.time.LocalDate;

public class Automobile {
    // Class variables
    private int idAutomobile;
    private String manufacturer;
    private String model;
    private String yearManufactured;
    private String color;
    private String numberPlate;
    private String numberVin;
    private int kilometers;
    private Fuel fuelType;
    private String currentState;
    private LocalDate lastMaintenance;
    private LocalDate nextMaintenance;
    // constructors

    /**
     * Default constructor
     */
    public Automobile() {}

    /**
     * Create a new automobile with all fields
     * @param idAutomobile
     * @param manufacturer
     * @param model
     * @param yearManufactured
     * @param color
     * @param numberPlate
     * @param numberVin
     * @param kilometers
     * @param fuelType
     * @param currentState
     * @param lastMaintenance
     * @param nextMaintenance
     */
    public Automobile(int idAutomobile,
                      String manufacturer,
                      String model,
                      String yearManufactured,
                      String color,
                      String numberPlate,
                      String numberVin,
                      int kilometers,
                      Fuel fuelType,
                      String currentState,
                      LocalDate lastMaintenance,
                      LocalDate nextMaintenance){
        this.idAutomobile = idAutomobile;
        this.manufacturer = manufacturer;
        this.model = model;
        this.yearManufactured = yearManufactured;
        this.color = color;
        this.numberPlate = numberPlate;
        this.numberVin = numberVin;
        this.kilometers = kilometers;
        this.fuelType = fuelType;
        this.currentState = currentState;
        this.lastMaintenance = lastMaintenance;
        this.nextMaintenance = nextMaintenance;
    }

    // Getters and setters


    public int getIdAutomobile() {
        return idAutomobile;
    }

    public void setIdAutomobile(int idAutomobile) {
        this.idAutomobile = idAutomobile;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(String yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getNumberVin() {
        return numberVin;
    }

    public void setNumberVin(String numberVin) {
        this.numberVin = numberVin;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public Fuel getFuelType() {
        return fuelType;
    }

    public void setFuelType(Fuel fuelType) {
        this.fuelType = fuelType;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public LocalDate getLastMaintenance() {
        return lastMaintenance;
    }

    public void setLastMaintenance(LocalDate lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
    }

    public LocalDate getNextMaintenance() {
        return nextMaintenance;
    }

    public void setNextMaintenance(LocalDate nextMaintenance) {
        this.nextMaintenance = nextMaintenance;
    }
}

