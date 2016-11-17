package abdi.andreas.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Brick implements GameObject {
    private RectF rectangle;

    private boolean isVisible;

    public Brick(int row, int column, int width, int height){

        isVisible = true;

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
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if(isVisible) {
            paint.setColor(Color.argb(255, 249, 129, 0));
            canvas.drawRect(rectangle, paint);
        }

    }

    @Override
    public void update(long fps) {

    }


}
