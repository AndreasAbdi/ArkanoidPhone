package abdi.andreas.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ArkanoidView extends SurfaceView implements Runnable {

    private SurfaceHolder surfaceHolder;

    Thread gameThread = null;
    GameModel gameModel;

    volatile boolean running;

    Canvas canvas;
    Paint paint;

    public ArkanoidView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenX = displayMetrics.widthPixels;
        int screenY = displayMetrics.heightPixels;
        gameModel = new GameModel(screenX, screenY);
    }

    @Override
    public void run() {
        while(running) {
            gameModel.run();
            draw();
        }
    }

    public void update() {

    }

    public void draw() {
        if(surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 26, 182, 182));

            //draw the rest.
            gameModel.draw(canvas, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e ) {
            Log.e("Error:", "interrupted while joining thread");
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return gameModel.handleMotionEvent(event);
    }


}
