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

/**
 * メニュー画面のアクティビティ
 */
public class MenuActivity extends Activity {
    
    /**プリファレンスに最高記録が記録されていない場合に表示する、デフォルトの最高記録値*/
    private static final int DEFAULT_MAX_RECORD = 0;
    
    /**
     * メニュー画面生成時にコールバックされるメソッド
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);           // お決まり
        this.setContentView(R.layout.activity_menu); // お決まり
        
        // スタートボタンのイベントハンドラを登録する
        Button startButton = (Button)this.findViewById(R.id.startButton);
        
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // ゲーム画面（GameActivity）を呼び出す
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }
    
    /**
     * メニュー画面が再表示されたときにコールバックされるメソッド
     */
    @Override
    protected void onResume() {
        super.onResume(); // お決まり
        
        // 最高記録をプリファレンスから取得し、画面に設定する
        SharedPreferences preference = this.getSharedPreferences(Constant.PREFERENCE_FILE_NAME, MODE_PRIVATE);
        int maxRecord = preference.getInt(Constant.MAX_RECORD_SAVE_KEY, DEFAULT_MAX_RECORD);
        
        TextView maxRecordText = (TextView)this.findViewById(R.id.maxRecordText);
        maxRecordText.setText(String.valueOf(maxRecord));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
