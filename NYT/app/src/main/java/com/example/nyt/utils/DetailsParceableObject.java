package com.example.nyt.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsParceableObject implements Parcelable {

    String title;
    String subText;
    String publishedDate;
    String byLine;
    String jsonAbstarct;
    String id;
    String image;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }


    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    public void setJsonAbstarct(String jsonAbstarct) {
        this.jsonAbstarct = jsonAbstarct;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubText() {
        return subText;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getByLine() {
        return byLine;
    }

    public String getJsonAbstarct() {
        return jsonAbstarct;
    }

    public String getId() {
        return id;
    }

    public DetailsParceableObject(String title, String subText, String publishedDate, String jsonAbstarct, String byLine, String id, String image) {
        this.title = title;
        this.subText = subText;
        this.publishedDate = publishedDate;
        this.jsonAbstarct = jsonAbstarct;
        this.byLine = byLine;
        this.id = id;
        this.image = image;
    }


    //parcel part
    public DetailsParceableObject(Parcel in) {
        String[] data = new String[7];

        in.readStringArray(data);
        title = data[0];
        subText = data[1];
        publishedDate = data[2];
        jsonAbstarct = data[3];
        byLine = data[4];
        id = data[5];
        image = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{this.title, this.subText, this.publishedDate, this.jsonAbstarct, this.byLine, this.id, this.image});
    }

    public static final Creator<DetailsParceableObject> CREATOR = new Creator<DetailsParceableObject>() {

        @Override
        public DetailsParceableObject createFromParcel(Parcel source) {
            return new DetailsParceableObject(source);  //using parcelable constructor
        }

        @Override
        public DetailsParceableObject[] newArray(int size) {
            return new DetailsParceableObject[size];
        }
    };
}
