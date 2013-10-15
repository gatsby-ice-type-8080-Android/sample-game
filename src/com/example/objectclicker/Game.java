package com.example.objectclicker;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;

import com.example.objectclicker.ball.Ball;
import com.example.objectclicker.ball.BallFactory;
import com.example.objectclicker.ball.BallTouchEventListener;

public class Game implements BallTouchEventListener {
	
	private GameFinishListener listener;
	private Context context;
	private ViewGroup viewBase;
	
	private int score;
	
	@Override
	public void touch(Ball ball) {
		this.score += ball.getPoint();
		Log.v("oc", "score = " + this.score);
	}

	public void setGameFinishListtener(GameActivity listener) {
		this.listener = listener;
	}

	private static final int MAX_BALL_NUM = 20;
	private boolean threadContinue = true;
	
	public void start() {
		Log.v("oc", "Game#start() is called");
		final Random rand = new Random(new Date().getTime());
		final Handler handler = new Handler();
		
		new Thread() {
			@Override
			public void run() {
				Set<Ball> ballSet = new HashSet<Ball>();
				int totalBallNum = 0;
				int fps = 30;
				int cnt = 0;

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				while (threadContinue) {
					// ボールの生成
					if (totalBallNum < MAX_BALL_NUM) {
						if ((cnt % 30) == 0) {
							int n = rand.nextInt(5) + 1;
							Set<Ball> newBallSet = BallFactory.createBalls(n, context, viewBase, handler, Game.this);
							ballSet.addAll(newBallSet);
							totalBallNum += n;
						}
					}
					
					// ボールの移動
					Iterator<Ball> ite = ballSet.iterator();
					while (ite.hasNext()) {
						Ball ball = ite.next();
						if (ball.fall()) {
							ite.remove();
						}
					}
					
					Log.v("oc", "totalBallNum = " + totalBallNum + ", ballSet.Size = " + ballSet.size());
					
					// 終了条件判定
					if (MAX_BALL_NUM <= totalBallNum && ballSet.isEmpty()) {
						threadContinue = false;
						listener.finishGame(Game.this);
					}
					
					try {
						Thread.sleep(fps);
						cnt++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					if (1800 < cnt) {
						Log.e("oc", "infinity loop!!");
					}
				}
			}
		}.start();
	}
	
	public void stop() {
		Log.v("oc", "Game#stop() is called");
		this.threadContinue = false;
	}

	public int getScore() {
		return this.score;
	}

	public void setContext(Context cotnext) {
		this.context = cotnext;
	}

	public void setViewBase(ViewGroup viewBase) {
		this.viewBase = viewBase;
	}
}
