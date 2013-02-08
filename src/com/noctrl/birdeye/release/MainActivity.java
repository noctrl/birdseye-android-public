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
//    private Lot lots[];
    private List<Lot> lotsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        this.lots = new Lot[0];
        this.lotsList = new ArrayList<Lot>();

//        Lot lotData[] = new Lot[] {
//                new Lot("Goldspohn", 14, 42),
//                new Lot("Old Main", 2, 30),
//                new Lot("LAC", 20, 68)
//        };

//        this.lotsAdapter = new LotsAdapter(this, R.layout.main_scroll_entry, lots);
        this.lotsAdapter = new LotsAdapter(this, R.layout.main_scroll_entry, lotsList);

        this.listView = (ListView)findViewById(R.id.listView);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lot lot = (Lot)MainActivity.this.listView.getItemAtPosition(i);
                MainActivity.this.displayLotActivity(lot.name);
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

    private class LoadLotsTask extends AsyncTask<Object, Object, ArrayList<Lot>> {
        public static final String LOTS_RESOURCE = "http://api.birdseye.no-control.net/lots";

        @Override
        protected ArrayList<Lot> doInBackground(Object... objects) {
            RestClient rc = new RestClient("http://api.birdseye.no-control.net/lots");
            Object json = rc.get();
            JSONArray lotsJsonArray = (JSONArray)json;
            Log.d(LOG_TAG, "Cast to JSONArray done");
            return Lot.jsonArrayToLots(lotsJsonArray);
        }

        @Override
        protected void onPostExecute(ArrayList<Lot> result) {
            Log.d(LOG_TAG, "in onPostExecute: " + result.toString());
//            MainActivity.this.lots = new Lot[result.size()];
            for(int i = 0; i < result.size(); i++)
                MainActivity.this.lotsAdapter.add(result.get(i));
//                MainActivity.this.lots[i] = result.get(i);
//            Log.d(LOG_TAG, "Finished onPostExecute: " + Arrays.toString(MainActivity.this.lots));
            MainActivity.this.lotsAdapter.notifyDataSetChanged();
            Log.d(LOG_TAG, "Notified data set changed");
        }
    }
}
