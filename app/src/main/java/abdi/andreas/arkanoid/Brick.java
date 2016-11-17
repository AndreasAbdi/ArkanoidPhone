package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Brick implements GameObject {
    private RectF rectangle;

    private boolean visible;

    public Brick(int row, int column, int width, int height){

        visible = true;

        int padding = 1;

        float topX = column * width;
        float topY = row * height;
        rectangle = new RectF(
                topX + padding,
                topY + padding,
                topX + width - padding,
                topY + height - padding
        );
    }

    public void setInvisible(){
        visible = false;
    }

    public boolean isVisible(){
        return visible;
    }

    public RectF getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if(visible) {
            paint.setColor(Color.argb(255, 249, 129, 0));
            canvas.drawRect(rectangle, paint);
        }

    }


    @Override
    public void update(long fps) {

    }


}
