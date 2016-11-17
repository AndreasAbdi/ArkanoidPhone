package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.HashMap;

public class GameModel {
    private HashMap<String, GameObjectCollection> collections =  new HashMap<>();

    private static final String PADDLE_KEY = "paddle";
    private static final String BRICK_KEY = "brick";
    private static final String BALL_KEY = "ball";

    int score;
    int lives;
    int screenX;
    int screenY;
    boolean paused = true;

    long fps;
    private long timeElapsedInFrame;

    public GameModel(int screenX, int screenY) {
        //add the ball, bricks, and paddle.
        GameObjectCollection<Paddle> paddleCollection = new GameObjectCollection<>(1, Paddle.class);
        GameObjectCollection<Brick> brickCollection = new GameObjectCollection<>(100, Brick.class);
        GameObjectCollection<Ball> ballCollection = new GameObjectCollection<>(1, Ball.class);
        collections.put(PADDLE_KEY, paddleCollection);
        collections.put(BRICK_KEY, brickCollection);
        collections.put(BALL_KEY, ballCollection);
        this.screenX = screenX;
        this.screenY = screenY;
        initializeData();
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        if(!paused) {
            update();
        }

        timeElapsedInFrame = System.currentTimeMillis() - startTime;
        if(timeElapsedInFrame >=1) {
            fps = 1000/ timeElapsedInFrame;
        }
    }

    public void update() {
        collections.get(PADDLE_KEY).getInstance(0).update(fps);
        collections.get(BALL_KEY).getInstance(0).update(fps);
    }

    public void initializeData() {
        Paddle paddle = new Paddle(screenX,screenY);
        collections.get(PADDLE_KEY).setInstance(0, paddle);
        Ball ball = new Ball(screenX, screenY);
        ball.reset(screenX, screenY);
        collections.get(BALL_KEY).setInstance(0,ball);
        Brick brick;

        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;
        int brickIndex = 0;
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 8; col++) {
                brick = new Brick(row, col, brickWidth, brickHeight);
                collections.get(BRICK_KEY).setInstance(brickIndex, brick);
                brickIndex++;
            }
        }
    }

    public void handleMotionEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                paused = false;
                if(event.getX() > screenX/2) {
                    ((Paddle)collections.get(PADDLE_KEY).getInstance(0)).setState(Paddle.State.RIGHT);
                } else {
                    ((Paddle)collections.get(PADDLE_KEY).getInstance(0)).setState(Paddle.State.LEFT);
                }
                break;
            case MotionEvent.ACTION_UP:
                ((Paddle)collections.get(PADDLE_KEY).getInstance(1)).setState(Paddle.State.STOPPED);
                break;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        for(GameObjectCollection collection : collections.values()) {
            for(int index = 0; index < collection.size; index++) {

                GameObject instance = collection.getInstance(index);
                if(instance != null) {
                    instance.draw(canvas, paint);
                }
            }
        }
    }


}
