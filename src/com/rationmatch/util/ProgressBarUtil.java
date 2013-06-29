package com.rationmatch.util;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;


public class ProgressBarUtil {
  private LinearLayout view;

  public ProgressBarUtil(LinearLayout view) {
    this.view = view;
  }

  public void setPrimaryColor(int id) {
    this.view.findViewWithTag("primary").setBackgroundColor(id);
  }

  public void setSecondaryColor(int id) {
    this.view.findViewWithTag("secondary").setBackgroundColor(id);
  }

  public void setDistance(int primary, int secondary) {
    int unused = 100 - primary - secondary;
    View v = this.view.findViewWithTag("primary");
    LayoutParams params = (LinearLayout.LayoutParams)v.getLayoutParams();
    v.setLayoutParams(new LinearLayout.LayoutParams(params.width, params.height, primary));
    v = this.view.findViewWithTag("secondary");
    params = (LinearLayout.LayoutParams)v.getLayoutParams();
    v.setLayoutParams(new LinearLayout.LayoutParams(params.width, params.height, secondary));
    v = this.view.findViewWithTag("unused");
    params = (LinearLayout.LayoutParams)v.getLayoutParams();
    v.setLayoutParams(new LinearLayout.LayoutParams(params.width, params.height, unused));
  }
}
