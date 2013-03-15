package com.noctrl.birdeye.release.adapters;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lot implements Parcelable{

    public static final String LOG_TAG = "Lot";

    public Integer id;
    public String name;
    public Integer spacesAvailable;
    public Integer spacesTotal;
    public String description;
    public String location;
    public String imageUrl;

    public Lot() {
        super();
    }

    public Lot(JSONObject jsonObject) {
        try {
            Log.d(LOG_TAG, jsonObject.toString());
            this.id = jsonObject.getInt("lot_id");
            this.name = jsonObject.getString("name");
            this.spacesAvailable = jsonObject.getInt("spaces_available");
            this.spacesTotal = jsonObject.getInt("total_spaces");
            this.description = jsonObject.getString("description");
            this.location = jsonObject.getString("location");
            this.imageUrl = jsonObject.getString("image_url");
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Malformed JSON: " + jsonObject, e);
            this.name = "Broken";
            this.spacesAvailable = -1;
            this.spacesTotal = -1;
        }
    }

    public Lot(String name, int spacesAvailable, int spacesTotal) {
        this.name = name;
        this.spacesAvailable = spacesAvailable;
        this.spacesTotal = spacesTotal;
    }

    public Lot(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.location = in.readString();
        this.spacesAvailable = in.readInt();
        this.spacesTotal = in.readInt();
        this.imageUrl = in.readString();
    }

    @Override
    public String toString() {
        return "<Lot id="+this.id+", name="+this.name+", description="+this.description+", location="+location
               +", spacesAvailable="+this.spacesAvailable+", spacesTotal="+this.spacesTotal+", "+this.imageUrl
               +", location=" + this.location + ">";
    }

    public static ArrayList<Lot> jsonArrayToLots(JSONArray jsonArray) {
        ArrayList<Lot>lotsList = new ArrayList<Lot>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject o = (JSONObject)jsonArray.get(i);
                lotsList.add(new Lot(o));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Json exception. Trying to get element " + i + " from json array: " + jsonArray, e);
            }
        }
        return lotsList;
    }

    @Override
    public int describeContents() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.id);
        out.writeString(this.name);
        out.writeString(this.description);
        out.writeString(this.location);
        out.writeInt(this.spacesAvailable);
        out.writeInt(this.spacesTotal);
        out.writeString(this.imageUrl);
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Lot createFromParcel(Parcel in) {
                    return new Lot(in);
                }

                public Lot[] newArray(int size) {
                    return new Lot[size];
                }
            };
}
