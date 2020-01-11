package com.axel.akcosmetics.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SliderMain implements Parcelable {

    private String id;
    private String heading;
    private String preHeading;

    public SliderMain(String id, String heading, String preHeading) {
        this.id = id;
        this.heading = heading;
        this.preHeading = preHeading;
    }

    public String getId() { return id;}

    public String getHeading() { return heading;}

    public String getPreHeading() { return preHeading;}


    protected SliderMain(Parcel in) {
        id = in.readString();
        heading = in.readString();
        preHeading = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(heading);
        dest.writeString(preHeading);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SliderMain> CREATOR = new Parcelable.Creator<SliderMain>() {
        @Override
        public SliderMain createFromParcel(Parcel in) {
            return new SliderMain(in);
        }

        @Override
        public SliderMain[] newArray(int size) {
            return new SliderMain[size];
        }
    };


}
