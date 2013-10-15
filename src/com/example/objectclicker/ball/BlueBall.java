package com.example.objectclicker.ball;

import android.content.Context;
import android.os.Handler;

import com.example.objectclicker.R;

public class BlueBall extends Ball {

	public BlueBall(Context context, Handler handler, float x, float y) {
		super(context, handler, x, y);
	}

	@Override
	protected int getImageResourceId() {
		return R.drawable.ball_blue;
	}

	@Override
	public int getPoint() {
		return 5;
	}

	@Override
	protected float getVelocity() {
		return 5f;
	}
}
