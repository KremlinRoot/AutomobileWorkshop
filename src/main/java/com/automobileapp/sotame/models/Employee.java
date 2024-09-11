package com.automobileapp.sotame.models;

public class Employee {
    // Employee's class attributes
    private int idEmployee;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String jobTitle;
    private double workedHours;

    // Constructors
    public Employee(){}
    /**
     * Full constructor for class employee
     * @param idEmployee
     * @param firstName
     * @param lastName
     * @param email
     * @param phone
     * @param jobTitle
     * @param workedHours
     */
    public Employee(int idEmployee, String firstName, String lastName, String email, String phone, String jobTitle, double workedHours){
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.workedHours = workedHours;
    }

    /**
     * default constructor for employee clas, no needs parameters
     */

    // Getters and Setter
    public void setIdEmployee(int idEmployee){ this.idEmployee = idEmployee; }
    public int getIdEmployee(){return idEmployee;}
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public String getFirstName(){return firstName;}
    public void setLastName(String lastName){ this.lastName = lastName; }
    public String getLastName(){return lastName;}
    public void setEmail(String email){ this.email = email; }
    public String getEmail(){return email;}
    public void setPhone(String phone){ this.phone = phone; }
    public String getPhone(){return phone;}
    public void setJobTitle(String jobTitle){ this.jobTitle = jobTitle; }
    public String getJobTitle(){return jobTitle;}
    public void setWorkedHours(double workedHours){ this.workedHours = workedHours; }
    public double getWorkedHours(){return workedHours;}
    // Methods

    /**
     * function to add work hour to employee, no return nothing
     * @param workedHours
     */
    public void addWorkedHours(double workedHours){ this.workedHours += workedHours; }
    /**
     * Removes worked hours from the employee's total worked hours.
     *
     * @param workedHours the hours to be removed
     */
    public void removeWorkedHours(double workedHours){this.workedHours -= workedHours;}
}
