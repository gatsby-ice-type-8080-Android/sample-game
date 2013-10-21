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
 * �Q�[���𐧌䂷��N���X
 */
public class Game implements BallTouchEventListener {

    /**�{�[���̍ő吶����*/
    private static final int MAX_BALL_NUM = 24;
    /**�Q�[���I����ʒm���郊�X�i*/
    private GameFinishListener listener;
    /**�Q�[����ʂ̃A�N�e�B�r�e�B*/
    private Activity activity;
    /**�{�[����\������ View*/
    private ViewGroup viewBase;
    
    /**�Q�[���̃X�R�A*/
    private int score;
    /**�Q�[�����p�����邩�ǂ����𔻒肷�邩�̃t���O*/
    private boolean threadContinue;
    /**�ʃX���b�h���� View �𑀍삷�邽�߂̃n���h��*/
    private Handler handler;
    /**�E�B���h�E�̍����i�s�N�Z���j*/
    private int windowHeight;
    /**�E�B���h�E�̕��i�s�N�Z���j*/
    private int windowWidth;
    /**�{�[���̃t�@�N�g��*/
    private BallFactory ballFactory;
    /**���ݗ������̃{�[����ێ�����Z�b�g*/
    private Set<Ball> activeBallSet;
    
    /**
     * �Q�[���J�n�O�̏���������
     */
    private void init() {
        this.handler = new Handler();
        this.ballFactory = new BallFactory(this.activity, this.windowWidth, this);
        this.threadContinue = true;
        this.activeBallSet = Collections.synchronizedSet(new HashSet<Ball>()); // �{�[���N���b�N���ɕʃX���b�h���瑀�삳���\������
    }

    /**
     * �Q�[�����J�n����
     */
    public void start() {
        Log.v("oc", "Game#start() is called");
        this.init();
        
        new Thread() { // �A�j���[�V�������J�n����
            @Override
            public void run() {
                
                int ballNum = 0;

                try {
                    while (threadContinue) {
                        // �{�[���̐���
                        if (ballNum < MAX_BALL_NUM) {
                            activeBallSet.add(newBall());
                            ballNum++;
                        }
                        
                        // �{�[���̗���
                        moveBalls(activeBallSet);
                        
                        // �I������
                        if (activeBallSet.isEmpty()) {
                            threadContinue = false;
                        }
                        
                        Thread.sleep(33); // 30fps
                    }
                    
                    if (activeBallSet.isEmpty()) { // �߂�{�^���ȂǂŒ��f���ꂽ�ꍇ�͋�łȂ��\��������
                        // �Q�[���I�������X�i�ɒʒm����
                        listener.finishGame(Game.this);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }
    
    /**
     * �V�����{�[���𐶐�����
     * @return �V�����������ꂽ�{�[��
     */
    private Ball newBall() {
        // �����_���Ȉʒu�i��ʏ㕔�j�Ƀ{�[���𐶐�����
        final Ball ball = this.ballFactory.createRandomPositionBall();
        
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                // View �Ƀ{�[����ǉ�����
                ball.appendTo(viewBase);
            }
        });
        
        return ball;
    }

    /**
     * �{�[�����ړ�����
     * <p>
     * �ړ��������ʁA�{�[�����E�B���h�E�̊O�ɒB�����ꍇ�A���̃{�[���͈����̃Z�b�g����폜�����B
     * 
     * @param ballSet �ړ�������{�[���̃Z�b�g
     */
    private void moveBalls(final Set<Ball> ballSet) {
        this.handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (ballSet) { // JavaDoc �Q�� [http://docs.oracle.com/javase/jp/6/api/java/util/Collections.html#synchronizedSet%28java.util.Set%29]
                    Iterator<Ball> ite = ballSet.iterator();
                    
                    while (ite.hasNext()) {
                        Ball ball = ite.next();
                        
                        // ����
                        ball.fall();
                        
                        // ��ʊO�Ɉړ������ꍇ�͍폜
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
     * �{�[�����N���b�N�����Ƃ��ɃR�[���o�b�N����郁�\�b�h
     * @param ball �N���b�N���ꂽ�{�[��
     */
    @Override
    public void touch(Ball ball) {
        // �X�R�A�����Z
        this.score += ball.getPoint();
        
        // �N���b�N���ꂽ�{�[�����폜
        ball.removeFrom(this.viewBase);
        this.activeBallSet.remove(ball);
        
        Log.v("oc", "score = " + this.score);
    }

    /**
     * �Q�[���𒆒f���܂��B
     */
    public void stop() {
        Log.v("oc", "Game#stop() is called");
        this.threadContinue = false;
    }

    /**
     * ���̃Q�[���̃X�R�A���擾���܂��B
     * @return ���̃Q�[���̃X�R�A
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
