package com.example.chandra.coldstone;

/**
 * Created by Raghuveer on 2/9/2016.
 */
public class Bill {

    String weight;
    String status;
    double price;
    String time;
    String date;
    int id;

    @Override
    public String toString() {
        return "Bill{" +
                "weight='" + weight + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
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
}
