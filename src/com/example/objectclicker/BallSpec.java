package com.example.objectclicker;

/**
 * �{�[���̎d�l�B
 */
public enum BallSpec {
    /**��*/
    BLUE      (R.drawable.ball_blue,        5,  5),
    /**��*/
    GREEN     (R.drawable.ball_green,      10, 10),
    /**���F*/
    LIGHT_BLUE(R.drawable.ball_light_blue, 15, 15),
    /**�I�����W*/
    ORANGE    (R.drawable.ball_orange,     20, 20),
    /**��*/
    PURPLE    (R.drawable.ball_purple,     25, 25),
    /**��*/
    RED       (R.drawable.ball_red,        30, 30),
    /**��*/
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
     * �摜�̃��\�[�X ID ���擾���܂��B
     * @return ���\�[�X ID
     */
    public int getResourceId() {
        return this.resourceId;
    }
    
    /**
     * �������x���擾���܂��B
     * @return �������x
     */
    public int getVelocity() {
        return this.velocity;
    }

    /**
     * �^�b�`�����Ƃ��̓��_���擾���܂��B
     * @return ���_
     */
    public int getPoint() {
        return point;
    }
}
