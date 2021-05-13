package com.cs478.services;

import android.os.Parcel;
import android.os.Parcelable;

public class ExampleObject implements Parcelable {

    private int mNumber;
    private String mString1;
    private String mString2;

    public ExampleObject()
    {
        mNumber = -999;
        mString1 = "default";
        mString2 = "default";
    }

    public ExampleObject(int number, String string1, String string2)
    {
        mNumber = number;
        mString1 = string1;
        mString2 = string2;
    }

    protected ExampleObject(Parcel in) {
        mNumber = in.readInt();
        mString1 = in.readString();
        mString2 = in.readString();
    }

    public static final Creator<ExampleObject> CREATOR = new Creator<ExampleObject>() {
        @Override
        public ExampleObject createFromParcel(Parcel in) {
            return new ExampleObject(in);
        }

        @Override
        public ExampleObject[] newArray(int size) {
            return new ExampleObject[size];
        }
    };

    public int getNumber()
    {
        return mNumber;
    }

    public void setNumber(int number){mNumber = number;};

    public String getString1()
    {
        return mString1;
    }

    public void setString1(String s){mString1 = s;};

    public String getString2()
    {
        return mString2;
    }

    public void setString2(String s){mString2 = s;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mNumber);
        dest.writeString(mString1);
        dest.writeString(mString2);
    }

    public void readFromParcel(Parcel in){
        mNumber = in.readInt();
        mString1 = in.readString();
        mString2 = in.readString();
    }


}