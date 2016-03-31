package com.example.chandra.coldstone.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.LinkedHashSet;

/**
 * Created by Raghuveer on 2/9/2016.
 */
public class Bill implements Serializable {

    String weight;
    String status;
    double price;
    String time;
    String date;
    int id;
    LinkedHashSet<String> selectedToppings;
    double toppingsPrice;

    public Bill() {

    }

    @Override
    public String toString() {
        return "Bill{" +
                "weight='" + weight + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", id=" + id +
                ", selectedToppings=" + selectedToppings +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LinkedHashSet<String> getSelectedToppings() {
        return selectedToppings;
    }

    public void setSelectedToppings(LinkedHashSet<String> selectedToppings) {
        this.selectedToppings = selectedToppings;
    }

    public double getToppingsPrice() {
        return toppingsPrice;
    }

    public void setToppingsPrice(double toppingsPrice) {
        this.toppingsPrice = toppingsPrice;
    }


}
