package com.noctrl.birdeye.release.adapters;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lot {

    public static final String LOG_TAG = "Lot";

    public String name;
    public Integer spacesAvailable;
    public Integer spacesTotal;

    public Lot() {
        super();
    }

    public Lot(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.spacesAvailable = jsonObject.getInt("spaces_available");
            this.spacesTotal = jsonObject.getInt("total_spaces");
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

    @Override
    public String toString() {
        return "<Lot name="+this.name+", spacesAvailable="+this.spacesAvailable+", spacesTotal="+this.spacesTotal+">";
    }

    public static ArrayList<Lot> jsonArrayToLots(JSONArray jsonArray) {
        Log.d(LOG_TAG, "In jsonArrayToLots");
        ArrayList<Lot>lotsList = new ArrayList<Lot>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Log.d(LOG_TAG, "Iteration #"+i);
            try {
                JSONObject o = (JSONObject)jsonArray.get(i);
                lotsList.add(new Lot(o));
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Json exception. Trying to get element " + i + " from json array: " + jsonArray, e);
            }
        }
        Log.d(LOG_TAG, "Exiting jsonArrayToLots");
        return lotsList;
    }
}
