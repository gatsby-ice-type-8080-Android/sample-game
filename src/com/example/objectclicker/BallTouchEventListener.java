package com.example.objectclicker;

/**
 * ボールのタッチをリスンします。
 */
public interface BallTouchEventListener {
    /**
     * ボールがタッチされた際にコールバックされます。
     * 
     * @param ball タッチされたボール
     */
    void touch(Ball ball);
}
