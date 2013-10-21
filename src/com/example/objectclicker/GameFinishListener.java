package com.example.objectclicker;

/**
 * ゲームの終了をリスンします。
 */
public interface GameFinishListener {
    /**
     * ゲームが正常に終了したときにコールバックされます。
     * 
     * @param game 終了したゲーム
     */
    void finishGame(Game game);
}
