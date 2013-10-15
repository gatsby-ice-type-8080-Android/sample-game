package com.example.objectclicker.ball;

import android.content.Context;
import android.os.Handler;

import com.example.objectclicker.R;

public class YellowBall extends Ball {
	
	public YellowBall(Context context, Handler handler, float x, float y) {
		super(context, handler, x, y);
	}

	@Override
	protected int getImageResourceId() {
		return R.drawable.ball_yellow;
	}

	@Override
	public int getPoint() {
		return 35;
	}

	@Override
	protected float getVelocity() {
		return 35f;
	}
}
