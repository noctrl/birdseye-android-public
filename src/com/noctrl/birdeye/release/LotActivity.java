package com.noctrl.birdeye.release;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.noctrl.birdeye.release.http.RestClient;
import org.json.JSONArray;

public class LotActivity extends Activity {

    public static final String LOG_TAG = "LotActivity";
    public static final String ARGS_LOT_NAME = "LOT_NAME";

    private TextView tv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lot_activity);

        this.tv = (TextView)findViewById(R.id.textView);
        new LoadLotsTask().execute();
    }

    private class LoadLotsTask extends AsyncTask<Object, Object, JSONArray> {
        public static final String LOTS_RESOURCE = "http://api.birdseye.no-control.net/lots";
        private JSONArray lotsJsonArray;

        @Override
        protected JSONArray doInBackground(Object... objects) {
            RestClient rc = new RestClient(LOTS_RESOURCE);
            Object json = rc.get();
            this.lotsJsonArray = (JSONArray)json;
            return this.lotsJsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            Log.d(LOG_TAG, "in onPostExecute: " + result.toString());
            LotActivity.this.tv.setText(result.toString());
        }
    }
}