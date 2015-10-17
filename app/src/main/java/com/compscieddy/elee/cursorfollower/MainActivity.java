package com.compscieddy.elee.cursorfollower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final FrameLayout rootView = (FrameLayout) getLayoutInflater().inflate(R.layout.activity_main, null, false);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            View hoverView;
            int hoverViewWidth = getResources().getDimensionPixelOffset(R.dimen.hover_view_width);
            int hoverViewHeight = getResources().getDimensionPixelOffset(R.dimen.hover_view_height);
            int value = 0;
            float oldX = -1f;
            float oldY = -1f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                if (oldX == -1f) {
                    oldX = x;
                }
                if (oldY == 1f) {
                    oldY = y;
                }
                Log.v(TAG, "" + event);

                float thresholdForSetXY = 4f;
                float thresholdForValueChange = 30f;
                float yAtDown = -1f;

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yAtDown = y;

                        hoverView = new View(MainActivity.this);
                        hoverView.setBackgroundColor(getResources().getColor(R.color.hover_view_bg_color));
                        hoverView.setX(x);
                        hoverView.setY(y);
                        rootView.addView(hoverView, hoverViewWidth, hoverViewHeight);
                        break;
                    case MotionEvent.ACTION_UP:
                        rootView.removeView(hoverView);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (oldX > thresholdForSetXY) {
                            hoverView.setX(x);
                        }
                        if (oldY > thresholdForSetXY) {
                            hoverView.setY(y);
                        }
                        break;
                }
                oldX = x;
                oldY = y;
                return true;
            }
        });

        setContentView(rootView);
    }
}
