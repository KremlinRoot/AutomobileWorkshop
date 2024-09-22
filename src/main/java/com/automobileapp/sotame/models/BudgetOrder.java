package com.automobileapp.sotame.models;

public class BudgetOrder {
    private int idBudgetOrder;
    private Order order; // Order related to the budget
    private double laborHours; // Laboured hours of the order
    private double partCost; // Cost related all stock item used in the order

    public BudgetOrder(){} // Default constructor

    /**
     * Full constructor
     * @param order
     * @param laborHours
     * @param partCost
     */
    public BudgetOrder(int idBudgetOrder, Order order, double laborHours, double partCost) {
        this.idBudgetOrder = idBudgetOrder;
        this.order = order;
        this.laborHours = laborHours;
        this.partCost = partCost;
    }
    // Getters and setters

    public Order getOrder()    {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(double laborHours) {
        this.laborHours = laborHours;
    }

    public double getPartCost() {
        return partCost;
    }

    public void setPartCost(double partCost) {
        this.partCost = partCost;
    }
    @Override
    public String toString() {
        // Devuelve una representación legible de la orden de presupuesto
        return "Orden: " + order.getOrderNumber() + ", Horas de trabajo: " + laborHours + ", Costo de piezas: $" + partCost;
    }
    public String getOrderNumber() {
        return order != null ? this.order.getOrderNumber() : "N/A";
    }

    public int getIdBudgetOrder() {
        return idBudgetOrder;
    }

    public void setIdBudgetOrder(int idBudgetOrder) {
        this.idBudgetOrder = idBudgetOrder;
    }
}
