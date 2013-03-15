package com.noctrl.birdeye.release;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.noctrl.birdeye.release.adapters.Lot;

import java.net.URL;

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

        new LoadLotImageTask().execute(lot);

    }

    private class LoadLotImageTask extends AsyncTask <Lot, Object, Bitmap> {
        @Override
        protected Bitmap doInBackground(Lot... lots) {
            try {
                Lot lot = lots[0];
                URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?maptype=hybrid&center=" + lot.location + "&zoom=17"
                                + "&size=800x400&scale=2&markers=size:normal|color:red|" + lot.location
                                + "&sensor=false");
                return BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                Log.e(LOG_TAG, "BAD URL", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            LotActivity.this.lotImageView.setImageBitmap(bmp);
            LotActivity.this.lotImageView.setAdjustViewBounds(true);
        }
    }

}