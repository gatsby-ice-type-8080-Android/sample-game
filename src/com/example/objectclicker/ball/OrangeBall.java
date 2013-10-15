package com.example.objectclicker.ball;

import android.content.Context;
import android.os.Handler;

import com.example.objectclicker.R;

public class OrangeBall extends Ball {

	public OrangeBall(Context context, Handler handler, float x, float y) {
		super(context, handler, x, y);
	}

	@Override
	protected int getImageResourceId() {
		return R.drawable.ball_orange;
	}

	@Override
	public int getPoint() {
		return 20;
	}

	@Override
	protected float getVelocity() {
		return 20f;
	}

}
