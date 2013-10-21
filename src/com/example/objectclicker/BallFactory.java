package com.example.objectclicker;

import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.widget.ImageView;

/**
 * ボールを生成するためのファクトリクラス
 */
public class BallFactory {
    
    /**ゲーム画面のアクティビティ*/
    private Activity activity;
    /**ウィンドウの幅（ピクセル）*/
    private int windowWidth;
    /**ボールがタッチされたことを通知するリスナ*/
    private BallTouchEventListener touchListener;
    
    private Random rand = new Random(new Date().getTime());
    
    /**
     * 新しいファクトリを生成します。
     * @param activity ゲーム画面のアクティビティ
     * @param windowWidth ウィンドウの幅（ピクセル）
     * @param touchListener ボールがタッチされたことを通知するリスナ
     */
    public BallFactory(Activity activity, int windowWidth, BallTouchEventListener touchListener) {
        this.activity = activity;
        this.windowWidth = windowWidth;
        this.touchListener = touchListener;
    }

    /**
     * ランダムな位置にボールを生成します。
     * <p>
     * ボールは、画面上部の画面外のランダムな位置に生成されます。
     * 
     * @return 生成されたボール
     */
    public Ball createRandomPositionBall() {
        // ボールのスペック（色・速さ・得点）を取得する
        BallSpec ballSpec = this.getBallSpec();
        
        // ボールの ImageView を生成する
        ImageView ballImage = this.createImageView(ballSpec.getResourceId());
        
        // ボールオブジェクトを生成し、必要な値を設定する
        Ball ball = new Ball();
        ball.setImage(ballImage);
        ball.setVelocity(ballSpec.getVelocity());
        ball.setPoint(ballSpec.getPoint());
        ball.setTouchListener(this.touchListener);
        
        // ボールをランダムな位置に移動させる
        int x = this.randomBetween(0, this.windowWidth - Ball.WIDTH / 2);
        int y = this.randomBetween(-Ball.HEIGHT * 4, -Ball.HEIGHT);
        
        ball.setX(x); // まだ view に登録していないので、別スレッドで実行しても問題ない
        ball.setY(y);
        
        return ball;
    }
    
    /**
     * ボールの仕様をランダムに取得する。
     * @return ボールの仕様
     */
    private BallSpec getBallSpec() {
        BallSpec[] ballSpec = BallSpec.values();
        return ballSpec[this.rand.nextInt(ballSpec.length)];
    }
    
    /**
     * 指定した範囲内でランダムな整数値を取得する。
     * @param min 最小値（この値を含む）
     * @param max 最大値（この値を含まない）
     * @return 取得したランダムな整数値
     */
    private int randomBetween(int min, int max) {
        return this.rand.nextInt(max - min) + min;
    }
    
    /**
     * ImageView を生成します。
     * @param resourceId 画像リソースの ID
     * @return 生成された ImageView
     */
    private ImageView createImageView(int resourceId) {
        ImageView imageView = new ImageView(this.activity);
        imageView.setImageResource(resourceId);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }
}
