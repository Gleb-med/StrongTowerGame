package com.example.strongtower.engine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Engine {

    private Model model;
    private Render render;

    private SurfaceHolder.Callback surfaceCallback;
    private SurfaceHolder surfaceHolder;

    private volatile boolean stopped;

    long time = System.nanoTime();

    Runnable threadRunnable = new Runnable() {
        @Override
        public void run() {
            while (!stopped) {
                Canvas canvas;
                if (surfaceHolder == null || (canvas = surfaceHolder.lockCanvas()) == null) {
                    synchronized (Engine.this) {
                        try {
                            Engine.this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    long timeElapsed = System.nanoTime() - time;
                    time = System.nanoTime();
                    model.update(timeElapsed);
                    render.draw(canvas, model);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    };

    public Engine (SurfaceView surfaceView) {
        model = new Model();
        render = new Render();

        Thread engineThread = new Thread(threadRunnable, "EngineThread");
        engineThread.start();

        surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                Engine.this.surfaceHolder = surfaceHolder;
                synchronized (Engine.this) {
                    Engine.this.notifyAll();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                Engine.this.surfaceHolder = surfaceHolder;
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                Engine.this.surfaceHolder = null;
            }
        };

        surfaceView.getHolder().addCallback(surfaceCallback);
    }

    public void stop() {
        this.stopped = true;
    }
}