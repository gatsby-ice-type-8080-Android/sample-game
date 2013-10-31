package com.example.objectclicker;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * ゲーム画面のアクティビティ
 */
public class GameActivity extends Activity implements GameFinishListener {
    
    private Game game;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_game);
        
        final ViewGroup base = (ViewGroup)this.findViewById(R.id.gamePanel);
        
        WindowManager wm = this.getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        
        // ゲームオブジェクトを生成して、ゲームを開始する
        this.game = new Game();
        this.game.setActivity(this);
        this.game.setViewBase(base);
        this.game.setGameFinishListtener(this);
        this.game.setWindowWidth(metrics.widthPixels);
        this.game.setWindowHeight(metrics.heightPixels);
        
        this.game.start();
        
    }
    
    /**
     * 画面が非表示になったときにコールバックされるメソッド
     */
    @Override
    protected void onPause() {
        super.onPause(); // お決まり
        
        // ゲームを終了する
        this.game.stop();
    }
    
    private Handler handler = new Handler();
    
    /**
     * ゲーム終了時にコールバックされるメソッド
     */
    @Override
    public void finishGame(final Game game) {
        // 得点を取得する
        final int score = game.getScore();
        
        // ゲーム結果をダイアログで表示する（別スレッドからコールバックされるので、 Handler を使用する）
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                // ダイアログの生成
                Builder dialog = new Builder(GameActivity.this);
                
                // タイトル設定
                dialog.setTitle(R.string.game_is_finished);
                
                // メッセージ設定
                Resources res = GameActivity.this.getResources();
                dialog.setMessage(res.getString(R.string.score) + " : " + score);
                
                // ボタンの配置と、そのクリックハンドラの設定
                dialog.setPositiveButton(R.string.ok, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.this.finish(); // アクティビティを終了する（= 画面の非表示）
                    }
                });
                
                // ダイアログを表示する
                dialog.show();
            }
        });
        
        // プリファレンスにゲーム結果を保存する
        SharedPreferences preference = this.getSharedPreferences(Constant.PREFERENCE_FILE_NAME, MODE_PRIVATE);
        int currentMaxRecord = preference.getInt(Constant.MAX_RECORD_SAVE_KEY, 0);
        
        if (currentMaxRecord < score) {
            // 最高得点を超えている場合のみ保存
            Editor editor = preference.edit();
            editor.putInt(Constant.MAX_RECORD_SAVE_KEY, score);
            editor.commit();
        }
    }
}
