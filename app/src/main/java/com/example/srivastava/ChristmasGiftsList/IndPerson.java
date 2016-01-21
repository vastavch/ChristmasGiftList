package com.example.srivastava.ChristmasGiftsList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SRIVASTAVA on 1/18/2016.
 */
public class IndPerson implements Parcelable {
    String objectId,name,budget,bal,numofgifts;
    public IndPerson(String objectId, String name, String budget, String bal, String numofgifts) {
        this.objectId = objectId;
        this.name = name;
        this.budget = budget;
        this.bal = bal;
        this.numofgifts = numofgifts;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public String getBudget() {
        return budget;
    }

    public String getBal() {
        return bal;
    }

    public String getNumofgifts() {
        return numofgifts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(name);
        dest.writeString(budget);
        dest.writeString(bal);
        dest.writeString(numofgifts);

    }
    public static final Parcelable.Creator<IndPerson> CREATOR
            = new Parcelable.Creator<IndPerson>() {
        public IndPerson createFromParcel(Parcel in) {
            return new IndPerson(in);
        }

        public IndPerson[] newArray(int size) {
            return new IndPerson[size];
        }
    };

    private IndPerson(Parcel in) {
        this.objectId=in.readString();
        this.name=in.readString();
        this.budget=in.readString();
        this.bal=in.readString();
        this.numofgifts=in.readString();
    }
}
