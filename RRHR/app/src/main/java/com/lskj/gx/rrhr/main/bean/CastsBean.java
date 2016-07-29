package com.lskj.gx.rrhr.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CastsBean implements Parcelable {
    private String alt;

    @Override
    public String toString() {
        return "CastsBean{" +
                "alt='" + alt + '\'' +
                ", avatars=" + avatars +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    /**
     * small : https://img3.doubanio.com/img/celebrity/small/8833.jpg
     * large : https://img3.doubanio.com/img/celebrity/large/8833.jpg
     * medium : https://img3.doubanio.com/img/celebrity/medium/8833.jpg
     */

    private AvatarsBean avatars;
    private String name;
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

    public CastsBean() {
    }

    protected CastsBean(Parcel in) {
        this.alt = in.readString();
        this.avatars = in.readParcelable(AvatarsBean.class.getClassLoader());
        this.name = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<CastsBean> CREATOR = new Parcelable.Creator<CastsBean>() {
        @Override
        public CastsBean createFromParcel(Parcel source) {
            return new CastsBean(source);
        }

        @Override
        public CastsBean[] newArray(int size) {
            return new CastsBean[size];
        }
    };
}
