package com.example.objectclicker;

import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.widget.ImageView;

/**
 * �{�[���𐶐����邽�߂̃t�@�N�g���N���X
 */
public class BallFactory {
    
    /**�Q�[����ʂ̃A�N�e�B�r�e�B*/
    private Activity activity;
    /**�E�B���h�E�̕��i�s�N�Z���j*/
    private int windowWidth;
    /**�{�[�����^�b�`���ꂽ���Ƃ�ʒm���郊�X�i*/
    private BallTouchEventListener touchListener;
    
    private Random rand = new Random(new Date().getTime());
    
    /**
     * �V�����t�@�N�g���𐶐����܂��B
     * @param activity �Q�[����ʂ̃A�N�e�B�r�e�B
     * @param windowWidth �E�B���h�E�̕��i�s�N�Z���j
     * @param touchListener �{�[�����^�b�`���ꂽ���Ƃ�ʒm���郊�X�i
     */
    public BallFactory(Activity activity, int windowWidth, BallTouchEventListener touchListener) {
        this.activity = activity;
        this.windowWidth = windowWidth;
        this.touchListener = touchListener;
    }

    /**
     * �����_���Ȉʒu�Ƀ{�[���𐶐����܂��B
     * <p>
     * �{�[���́A��ʏ㕔�̉�ʊO�̃����_���Ȉʒu�ɐ�������܂��B
     * 
     * @return �������ꂽ�{�[��
     */
    public Ball createRandomPositionBall() {
        // �{�[���̃X�y�b�N�i�F�E�����E���_�j���擾����
        BallSpec ballSpec = this.getBallSpec();
        
        // �{�[���� ImageView �𐶐�����
        ImageView ballImage = this.createImageView(ballSpec.getResourceId());
        
        // �{�[���I�u�W�F�N�g�𐶐����A�K�v�Ȓl��ݒ肷��
        Ball ball = new Ball();
        ball.setImage(ballImage);
        ball.setVelocity(ballSpec.getVelocity());
        ball.setPoint(ballSpec.getPoint());
        ball.setTouchListener(this.touchListener);
        
        // �{�[���������_���Ȉʒu�Ɉړ�������
        int x = this.randomBetween(0, this.windowWidth - Ball.WIDTH / 2);
        int y = this.randomBetween(-Ball.HEIGHT * 4, -Ball.HEIGHT);
        
        ball.setX(x); // �܂� view �ɓo�^���Ă��Ȃ��̂ŁA�ʃX���b�h�Ŏ��s���Ă����Ȃ�
        ball.setY(y);
        
        return ball;
    }
    
    /**
     * �{�[���̎d�l�������_���Ɏ擾����B
     * @return �{�[���̎d�l
     */
    private BallSpec getBallSpec() {
        BallSpec[] ballSpec = BallSpec.values();
        return ballSpec[this.rand.nextInt(ballSpec.length)];
    }
    
    /**
     * �w�肵���͈͓��Ń����_���Ȑ����l���擾����B
     * @param min �ŏ��l�i���̒l���܂ށj
     * @param max �ő�l�i���̒l���܂܂Ȃ��j
     * @return �擾���������_���Ȑ����l
     */
    private int randomBetween(int min, int max) {
        return this.rand.nextInt(max - min) + min;
    }
    
    /**
     * ImageView �𐶐����܂��B
     * @param resourceId �摜���\�[�X�� ID
     * @return �������ꂽ ImageView
     */
    private ImageView createImageView(int resourceId) {
        ImageView imageView = new ImageView(this.activity);
        imageView.setImageResource(resourceId);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }
}
