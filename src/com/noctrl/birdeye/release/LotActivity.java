package com.noctrl.birdeye.release;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.noctrl.birdeye.release.adapters.Lot;

public class LotActivity extends Activity {

    public static final String LOG_TAG = "LotActivity";
    public static final String ARGS_LOT_NAME = "LOT_NAME";
    public static final String ARGS_LOT = "LOT";

    private TextView titleTextView;
    private TextView descriptionTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lot_activity);

        this.titleTextView = (TextView)findViewById(R.id.lotNameTitleTextView);
        this.descriptionTextView = (TextView)findViewById(R.id.lotDescTextView);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Lot lot = null;
        if(b != null)
            lot = b.getParcelable(LotActivity.ARGS_LOT);

        if(lot != null) {
            this.titleTextView.setText(lot.name);
            this.descriptionTextView.setText(lot.description);
        }


    }

}