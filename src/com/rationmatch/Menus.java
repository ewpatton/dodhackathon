package com.rationmatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.rationmatch.util.RdfUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Menus extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_layout);

		GridView gridView = (GridView) findViewById(R.id.grid_view);
		
		// Instance of ImageAdapter Class
		gridView.setAdapter(new ImageAdapter(this));

		/**
		 * On Click event for Single Gridview Item
		 * */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ProgressDialog dialog = ProgressDialog.show(Menus.this, "Loading...", "Retrieving your menu...", true);
				JsonNode results = RdfUtil.executeSELECTQuery("http://www.rationmatch.com/virtuoso/sparql",
				    "SELECT * WHERE { GRAPH <"+RdfUtil.US_ARMY_DATA+"> { ?s ?p ?o } }"
				    );
				if(results != null) {
				  // process JsonNode into a Menu and its MenuItems
				}
				// Sending image id to FullScreenActivity
				Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
				// passing array index
				i.putExtra("id", position);
				startActivity(i);
			}
		});
	}
}