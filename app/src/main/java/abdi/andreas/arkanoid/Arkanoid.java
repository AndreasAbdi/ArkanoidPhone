package abdi.andreas.arkanoid;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class Arkanoid extends Activity {

    ArkanoidView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new ArkanoidView(this);
        setContentView(view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onPause() {
        super.onPause();

        view.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.resume();
    }
}
