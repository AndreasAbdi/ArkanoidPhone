package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Ball implements GameObject {
    private RectF rectangle;
    private float xVelocity;
    private float yVelocity;
    private float ballHeight = 30;
    private float ballWidth = 30;
    private float screenX;
    private float screenY;

    public Ball(int screenX, int screenY) {
        yVelocity = -8000;
        xVelocity = 4000;
        rectangle = new RectF();
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }

    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    public void clearObstacleY(float y){
        rectangle.bottom = y;
        rectangle.top = y - ballHeight;
    }

    public void clearObstacleX(float x){
        rectangle.left = x;
        rectangle.right = x + ballWidth;
    }

    public void reset(int x, int y){
        rectangle.left = x / 2;
        rectangle.top = y - 20;
        rectangle.right = x / 2 + ballWidth;
        rectangle.bottom = y - 20 - ballHeight;
    }

    public RectF getRectangle() {
        return rectangle;
    }

    public boolean intersects(Paddle paddle) {
        if (RectF.intersects(paddle.getRectangle(), rectangle)) {
            this.setRandomXVelocity();
            this.reverseYVelocity();
            this.clearObstacleY(paddle.getRectangle().top);
            return true;
        }
        return false;
    }

    public boolean intersects(Brick brick) {
        if (RectF.intersects(brick.getRectangle(), this.getRectangle()) && brick.isVisible()) {
            brick.setInvisible();
            this.reverseYVelocity();
            return true;
        }
        return false;
    }


    public boolean intersectsTop() {
        if (rectangle.top >= 0) {
            return false;
        }
        reverseYVelocity();
        clearObstacleY(ballHeight + 2);
        return true;
    }

    public boolean intersectsBottom() {
        if (this.rectangle.bottom <= screenY) {
            return false;
        }

        this.reverseYVelocity();
        this.clearObstacleY(screenY - 2);
        return true;
    }

    public boolean intersectsLeft() {
        if (rectangle.left >= 0) {
            return false;
        }
        reverseXVelocity();
        clearObstacleX(2);
        return true;
    }

    public boolean intersectsRight() {
        if (rectangle.right <= screenX) {
            return false;
        }
        reverseXVelocity();
        clearObstacleX(screenX - ballWidth - 2);
        return true;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));

        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update(long fps) {
        rectangle.left = rectangle.left + (xVelocity / fps);
        rectangle.top = rectangle.top + (yVelocity / fps);
        rectangle.right = rectangle.left + ballWidth;
        rectangle.bottom = rectangle.top - ballHeight;
    }
}
