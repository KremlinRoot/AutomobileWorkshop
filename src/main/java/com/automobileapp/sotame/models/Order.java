package com.automobileapp.sotame.models;

import java.time.LocalDate;

public class Order {
    // Class variables
    private int idOrder;
    private String orderNumber;
    private LocalDate orderDate;
    private LocalDate estimatedCompletionDate;
    private String workDescription;
    private double totalCost;
    private StatusOrder statusOrder;
    private Automobile automobileOfOrder;
    private Customer customerOfOrder;

    // Constructor

    /**
     * Default constructor
     */
    public Order(){}

    /**
     * Creates a new order with all the parameters
     * @param idOrder
     * @param orderNumber
     * @param orderDate
     * @param estimatedCompletionDate
     * @param workDescription
     * @param totalCost
     * @param statusOrder
     * @param employeeOfOrder
     * @param automobileOfOrder
     * @param customerOfOrder
     */
    public Order(int idOrder,
                 String orderNumber,
                 LocalDate orderDate,
                 LocalDate estimatedCompletionDate,
                 String workDescription,
                 double totalCost,
                 StatusOrder statusOrder,
                 Automobile automobileOfOrder,
                 Customer customerOfOrder) {
        this.idOrder = idOrder;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.estimatedCompletionDate = estimatedCompletionDate;
        this.workDescription = workDescription;
        this.totalCost = totalCost;
        this.statusOrder = statusOrder;
        this.automobileOfOrder = automobileOfOrder;
        this.customerOfOrder = customerOfOrder;
    }

    // Getters and setters

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getEstimatedCompletionDate() {
        return estimatedCompletionDate;
    }

    public void setEstimatedCompletionDate(LocalDate estimatedCompletionDate) {
        this.estimatedCompletionDate = estimatedCompletionDate;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }



    public Automobile getAutomobileOfOrder() {
        return automobileOfOrder;
    }

    public void setAutomobileOfOrder(Automobile automobileOfOrder) {
        this.automobileOfOrder = automobileOfOrder;
    }

    public Customer getCustomerOfOrder() {
        return customerOfOrder;
    }

    public void setCustomerOfOrder(Customer customerOfOrder) {
        this.customerOfOrder = customerOfOrder;
    }
}
