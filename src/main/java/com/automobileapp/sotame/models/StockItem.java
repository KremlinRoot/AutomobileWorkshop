package com.automobileapp.sotame.models;

public class StockItem {
    // Class variables
    private int idStockItem;
    private String productName;
    private String productCode;
    private Category category;
    private int quantityInStock;
    private int minumunQunantityInStock;
    private double unitPrice;
    private String Supplier;
    private double totalCost;
    private String notes;

    // Constructor
    public StockItem() {} // Default constructor
    public StockItem(int idStockItem,String productName, String productCode,
                     Category category, int quantityInStock, int minumunQunantityInStock,
                     double unitPrice, String Supplier, String notes) {
        this.idStockItem = idStockItem;
        this.productName = productName;
        this.productCode = productCode;
        this.category = category;
        this.quantityInStock = quantityInStock;
        this.minumunQunantityInStock = minumunQunantityInStock;
        this.unitPrice = unitPrice;
        this.Supplier = Supplier;
        this.totalCost = quantityInStock * unitPrice;
        this.notes = notes;
    }

    // Getter and setter methods


    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getIdStockItem() {
        return idStockItem;
    }

    public void setIdStockItem(int idStockItem) {
        this.idStockItem = idStockItem;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getMinumunQunantityInStock() {
        return minumunQunantityInStock;
    }

    public void setMinumunQunantityInStock(int minumunQunantityInStock) {
        this.minumunQunantityInStock = minumunQunantityInStock;
    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
