package com.example.objectclicker;

/**
 * ボールの仕様。
 */
public enum BallSpec {
    /**青*/
    BLUE      (R.drawable.ball_blue,        5,  5),
    /**緑*/
    GREEN     (R.drawable.ball_green,      10, 10),
    /**水色*/
    LIGHT_BLUE(R.drawable.ball_light_blue, 15, 15),
    /**オレンジ*/
    ORANGE    (R.drawable.ball_orange,     20, 20),
    /**紫*/
    PURPLE    (R.drawable.ball_purple,     25, 25),
    /**赤*/
    RED       (R.drawable.ball_red,        30, 30),
    /**黄*/
    YELLOW    (R.drawable.ball_yellow,     35, 35),
    ;
    
    private int resourceId;
    private int velocity;
    private int point;
    
    private BallSpec(int resourceId, int velocity, int point) {
        this.resourceId = resourceId;
        this.velocity = velocity;
        this.point = point;
    }
    
    /**
     * 画像のリソース ID を取得します。
     * @return リソース ID
     */
    public int getResourceId() {
        return this.resourceId;
    }
    
    /**
     * 落下速度を取得します。
     * @return 落下速度
     */
    public int getVelocity() {
        return this.velocity;
    }

    /**
     * タッチしたときの得点を取得します。
     * @return 得点
     */
    public int getPoint() {
        return point;
    }
}
