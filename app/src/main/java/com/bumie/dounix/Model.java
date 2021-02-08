package com.bumie.dounix;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("id")
    @Expose
    String id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("image")
    @Expose
    String image;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("price")
    @Expose
    String price;
    @SerializedName("category")
    @Expose
    String category;

    public Model(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Model(String image, String id) {
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Post{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description=" + description + '\''+
                ", price=" + price + '\''+
                ", category=" + category +
                '}';
    }
}
