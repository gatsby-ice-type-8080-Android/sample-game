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
 * �Q�[����ʂ̃A�N�e�B�r�e�B
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
        
        // �Q�[���I�u�W�F�N�g�𐶐����āA�Q�[�����J�n����
        this.game = new Game();
        this.game.setActivity(this);
        this.game.setViewBase(base);
        this.game.setGameFinishListtener(this);
        this.game.setWindowWidth(metrics.widthPixels);
        this.game.setWindowHeight(metrics.heightPixels);
        
        this.game.start();
        
    }
    
    /**
     * ��ʂ���\���ɂȂ����Ƃ��ɃR�[���o�b�N����郁�\�b�h
     */
    @Override
    protected void onPause() {
        super.onPause(); // �����܂�
        
        // �Q�[�����I������
        this.game.stop();
    }
    
    private Handler handler = new Handler();
    
    /**
     * �Q�[���I�����ɃR�[���o�b�N����郁�\�b�h
     */
    @Override
    public void finishGame(final Game game) {
        // ���_���擾����
        final int score = game.getScore();
        
        // �Q�[�����ʂ��_�C�A���O�ŕ\������i�ʃX���b�h����R�[���o�b�N�����̂ŁA Handler ���g�p����j
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                // �_�C�A���O�̐���
                Builder dialog = new Builder(GameActivity.this);
                
                // �^�C�g���ݒ�
                dialog.setTitle(R.string.game_is_finished);
                
                // ���b�Z�[�W�ݒ�
                Resources res = GameActivity.this.getResources();
                dialog.setMessage(res.getString(R.string.score) + " : " + score);
                
                // �{�^���̔z�u�ƁA���̃N���b�N�n���h���̐ݒ�
                dialog.setPositiveButton(R.string.ok, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.this.finish(); // �A�N�e�B�r�e�B���I������i= ��ʂ̔�\���j
                    }
                });
                
                // �_�C�A���O��\������
                dialog.show();
            }
        });
        
        // �v���t�@�����X�ɃQ�[�����ʂ�ۑ�����
        SharedPreferences preference = this.getSharedPreferences(Constant.PREFERENCE_FILE_NAME, MODE_PRIVATE);
        int currentMaxRecord = preference.getInt(Constant.MAX_RECORD_SAVE_KEY, 0);
        
        if (currentMaxRecord < score) {
            // �ō����_�𒴂��Ă���ꍇ�̂ݕۑ�
            Editor editor = preference.edit();
            editor.putInt(Constant.MAX_RECORD_SAVE_KEY, score);
            editor.commit();
        }
    }
}
