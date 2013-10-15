package com.example.objectclicker.ball;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;


public class BallFactory {
	
	public static Set<Ball> createBalls(int ballNum, Context context, ViewGroup base, Handler handler, BallTouchEventListener listener) {
		Log.v("oc", "BallFactory#createBalls() start");
		Random rand = new Random(new Date().getTime());
		
		Set<Ball> set = new HashSet<Ball>();
		
		for (int i=0; i<ballNum; i++) {
			float x = rand.nextInt(600);
			float y = -400 + rand.nextInt(400);
			int ballType = rand.nextInt(7);
			
			Ball ball;
			
			switch (ballType) {
			case 0:
				ball = new BlueBall(context, handler, x, y);
				break;
			case 1:
				ball = new GreenBall(context, handler, x, y);
				break;
			case 2:
				ball = new LightBlueBall(context, handler, x, y);
				break;
			case 3:
				ball = new OrangeBall(context, handler, x, y);
				break;
			case 4:
				ball = new PurpleBall(context, handler, x, y);
				break;
			case 5:
				ball = new RedBall(context, handler, x, y);
				break;
			case 6:
				ball = new YellowBall(context, handler, x, y);
				break;
			default:
				throw new RuntimeException("unknown ball type : " + ballType);
			}
			
			ball.setTouchEventListener(listener);
			ball.addTo(base);
			
			set.add(ball);
		}
		
		Log.v("oc", "BallFactory#createBalls() end set size = " + set.size());
		return set;
	}
	
	
}
