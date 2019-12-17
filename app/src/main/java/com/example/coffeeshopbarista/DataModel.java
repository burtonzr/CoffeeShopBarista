package com.example.coffeeshopbarista;

public class DataModel {
    private String CoffeeName;
    private String CoffeePrice;
    private String MilkType;
    private String Size;
    private String Status;
    private String UserID;
    private int Quantity;
    private int OrderID;

    public DataModel(String CoffeeName, String CoffeePrice, String MilkType, String Size, int Quantity, int OrderID, String Status, String UserID) {
        this.CoffeeName     = CoffeeName;
        this.CoffeePrice    = CoffeePrice;
        this.MilkType       = MilkType;
        this.Size           = Size;
        this.Quantity       = Quantity;
        this.OrderID        = OrderID;
        this.Status         = Status;
        this.UserID         = UserID;
    }

    public String getCoffeeName() {
        return CoffeeName;
    }

    public String getCoffeePrice() {
        return CoffeePrice;
    }

    public String getMilkType() {
        return MilkType;
    }

    public String getSize() {
        return Size;
    }

    public String getUserID() {
        return UserID;
    }

    public String getStatus() {
        return Status;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getOrderID() {
        return OrderID;
    }
}
