package com.example.myles.testingwaters;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class TriangleMainThread extends Thread {

    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private TriangleGame triangleGame;
    private boolean running;
    public static Canvas canvas;

    public TriangleMainThread(SurfaceHolder surfaceHolder, TriangleGame triangleGame) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.triangleGame = triangleGame;
    }

    @Override
    public void run() {

        while (running) {
            canvas = null;
        }

        try {
            canvas = this.surfaceHolder.lockCanvas();;
            synchronized (surfaceHolder) {
                this.triangleGame.update();
                this.triangleGame.draw(canvas);
            }
        } catch (Exception e){

        }
    }

    public void setRunning(boolean b) {
        running = b;
    }

}
