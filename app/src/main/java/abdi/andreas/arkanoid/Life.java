package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Life implements GameObject{
    private int lives;
    public static final int DEFAULT_LIFE = 3;

    public Life() {
        lives = DEFAULT_LIFE;
    }

    public int getLife() {
        return lives;
    }

    public void reduceLife() {
        lives--;
    }

    public void reset() {
        lives = DEFAULT_LIFE;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));

        // Draw the score
        paint.setTextSize(40);
        canvas.drawText( "Lives: " + lives, 300, 50, paint);
    }

    @Override
    public void update(long fps) {

    }
}
