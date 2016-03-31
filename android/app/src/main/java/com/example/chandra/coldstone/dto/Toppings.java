package com.example.chandra.coldstone.dto;

/**
 * Created by Raghuveer on 3/24/2016.
 */
public class Toppings {

    private String name;
    private int id;
    private float price;
    private String url;

    public Toppings(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "Toppings{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }
}
