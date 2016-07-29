package com.lskj.gx.rrhr.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DirectorsBean implements Parcelable {
    private String alt;
    /**
     * small : https://img3.doubanio.com/img/celebrity/small/33301.jpg
     * large : https://img3.doubanio.com/img/celebrity/large/33301.jpg
     * medium : https://img3.doubanio.com/img/celebrity/medium/33301.jpg
     */

    private AvatarsBean avatars;
    private String name;

    @Override
    public String toString() {
        return "DirectorsBean{" +
                "alt='" + alt + '\'' +
                ", avatars=" + avatars +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public AvatarsBean getAvatars() {
        return avatars;
    }

    public void setAvatars(AvatarsBean avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(this.alt);
        dest.writeParcelable(this.avatars, flags);
        dest.writeString(this.name);
        dest.writeString(this.id);
    }

    public DirectorsBean() {
    }

    protected DirectorsBean(Parcel in) {
        this.alt = in.readString();
        this.avatars = in.readParcelable(AvatarsBean.class.getClassLoader());
        this.name = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<DirectorsBean> CREATOR = new Parcelable.Creator<DirectorsBean>() {
        @Override
        public DirectorsBean createFromParcel(Parcel source) {
            return new DirectorsBean(source);
        }

        @Override
        public DirectorsBean[] newArray(int size) {
            return new DirectorsBean[size];
        }
    };
}