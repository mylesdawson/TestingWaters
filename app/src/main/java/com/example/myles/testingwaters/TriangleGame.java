package com.example.myles.testingwaters;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class TriangleGame extends SurfaceView implements SurfaceHolder.Callback {


    private TriangleMainThread mainThread;

    public TriangleGame(Context context) {
        super(context);

        // add callback so we can detect presses on phone\
        getHolder().addCallback(this);
        mainThread = new TriangleMainThread(getHolder(), this);

        // make game panel focusable so it can handle events
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            } catch (InterruptedException e) {

            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // safely start the game loop
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void update() {

    }
}

