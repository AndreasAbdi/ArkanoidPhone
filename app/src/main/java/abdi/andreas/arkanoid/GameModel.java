package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.SensorEvent;
import android.view.MotionEvent;

import java.util.ArrayList;

public class GameModel {
    private ArrayList<GameObjectCollection> collections =  new ArrayList<>();

    private GameObjectCollection<Paddle> paddleCollection = new GameObjectCollection<>();
    private GameObjectCollection<Brick> brickCollection = new GameObjectCollection<>();
    private GameObjectCollection<Ball> ballCollection = new GameObjectCollection<>();
    private GameObjectCollection<Score> scoreCollection = new GameObjectCollection<>();
    private GameObjectCollection<Life> lifeCollection = new GameObjectCollection<>();

    private int screenX;
    private int screenY;
    boolean paused = true;

    private static final int numberRowsBricks = 3;
    private static final int numberColsBricks = 8;
    private long fps;
    private long timeElapsedInFrame;

    public GameModel(int screenX, int screenY) {
        collections.add(paddleCollection);
        collections.add(brickCollection);
        collections.add(ballCollection);
        collections.add(scoreCollection);
        collections.add(lifeCollection);

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
        for(GameObjectCollection collection : collections) {
            collection.update(fps);
        }

        handleObjectCollisions();
        handleTerminationConditions();

    }

    private void resetGame() {
        paused = true;
        initializeData();
    }

    public void initializeData() {
        for(GameObjectCollection collection : collections) {
            collection.clear();
        }
        addDefaultObjects();
    }

    public boolean handleMotionEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
            /**
             *
             */


            case MotionEvent.ACTION_DOWN:
                if(paused == true) {
                    paused = false;
                }
                paddleCollection.get(0).setState(Paddle.State.STOPPED);
                //paddleCollection.get(0).setState(Paddle.State.STOPPED);
                /**
                if(event.getX() > screenX/2) {
                    paddleCollection.get(0).setState(Paddle.State.RIGHT);
                } else {
                    paddleCollection.get(0).setState(Paddle.State.LEFT);
                }
                 */
                break;
            case MotionEvent.ACTION_UP:
                //
                break;
        }
        return true;
    }

    public boolean handleSensorEvent(SensorEvent event) {
        float x = event.values[1];
        if(x > 0.05) {
            paddleCollection.get(0).setState(Paddle.State.RIGHT);
        } else if (x < -0.05) {
            paddleCollection.get(0).setState(Paddle.State.LEFT);
        } else {
            paddleCollection.get(0).setState(Paddle.State.STOPPED);
        }
        return true;
    }

    public void draw(Canvas canvas, Paint paint) {
        for(GameObjectCollection collection : collections) {
            collection.draw(canvas, paint);
        }
    }

    private void addDefaultObjects() {
        Paddle paddle = new Paddle(screenX,screenY);
        paddleCollection.add(paddle);
        Ball ball = new Ball(screenX, screenY);
        ball.reset(screenX, screenY);
        ballCollection.add(ball);

        Brick brick;
        int brickWidth = screenX / numberColsBricks;
        int brickHeight = screenY / 10;
        int brickIndex = 0;
        for(int row = 0; row < numberRowsBricks; row++) {
            for(int col = 0; col < numberColsBricks; col++) {
                brick = new Brick(row, col, brickWidth, brickHeight);
                brickCollection.add(brickIndex, brick);
                brickIndex++;
            }
        }

        Score score = new Score();
        scoreCollection.add(0, score);
        Life life = new Life();
        lifeCollection.add(0, life);
    }

    private void handleObjectCollisions() {
        Ball ball = ballCollection.get(0);
        Paddle paddle = paddleCollection.get(0);
        Life life = lifeCollection.get(0);
        Score score = scoreCollection.get(0);

        //TODO: should do double intersection instead?
        for(Brick brick : brickCollection) {
            if(ball.intersects(brick)) {
                score.score += 10;
            }
        }
        ball.intersects(paddle);
        //TODO: generalize walls to gameObjects. then change intersects to methods taking walls.
        if(ball.intersectsBottom()) {
            life.reduceLife();
        }
        ball.intersectsRight();
        ball.intersectsLeft();
        ball.intersectsTop();
    }

    private void handleTerminationConditions() {
        Life life = lifeCollection.get(0);
        Score score = scoreCollection.get(0);

        if(score.score == 10 * numberRowsBricks * numberColsBricks || life.getLife() == 0) {
            resetGame();
        }
    }
}
