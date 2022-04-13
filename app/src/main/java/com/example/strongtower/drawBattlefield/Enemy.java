package com.example.strongtower.drawBattlefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.strongtower.R;

import java.util.Timer;
import java.util.TimerTask;

// создаем класс врага
public class Enemy {
    public int enemyXCoordinate = 0;
    public int enemySpeed = 0;
    public String enemyType;
    public int enemyWidth = 30;
    private Paint paint;
    private Bitmap enemy;

    public Enemy(int enemySpeed) {
        this.paint = new Paint();
        this.enemySpeed = enemySpeed;
        this.paint = paint;
    }

    public void draw(Context context, Canvas canvas) {
        enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        canvas.drawBitmap(enemy, enemyXCoordinate, canvas.getHeight()/2+100, paint);
        //canvas.drawRect(enemyXCoordinate, canvas.getHeight() / 2 + 300, enemyXCoordinate + enemyWidth, canvas.getHeight() / 2 + 200, this.paint);
    }
}

//    public void draw(Context context, Canvas canvas) {
//        arrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.m_arrow);
//        canvas.drawBitmap(arrow, arrowXCoordinate, arrowYCoordinate, paint);
//    }
