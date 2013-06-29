package com.rationmatch;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

public class Settings extends Activity {

  private static final String SETTINGS="settings";
  private static final String GENDER="gender";
  private static final String ON_DUTY="on_duty";
  private static final String CALORIES="calories";
  private static final String FAT="fat";
  private static final String CARBOHYDRATES="carbs";
  private static final String PROTEIN="protein";

  private static final int MALE = 0;
  private static final int FEMALE = 1;

  private static final int HIGH_INTENSITY = 0;
  private static final int LOW_INTENSITY = 1;

  private static final int CALORIES_FIELD = 0;
  private static final int FAT_FIELD = 1;
  private static final int CARB_FIELD = 2;
  private static final int PROTEIN_FIELD = 3;

  // convert this to RDF if time; sourdces from pages 11 & 28 of the handouts
  private final int defaults[][][] = new int[][][] {
      // male
      new int[][] {
          new int[] { 4320, 160, 360, 360 },
          new int[] { 3000, 111, 250, 250 }
      },
      // female
      new int[][] {
          new int[] { 3150, 117, 262, 262 },
          new int[] { 2200, 82, 183, 183 }
      }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    SharedPreferences prefs = getSharedPreferences(SETTINGS, MODE_PRIVATE);
    if(!prefs.contains(ON_DUTY)) {
      prefs.edit().putInt(GENDER, MALE).commit();
      prefs.edit().putInt(ON_DUTY, HIGH_INTENSITY).commit();
      prefs.edit().putInt(CALORIES, defaults[MALE][HIGH_INTENSITY][CALORIES_FIELD]).commit();
      prefs.edit().putInt(FAT, defaults[MALE][HIGH_INTENSITY][FAT_FIELD]).commit();
      prefs.edit().putInt(CARBOHYDRATES, defaults[MALE][HIGH_INTENSITY][CARB_FIELD]).commit();
      prefs.edit().putInt(PROTEIN, defaults[MALE][HIGH_INTENSITY][PROTEIN_FIELD]).commit();
    }
    Spinner spinner = (Spinner)findViewById(R.id.spinner1);
    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> src, View view, int position,
          long id) {
        if(Settings.this.getSharedPreferences(SETTINGS, MODE_PRIVATE)
            .getInt(ON_DUTY, HIGH_INTENSITY) != position) {
          updatePrefs(position, -1);
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
        // do nothing
      }
    });
    if(prefs.getInt(ON_DUTY, HIGH_INTENSITY) == LOW_INTENSITY) {
      spinner.setSelection(LOW_INTENSITY);
    }
    spinner = (Spinner)findViewById(R.id.spinner2);
    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> src, View view, int position,
          long id) {
        if(Settings.this.getSharedPreferences(SETTINGS, MODE_PRIVATE)
            .getInt(GENDER, MALE) != position) {
          updatePrefs(-1, position);
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> arg0) {
        // do nothing
      }
    });
    if(prefs.getInt(GENDER, MALE) == FEMALE) {
      spinner.setSelection(FEMALE);
    }
    updateFields();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.settings, menu);
    return true;
  }

  protected void updatePrefs(int intensity, int gender) {
    SharedPreferences prefs = getSharedPreferences(SETTINGS, MODE_PRIVATE);
    if(intensity != -1) {
      prefs.edit().putInt(ON_DUTY, intensity).commit();
    } else {
      intensity = prefs.getInt(ON_DUTY, HIGH_INTENSITY);
    }
    if(gender != -1) {
      prefs.edit().putInt(GENDER, gender).commit();
    } else {
      gender = prefs.getInt(GENDER, MALE);
    }
    prefs.edit().putInt(CALORIES, defaults[gender][intensity][CALORIES_FIELD])
      .putInt(FAT, defaults[gender][intensity][FAT_FIELD])
      .putInt(CARBOHYDRATES, defaults[gender][intensity][CARB_FIELD])
      .putInt(PROTEIN, defaults[gender][intensity][PROTEIN_FIELD]).commit();
    updateFields();
  }

  protected void updateFields() {
    int value;
    SharedPreferences prefs = getSharedPreferences(SETTINGS, MODE_PRIVATE);
    int intensity = prefs.getInt(ON_DUTY, HIGH_INTENSITY);
    int gender = prefs.getInt(GENDER, MALE);
    
    value = prefs.getInt(CALORIES, defaults[gender][intensity][CALORIES_FIELD]);
    TextView text = (TextView)findViewById(R.id.totalCals);
    text.setText(Integer.toString(prefs.getInt(CALORIES, value)));

    value = prefs.getInt(CALORIES, defaults[gender][intensity][FAT_FIELD]);
    text = (TextView)findViewById(R.id.totalFat);
    text.setText(Integer.toString(prefs.getInt(FAT, value)));

    value = prefs.getInt(CALORIES, defaults[gender][intensity][CARB_FIELD]);
    text = (TextView)findViewById(R.id.totalCarbohydrates);
    text.setText(Integer.toString(prefs.getInt(CARBOHYDRATES, value)));

    value = prefs.getInt(CALORIES, defaults[gender][intensity][PROTEIN_FIELD]);
    text = (TextView)findViewById(R.id.totalProtein);
    text.setText(Integer.toString(prefs.getInt(PROTEIN, value)));
  }
}
