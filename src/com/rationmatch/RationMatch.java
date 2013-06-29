package com.rationmatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class RationMatch extends Activity {

  private final static String LOG_TAG = RationMatch.class.getSimpleName();

  protected OnClickListener createClickListener(final int viewId) {
    return new OnClickListener() {
      @Override
      public void onClick(View v) {
        RationMatch.this.onViewClicked(viewId, v);
      }
    };
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ration_match);
    int[] viewsToBind = { R.id.overview_layout, R.id.lets_eat_entry,
        R.id.trade_entry, R.id.nutrient_reference_entry, R.id.settings_entry};
    for(int i=0;i<viewsToBind.length;i++) {
      View v = findViewById(viewsToBind[i]);
      v.setOnClickListener(createClickListener(viewsToBind[i]));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.ration_match, menu);
    return true;
  }
  
  protected void onViewClicked(int id, View view) {
    Log.d(LOG_TAG, "id = "+id);
    switch(id) {
    case R.id.nutrient_reference_entry:
      Intent i = new Intent();
      i.setClass(this.getApplicationContext(), NutritionalReference.class);
      startActivity(i);
      break;
    default:
      break;
    }
  }
}
