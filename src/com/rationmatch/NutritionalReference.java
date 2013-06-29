package com.rationmatch;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.app.ListActivity;
import android.database.DataSetObserver;

public class NutritionalReference extends ListActivity {
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setListAdapter(new ListAdapter() {

      @Override
      public int getCount() {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override
      public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override
      public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override
      public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        return null;
      }

      @Override
      public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 0;
      }

      @Override
      public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public void registerDataSetObserver(DataSetObserver arg0) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void unregisterDataSetObserver(DataSetObserver arg0) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return false;
      }

      @Override
      public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return false;
      }
      
    });
  }

}
