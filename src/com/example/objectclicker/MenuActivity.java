package com.example.objectclicker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity {
	
	public static final String PREFERENCE_FILE_NAME = "ObjectClickerPreference";
	public static final String MAX_RECORD_SAVE_KEY = "max.record";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		Button startButton = (Button)findViewById(R.id.startButton);
		
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MenuActivity.this, GameActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		TextView maxRecordText = (TextView)findViewById(R.id.maxRecordText);
		SharedPreferences preference = getSharedPreferences(PREFERENCE_FILE_NAME, MODE_PRIVATE);
		int maxRecord = preference.getInt(MAX_RECORD_SAVE_KEY, 0);
		maxRecordText.setText(String.valueOf(maxRecord));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
