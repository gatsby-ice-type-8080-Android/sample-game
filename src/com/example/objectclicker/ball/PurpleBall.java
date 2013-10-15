package com.example.objectclicker.ball;

import android.content.Context;
import android.os.Handler;

import com.example.objectclicker.R;

public class PurpleBall extends Ball {

	public PurpleBall(Context context, Handler handler, float x, float y) {
		super(context, handler, x, y);
	}

	@Override
	protected int getImageResourceId() {
		return R.drawable.ball_purple;
	}

	@Override
	public int getPoint() {
		return 25;
	}

	@Override
	protected float getVelocity() {
		return 25f;
	}

}
