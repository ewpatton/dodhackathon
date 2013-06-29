package com.rationmatch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	// Keep all Images in array
	public Integer[] mThumbIds = {
			R.drawable.pic_1, R.drawable.pic_2,
			R.drawable.pic_3, R.drawable.pic_4,
			R.drawable.pic_5, R.drawable.pic_6,
			R.drawable.pic_7, R.drawable.pic_8,
			R.drawable.pic_9, R.drawable.pic_10,
			R.drawable.pic_11, R.drawable.pic_12
	};
	
	// Constructor
	public ImageAdapter(Context c){
		mContext = c;
	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {			
		LinearLayout rLayout = new LinearLayout(mContext);
		
		rLayout.setOrientation(LinearLayout.VERTICAL);
		
		ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
        
        LinearLayout.LayoutParams tParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        
        
        TextView text =  new TextView(mContext);
        text.setText("Menu ".concat(Integer.toString(position+1)));
        text.setTypeface(Typeface.DEFAULT_BOLD);
        text.setLayoutParams(tParams);
        
        rLayout.addView(imageView);
        rLayout.addView(text);
        
        return rLayout;
	}

}
