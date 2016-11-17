package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Score implements GameObject {
    public int score;

    public Score() {
        score = 0;
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));

        // Draw the score
        paint.setTextSize(40);
        canvas.drawText("Score: " + score, 10, 50, paint);
    }

    @Override
    public void update(long fps) {

    }
}
