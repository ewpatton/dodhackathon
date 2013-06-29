package com.rationmatch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.rationmatch.model.MenuCollectionFactory;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class RationMatch extends Activity {

  private final static String LOG_TAG = RationMatch.class.getSimpleName();

  private void unpack() {
    File data = getDir("data", MODE_PRIVATE);
    File target = new File(data, "database.json");
    if(!target.exists()) {
      try {
        byte[] buffer = new byte[1024];
        int read = 0;
        InputStream is = this.getResources().getAssets().open("database.json");
        OutputStream os = new FileOutputStream(target);
        while((read = is.read(buffer)) > 0) {
          os.write(buffer, 0, read);
        }
        is.close();
        os.close();
      } catch(Exception e) {
        Log.w(LOG_TAG, "Unable to unpack database.json to "+target);
      }
    }
  }

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
    unpack();
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
    Intent i = new Intent();
    switch(id) {
    case R.id.nutrient_reference_entry:
      i.setClass(this.getApplicationContext(), NutritionalReference.class);
      break;
    case R.id.lets_eat_entry:
    	i.setClass(this.getApplicationContext(), Menus.class);
    	break;
    case R.id.settings_entry:
      i.setClass(this.getApplicationContext(), MenuInfo.class);
      i.putExtra("menu", MenuCollectionFactory
          .listMenusInCollection(getApplicationContext(),
              "MRE 2013").get(0));
      break;
    default:
      return;
    }
    startActivity(i);
  }
}
