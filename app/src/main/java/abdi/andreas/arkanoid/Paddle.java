package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Paddle implements GameObject {

    enum State {
        RIGHT,
        LEFT,
        STOPPED
    }

    private RectF rectangle;
    private float height;
    private float width;
    private float topX;
    private float topY;
    private float paddleSpeed;
    private float maxRight;
    private float maxLeft;
    State state;

    public Paddle(int screenX, int screenY) {
        width = screenX/10;
        height = screenY/20;
        topX = screenX/2;
        topY = screenY - 20;

        rectangle = new RectF(topX, topY, topX + width, topY + height);
        paddleSpeed = 2000;

        maxLeft = 0;
        maxRight = screenX;

        state = State.STOPPED;

    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void update(long fps) {
        if(state == State.LEFT) {
            float intermediate = topX - paddleSpeed/fps;
            topX = (intermediate < maxLeft) ? 0 : intermediate;

        }
        if(state == State.RIGHT) {
            float intermediate = topX + paddleSpeed/fps;
            topX = (intermediate + width > maxRight) ? maxRight - width : intermediate;
        }
        rectangle.left = topX;
        rectangle.right = topX + width;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255, 255, 255, 255));

        canvas.drawRect(rectangle, paint);
    }
}
