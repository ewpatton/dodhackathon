package com.rationmatch;

import com.fasterxml.jackson.databind.JsonNode;
import com.rationmatch.util.RdfUtil;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
					final int position, long id) {
				
				AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
					
					private ProgressDialog progressDialog;

					@Override
		            protected void onPreExecute()
		            {
		                /*
		                 * This is executed on UI thread before doInBackground(). It is
		                 * the perfect place to show the progress dialog.
		                 */
		                progressDialog = ProgressDialog.show(Menus.this, "Loading...", "Retrieving your menu...", true);
						
		            }

					@Override
					protected Void doInBackground(Void... arg0) {
						try {
		                    /*
		                     * This is run on a background thread, so we can sleep here
		                     * or do whatever we want without blocking UI thread. A more
		                     * advanced use would download chunks of fixed size and call
		                     * publishProgress();
		                     */
		                    //Thread.sleep(1000);
		            		JsonNode results = RdfUtil.executeSELECTQuery("http://www.rationmatch.com/virtuoso/sparql",
		            			    "SELECT * FROM <"+RdfUtil.US_ARMY_DATA+"> WHERE { ?s ?p ?o }"
		            			    );
		            			if(results != null) {
		            			  // process JsonNode into a Menu and its MenuItems
		            				Log.i("QUERY", results.asText());
		            				
		            			}
		            			
		               }
		                catch (Exception e)
		                {
		                    Log.e("tag", e.getMessage());
		                    /*
		                     * The task failed
		                     */
		                    //return false;
		                }
						return null;
		                
		                

		                /*
		                 * The task succeeded
		                 */
		                //return true;
					}
					
					@Override
					protected void onPostExecute(Void result) {
		                    progressDialog.dismiss();
		    				// Sending image id to FullScreenActivity
		    				Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
		    				// passing array index
		    				i.putExtra("id", position);
		    				startActivity(i);
		            }
				};
				
				task.execute((Void[])null);
				
			}
		});
	}
}