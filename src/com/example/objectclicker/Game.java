package com.example.objectclicker;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;

/**
 * ゲームを制御するクラス
 */
public class Game implements BallTouchEventListener {

    /**ボールの最大生成数*/
    private static final int MAX_BALL_NUM = 24;
    /**ゲーム終了を通知するリスナ*/
    private GameFinishListener listener;
    /**ゲーム画面のアクティビティ*/
    private Activity activity;
    /**ボールを表示する View*/
    private ViewGroup viewBase;
    
    /**ゲームのスコア*/
    private int score;
    /**ゲームを継続するかどうかを判定するかのフラグ*/
    private boolean threadContinue;
    /**別スレッドから View を操作するためのハンドラ*/
    private Handler handler;
    /**ウィンドウの高さ（ピクセル）*/
    private int windowHeight;
    /**ウィンドウの幅（ピクセル）*/
    private int windowWidth;
    /**ボールのファクトリ*/
    private BallFactory ballFactory;
    /**現在落下中のボールを保持するセット*/
    private Set<Ball> activeBallSet;
    
    /**
     * ゲーム開始前の初期化処理
     */
    private void init() {
        this.handler = new Handler();
        this.ballFactory = new BallFactory(this.activity, this.windowWidth, this);
        this.threadContinue = true;
        this.activeBallSet = Collections.synchronizedSet(new HashSet<Ball>()); // ボールクリック時に別スレッドから操作される可能性あり
    }

    /**
     * ゲームを開始する
     */
    public void start() {
        Log.v("oc", "Game#start() is called");
        this.init();
        
        new Thread() { // アニメーションを開始する
            @Override
            public void run() {
                
                int ballNum = 0;

                try {
                    while (threadContinue) {
                        // ボールの生成
                        if (ballNum < MAX_BALL_NUM) {
                            activeBallSet.add(newBall());
                            ballNum++;
                        }
                        
                        // ボールの落下
                        moveBalls(activeBallSet);
                        
                        // 終了判定
                        if (activeBallSet.isEmpty()) {
                            threadContinue = false;
                        }
                        
                        Thread.sleep(33); // 30fps
                    }
                    
                    if (activeBallSet.isEmpty()) { // 戻るボタンなどで中断された場合は空でない可能性がある
                        // ゲーム終了をリスナに通知する
                        listener.finishGame(Game.this);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }
    
    /**
     * 新しいボールを生成する
     * @return 新しく生成されたボール
     */
    private Ball newBall() {
        // ランダムな位置（画面上部）にボールを生成する
        final Ball ball = this.ballFactory.createRandomPositionBall();
        
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                // View にボールを追加する
                ball.appendTo(viewBase);
            }
        });
        
        return ball;
    }

    /**
     * ボールを移動する
     * <p>
     * 移動した結果、ボールがウィンドウの外に達した場合、そのボールは引数のセットから削除される。
     * 
     * @param ballSet 移動させるボールのセット
     */
    private void moveBalls(final Set<Ball> ballSet) {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (ballSet) { // JavaDoc 参照 [http://docs.oracle.com/javase/jp/6/api/java/util/Collections.html#synchronizedSet%28java.util.Set%29]
                    Iterator<Ball> ite = ballSet.iterator();
                    
                    while (ite.hasNext()) {
                        Ball ball = ite.next();
                        
                        // 落下
                        ball.fall();
                        
                        // 画面外に移動した場合は削除
                        if (windowHeight < ball.getY()) {
                            ball.removeFrom(viewBase);
                            ite.remove();
                        }
                    }
                }
            }
        });
    }
    
    /**
     * ボールをクリックしたときにコールバックされるメソッド
     * @param ball クリックされたボール
     */
    @Override
    public void touch(Ball ball) {
        // スコアを加算
        this.score += ball.getPoint();
        
        // クリックされたボールを削除
        ball.removeFrom(this.viewBase);
        this.activeBallSet.remove(ball);
        
        Log.v("oc", "score = " + this.score);
    }

    /**
     * ゲームを中断します。
     */
    public void stop() {
        Log.v("oc", "Game#stop() is called");
        this.threadContinue = false;
    }

    /**
     * このゲームのスコアを取得します。
     * @return このゲームのスコア
     */
    public int getScore() {
        return this.score;
    }

    public void setGameFinishListtener(GameActivity listener) {
        this.listener = listener;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setViewBase(ViewGroup viewBase) {
        this.viewBase = viewBase;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
}
