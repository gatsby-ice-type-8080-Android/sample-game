package com.example.objectclicker;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Ball {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    
    private ImageView image;
    private int velocity;
    private int point;
    
    public void appendTo(ViewGroup viewBase) {
        viewBase.addView(this.image);
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setX(float x) {
        this.image.setTranslationX(x);
    }
    
    public void setY(float y) {
        this.image.setTranslationY(y);
    }
    
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void fall() {
        float y = this.getY();
        this.setY(y + this.velocity);
    }

    public float getY() {
        return this.image.getTranslationY();
    }

    public void removeFrom(ViewGroup viewBase) {
        Log.v("oc", "remove ball");
        viewBase.removeView(this.image);
    }

    public int getPoint() {
        return this.point;
    }

    public void setPoint(int point) {
        this.point = point;
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
