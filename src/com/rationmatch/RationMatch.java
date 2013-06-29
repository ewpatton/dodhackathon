package com.rationmatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;

public class RationMatch extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ration_match);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.ration_match, menu);
    return true;
  }
  
  /**
   * On click event for a button on the main rationview
   */
  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

      // Sending image id to FullScreenActivity
      Intent i = new Intent(getApplicationContext(), RationMenu.class);
      // passing array index
      //i.putExtra("id", position);
      startActivity(i);
  }

}
