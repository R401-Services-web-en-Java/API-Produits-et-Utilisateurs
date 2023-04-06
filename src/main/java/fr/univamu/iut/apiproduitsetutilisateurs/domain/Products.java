package fr.univamu.iut.apiproduitsetutilisateurs.domain;

public class Products {
    protected String name;

    protected int quantity_stock;

    protected float price;

    protected String unit;

    public Products() {}

    public Products(String name, int quantity_stock, float price, String unit) {
        this.name = name;
        this.quantity_stock = quantity_stock;
        this.price = price;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity_stock() {
        return quantity_stock;
    }

    public void setQuantity_stock(int quantity_stock) {
        this.quantity_stock = quantity_stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
