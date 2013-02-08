package com.noctrl.birdeye.release;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.noctrl.birdeye.release.adapters.Lot;
import com.noctrl.birdeye.release.adapters.LotsAdapter;
import com.noctrl.birdeye.release.http.RestClient;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{

    public static final String LOG_TAG = "BirdseyePg1";

    private ListView listView;
    private LotsAdapter lotsAdapter;
    private List<Lot> lotsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.lotsList = new ArrayList<Lot>();

        this.lotsAdapter = new LotsAdapter(this, R.layout.main_scroll_entry, lotsList);

        this.listView = (ListView)findViewById(R.id.listView);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lot lot = (Lot)MainActivity.this.listView.getItemAtPosition(i);
//                MainActivity.this.displayLotActivity(lot.name);
//                Lot lot = MainActivity.this.lotsList.get(i);
                MainActivity.this.displayLotActivity(lot);
            }
        });
        listView.setAdapter(this.lotsAdapter);

        new LoadLotsTask().execute();
    }

    public void displayLotActivity(String lotName) {
        Intent intent = new Intent(this, LotActivity.class);
        intent.putExtra(LotActivity.ARGS_LOT_NAME, lotName);
        this.startActivity(intent);
    }

    public void displayLotActivity(Lot lot) {
        Intent intent = new Intent(this, LotActivity.class);
        intent.putExtra(LotActivity.ARGS_LOT, lot);
        this.startActivity(intent);
    }

    private class LoadLotsTask extends AsyncTask<Object, Object, ArrayList<Lot>> {
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
                MainActivity.this.lotsList.add(result.get(i));

            MainActivity.this.lotsAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Notified data set changed");
        }
    }
}
