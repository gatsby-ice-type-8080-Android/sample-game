package com.example.objectclicker.ball;

import android.content.Context;
import android.os.Handler;

import com.example.objectclicker.R;

public class GreenBall extends Ball {

	public GreenBall(Context context, Handler handler, float x, float y) {
		super(context, handler, x, y);
	}

	@Override
	protected int getImageResourceId() {
		return R.drawable.ball_green;
	}

	@Override
	public int getPoint() {
		return 15;
	}

	@Override
	protected float getVelocity() {
		return 15f;
	}

}
