package com.noctrl.birdeye.release;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.noctrl.birdeye.release.adapters.Lot;
import com.noctrl.birdeye.release.http.RestClient;
import org.json.JSONArray;

import java.util.ArrayList;

public class LotActivity extends Activity {

    public static final String LOG_TAG = "LotActivity";
    public static final String ARGS_LOT_NAME = "LOT_NAME";
    public static final String ARGS_LOT = "LOT";

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView spacesAvailableTextView;
    private TextView spacesTotalTextView;
    private ImageView lotImageView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lot_activity);

        this.titleTextView = (TextView)findViewById(R.id.lotNameTitleTextView);
        this.descriptionTextView = (TextView)findViewById(R.id.lotDescTextView);
        this.spacesAvailableTextView = (TextView)findViewById(R.id.bigSpacesAvailableTextView);
        this.spacesTotalTextView = (TextView)findViewById(R.id.bigSpacesTotalTextView);
        this.lotImageView = (ImageView)findViewById(R.id.lotImageView);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Lot lot = null;
        if(b != null)
            lot = b.getParcelable(LotActivity.ARGS_LOT);

        if(lot != null) {
            this.titleTextView.setText(lot.name);
            this.descriptionTextView.setText(lot.description);
            this.spacesAvailableTextView.setText(""+lot.spacesAvailable);
            this.spacesTotalTextView.setText(""+lot.spacesTotal);
        }

    }

    private class LoadLotImage extends AsyncTask<Object, Object, ArrayList<Lot>> {
        public static final String LOTS_RESOURCE = "http://api.birdseye.no-control.net/lots?all=true";

        @Override
        protected ArrayList<Lot> doInBackground(Object... objects) {
            RestClient rc = new RestClient(LOTS_RESOURCE);
            Object json = rc.get();
            JSONArray lotsJsonArray = (JSONArray)json;
            Log.d(LOG_TAG, "Cast to JSONArray done");
            return Lot.jsonArrayToLots(lotsJsonArray);
        }

        @Override
        protected void onPostExecute(ArrayList<Lot> result) {
            Log.d(LOG_TAG, "in onPostExecute: " + result.toString());
            for(int i = 0; i < result.size(); i++)
                return;

//            LotActivity.this.lotsAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Notified data set changed");
        }
    }

}