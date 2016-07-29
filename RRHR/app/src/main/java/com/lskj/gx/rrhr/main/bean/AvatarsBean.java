package com.lskj.gx.rrhr.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AvatarsBean implements Parcelable {
    private String small;

    @Override
    public String toString() {
        return "AvatarsBean{" +
                "small='" + small + '\'' +
                ", large='" + large + '\'' +
                ", medium='" + medium + '\'' +
                '}';
    }

    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.small);
        dest.writeString(this.large);
        dest.writeString(this.medium);
    }

    public AvatarsBean() {
    }

    protected AvatarsBean(Parcel in) {
        this.small = in.readString();
        this.large = in.readString();
        this.medium = in.readString();
    }

    public static final Parcelable.Creator<AvatarsBean> CREATOR = new Parcelable.Creator<AvatarsBean>() {
        @Override
        public AvatarsBean createFromParcel(Parcel source) {
            return new AvatarsBean(source);
        }

        @Override
        public AvatarsBean[] newArray(int size) {
            return new AvatarsBean[size];
        }
    };
}