package com.example.objectclicker;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * ボールを表すオブジェクト
 * <p>
 * {@link #setX(float)} など一部のメソッドは、このボールに紐づく View を操作します。
 * <p>
 * このため、{@link #appendTo(ViewGroup)} でこのボールが既に View と紐付いている状態で
 * View を操作するメソッドを実行する場合は、 Handler を使ってメインスレッドから実行するようにしてください。
 */
public class Ball {
    /**ボールの幅（ピクセル）*/
    public static final int WIDTH = 100;
    /**ボールの高さ（ピクセル）*/
    public static final int HEIGHT = 100;
    
    /**ボールのイメージ View*/
    private ImageView image;
    /**ボールの落下速度*/
    private int velocity;
    /**ボールの得点*/
    private int point;
    
    /**
     * ボールを指定した ViewGroup に追加します。
     * 
     * @param viewBase 追加先
     */
    public void appendTo(ViewGroup viewBase) {
        viewBase.addView(this.image);
    }

    /**
     * ボールを落下させます。
     * <p>
     * このメソッドは View を操作します。
     */
    public void fall() {
        float y = this.getY();
        this.setY(y + this.velocity);
    }

    /**
     * 指定した ViewGroup から、このボールの View を除去します。
     * <p>
     * このメソッドは View を操作します。
     * 
     * @param viewBase このボールの View を除去する ViewGroup
     */
    public void removeFrom(ViewGroup viewBase) {
        viewBase.removeView(this.image);
    }

    /**
     * ボールの X 座標を設定します。
     * <p>
     * このメソッドは View を操作します。
     * 
     * @param x X座標
     */
    public void setX(float x) {
        this.image.setTranslationX(x);
    }

    /**
     * ボールの Y 座標を設定します。
     * <p>
     * このメソッドは View を操作します。
     * 
     * @param y Y座標
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
