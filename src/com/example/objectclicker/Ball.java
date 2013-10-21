package com.example.objectclicker;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * �{�[����\���I�u�W�F�N�g
 * <p>
 * {@link #setX(float)} �Ȃǈꕔ�̃��\�b�h�́A���̃{�[���ɕR�Â� View �𑀍삵�܂��B
 * <p>
 * ���̂��߁A{@link #appendTo(ViewGroup)} �ł��̃{�[�������� View �ƕR�t���Ă����Ԃ�
 * View �𑀍삷�郁�\�b�h�����s����ꍇ�́A Handler ���g���ă��C���X���b�h������s����悤�ɂ��Ă��������B
 */
public class Ball {
    /**�{�[���̕��i�s�N�Z���j*/
    public static final int WIDTH = 100;
    /**�{�[���̍����i�s�N�Z���j*/
    public static final int HEIGHT = 100;
    
    /**�{�[���̃C���[�W View*/
    private ImageView image;
    /**�{�[���̗������x*/
    private int velocity;
    /**�{�[���̓��_*/
    private int point;
    
    /**
     * �{�[�����w�肵�� ViewGroup �ɒǉ����܂��B
     * 
     * @param viewBase �ǉ���
     */
    public void appendTo(ViewGroup viewBase) {
        viewBase.addView(this.image);
    }

    /**
     * �{�[���𗎉������܂��B
     * <p>
     * ���̃��\�b�h�� View �𑀍삵�܂��B
     */
    public void fall() {
        float y = this.getY();
        this.setY(y + this.velocity);
    }

    /**
     * �w�肵�� ViewGroup ����A���̃{�[���� View ���������܂��B
     * <p>
     * ���̃��\�b�h�� View �𑀍삵�܂��B
     * 
     * @param viewBase ���̃{�[���� View ���������� ViewGroup
     */
    public void removeFrom(ViewGroup viewBase) {
        viewBase.removeView(this.image);
    }

    /**
     * �{�[���� X ���W��ݒ肵�܂��B
     * <p>
     * ���̃��\�b�h�� View �𑀍삵�܂��B
     * 
     * @param x X���W
     */
    public void setX(float x) {
        this.image.setTranslationX(x);
    }

    /**
     * �{�[���� Y ���W��ݒ肵�܂��B
     * <p>
     * ���̃��\�b�h�� View �𑀍삵�܂��B
     * 
     * @param y Y���W
     */
    public void setY(float y) {
        this.image.setTranslationY(y);
    }

    public float getY() {
        return this.image.getTranslationY();
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
    
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return this.point;
    }

    public void setTouchListener(final BallTouchEventListener touchListener) {
        this.image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                touchListener.touch(Ball.this);
            }
        });
    }
}
