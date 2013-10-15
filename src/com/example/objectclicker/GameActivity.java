package com.example.objectclicker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

public class GameActivity extends Activity implements GameFinishListener {
	
//	private static final long GAME_TIME = 5;
	private Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		final ViewGroup base = (ViewGroup)findViewById(R.id.gamePanel);
		
		this.game = new Game(this, base);
		this.game.setGameFinishListtener(this);
		this.game.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		this.game.stop();
	}
	
	private Handler handler = new Handler();
	
	/**
	 * ゲームを終了する
	 */
	@Override
	public void finishGame(final Game game) {
		final int point = game.getPoint();
		handler.post(new Runnable() {
			@Override
			public void run() {
				Builder dialog = new Builder(GameActivity.this);
				dialog.setTitle("ゲーム終了です");
				dialog.setMessage("スコア：" + point);
				dialog.setPositiveButton("OK", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
				System.out.println("dialog show");
				dialog.show();
			}
		});
		SharedPreferences preference = getSharedPreferences(MenuActivity.PREFERENCE_FILE_NAME, MODE_PRIVATE);
		int currentMaxRecord = preference.getInt(MenuActivity.MAX_RECORD_SAVE_KEY, 0);
		
		if (currentMaxRecord < point) {
			Editor editor = preference.edit();
			editor.putInt(MenuActivity.MAX_RECORD_SAVE_KEY, point);
			editor.commit();
		}
	}
}
