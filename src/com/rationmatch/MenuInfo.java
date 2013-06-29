package com.rationmatch;

import java.util.List;
import java.util.Map;

import com.rationmatch.model.Menu;
import com.rationmatch.model.MenuItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.app.ListActivity;
import android.database.DataSetObserver;

public class MenuInfo extends ListActivity {

  Menu menu = null;

  @SuppressWarnings("unchecked")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ListView lv = getListView();
    LayoutInflater inflater = getLayoutInflater();
    ViewGroup header = (ViewGroup)inflater.inflate(R.layout.menu_info_header, lv, false);
    lv.addHeaderView(header, null, false);
    menu = new Menu((Map<String, Object>)getIntent().getSerializableExtra("menu"));
    setListAdapter(new MenuListAdapter(menu, this));
  }

  private static class MenuListAdapter implements ListAdapter {

    final Menu menu;
    final Activity context;

    MenuListAdapter(Menu menu, Activity context) {
      super();
      this.menu = menu;
      this.context = context;
    }

    @Override
    public int getCount() {
      List<MenuItem> items = menu.getMenuItems();
      if(items != null) return items.size();
      return 0;
    }

    @Override
    public Object getItem(int position) {
      return menu.getMenuItems().get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public int getItemViewType(int position) {
      return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = context.getLayoutInflater();
      CheckedTextView vg = (CheckedTextView)inflater.inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
      vg.setText(menu.getMenuItems().get(position).getLabel());
      vg.setChecked(true);
      return vg;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public boolean hasStableIds() {
      return false;
    }

    @Override
    public boolean isEmpty() {
      List<MenuItem> items = menu.getMenuItems();
      if(items != null) return items.isEmpty();
      return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public boolean areAllItemsEnabled() {
      return true;
    }

    @Override
    public boolean isEnabled(int position) {
      return true;
    }
    
  }
}
