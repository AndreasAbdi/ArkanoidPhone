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


    public Ball(int screenX, int screenY) {
        yVelocity = -4000;
        xVelocity = 2000;
        rectangle = new RectF();
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
