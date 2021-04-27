package com.maad.buyfromcandroiduser;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {

    private String title;
    private String description;
    private double price;
    private int quantity;
    private String category;
    private String image;
    private String id;

    //Required Empty Constructor for reading from firebase
    public ProductModel(){}

    //This constructor is used when adding products to basket
    public ProductModel(String image, int quantity, String title){
        this.image = image;
        this.quantity = quantity;
        this.title = title;
    }

    protected ProductModel(Parcel in) {
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
        category = in.readString();
        image = in.readString();
        id = in.readString();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeInt(quantity);
        dest.writeString(category);
        dest.writeString(image);
        dest.writeString(id);
    }

}
