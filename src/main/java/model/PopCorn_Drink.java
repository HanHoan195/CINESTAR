package model;

import repository.IModel;

public class PopCorn_Drink implements IModel<PopCorn_Drink> {
    private long id;
    private String name;
    private double price;

    public PopCorn_Drink() {
    }

    public PopCorn_Drink(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    @Override
    public void update(PopCorn_Drink obj) {

    }

    @Override
    public PopCorn_Drink parseData(String line) {
        return null;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public PopCorn_Drink(String raw) {
        String[] strings = raw.split(",");
        this.id = Long.parseLong(strings[0]);
        this.name = strings[1];
        this.price = Double.parseDouble(strings[2]);
    }

    @Override
    public String toString() {
        return id+ "," + name + "," + price;
    }
}
