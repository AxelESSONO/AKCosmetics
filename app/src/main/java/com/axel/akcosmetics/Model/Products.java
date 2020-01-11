package com.axel.akcosmetics.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Products implements Parcelable
{

    private  String pname, description, price, image, category, pid, date, time, productState;


    private double previousPrice;

    public Products(String pname, String price, String pid) {
        this.pname = pname;
        this.price = price;
        this.pid = pid;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }
    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }


    //public Products(String product_7, String round_neck_tshirt, double v) { }

    public Products(String pname, String description, String price, String image, String category, String pid, String date, String time, String productState)
    {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.productState = productState;
    }
    public String getPname() { return pname; }
    public void setPname(String pname) { this.pname = pname; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPid() { return pid; }
    public void setPid(String pid) { this.pid = pid; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getProductState() {return productState; }
    public void setProductState(String productState) { this.productState = productState; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.pid);
        dest.writeString(this.pname);
        dest.writeString(this.price);
        dest.writeDouble(this.previousPrice);
    }

    protected Products(Parcel in) {
        this.pid = in.readString();
        this.pname = in.readString();
        this.price = in.readString();
        this.previousPrice = in.readDouble();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel source) {
            return new Products(source);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}
