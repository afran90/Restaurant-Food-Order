package com.example.user.foodorder;

/**
 * Created by User on 2/3/2018.
 */

public class Order {
    private String username,itemname;

    public Order(){

    }
    public Order (String username, String itemname) {

        this.username = username;
        this.itemname = itemname;
    }

    public String getItemname() {
        return itemname;
    }

    public String getUsername() {
        return username;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
