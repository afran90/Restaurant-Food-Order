package com.example.user.foodorder;

/**
 * Created by User on 2/1/2018.
 */

public class Food {



        public String name,price,image,desc;

        public Food(){

        }
        public Food(String name, String price, String image, String desc) {

            this.name = name;
            this.price = price;
            this.image = image;
            this.desc = desc;

        }

        public String getDesc() {
            return desc;
        }

        public String getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

    }


