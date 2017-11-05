package com.assignment.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Summary : Location pojo
 */
public class Location implements Parcelable {

    private String id;
    private String name;
    private String lattitude;
    private String longitude;
    private String bycar;
    private String byTrain;


    public Location(String id, String name, String byCar, String byTrain, String longitude, String lattitude) {
        this.id = id;
        this.byTrain = byTrain;
        this.bycar = byCar;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBycar() {
        return bycar;
    }

    public void setBycar(String bycar) {
        this.bycar = bycar;
    }

    public String getByTrain() {
        return byTrain;
    }

    public void setByTrain(String byTrain) {
        this.byTrain = byTrain;
    }

    /**
     * ArrayAdapter will read the toString of the given object for the name
     *
     * @return name
     */
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // Parcelling part
    public Location(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.lattitude = in.readString();
        this.longitude = in.readString();
        this.bycar = in.readString();
        this.byTrain = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.lattitude);
        dest.writeString(this.longitude);
        dest.writeString(this.bycar);
        dest.writeString(this.byTrain);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
