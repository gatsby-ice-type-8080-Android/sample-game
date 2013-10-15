package com.example.objectclicker.ball;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;


public abstract class Ball implements OnClickListener {
	
	private BallTouchEventListener listener;
	private Handler handler;
	private ImageView view;
	private ViewGroup base;
	private float y;

	abstract protected int getImageResourceId();
	abstract public int getPoint();
	abstract protected float getVelocity();
	
	public Ball(final Context context, Handler handler, final float x, final float y) {
		this.handler = handler;
		this.handler.post(new Runnable() {
			@Override
			public void run() {
				view = new ImageView(context);
				view.setImageResource(getImageResourceId());
				view.setOnClickListener(Ball.this);
				view.setTranslationX(x);
				view.setTranslationY(y);
			}
		});
	}
	
	@Override
	synchronized public void onClick(final View v) {
		this.listener.touch(this);
		this.handler.post(new Runnable() {
			@Override
			public void run() {
				Log.v("oc", "click remove");
				base.removeView(v);
			}
		});
	}

	public void setTouchEventListener(BallTouchEventListener handler) {
		this.listener = handler;
	}

	public void addTo(final ViewGroup b) {
		this.base = b;
		this.handler.post(new Runnable() {
			@Override
			public void run() {
				base.addView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		});
	}
	
	synchronized public boolean fall() {
		this.y += getVelocity();
		this.handler.post(new Runnable() {
			@Override
			public void run() {
				view.setTranslationY(y);
			}
		});
		
		if (1200 < this.y) {
			return true;
		} else {
			return false;
		}
	}
}
