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
 * ���j���[��ʂ̃A�N�e�B�r�e�B
 */
public class MenuActivity extends Activity {
    
    /**�v���t�@�����X�ɍō��L�^���L�^����Ă��Ȃ��ꍇ�ɕ\������A�f�t�H���g�̍ō��L�^�l*/
    private static final int DEFAULT_MAX_RECORD = 0;
    
    /**
     * ���j���[��ʐ������ɃR�[���o�b�N����郁�\�b�h
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);           // �����܂�
        this.setContentView(R.layout.activity_menu); // �����܂�
        
        // �X�^�[�g�{�^���̃C�x���g�n���h����o�^����
        Button startButton = (Button)this.findViewById(R.id.startButton);
        
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // �Q�[����ʁiGameActivity�j���Ăяo��
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                MenuActivity.this.startActivity(intent);
            }
        });
    }
    
    /**
     * ���j���[��ʂ��ĕ\�����ꂽ�Ƃ��ɃR�[���o�b�N����郁�\�b�h
     */
    @Override
    protected void onResume() {
        super.onResume(); // �����܂�
        
        // �ō��L�^���v���t�@�����X����擾���A��ʂɐݒ肷��
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
