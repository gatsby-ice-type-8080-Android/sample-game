package com.example.objectclicker.ball;

import android.content.Context;
import android.os.Handler;

import com.example.objectclicker.R;

public class RedBall extends Ball {

	public RedBall(Context context, Handler handler, float x, float y) {
		super(context, handler, x, y);
	}

	@Override
	protected int getImageResourceId() {
		return R.drawable.ball_red;
	}

	@Override
	public int getPoint() {
		return 30;
	}

	@Override
	protected float getVelocity() {
		return 30f;
	}

}
