package com.example.strongtower.drawBattlefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.strongtower.R;

public class Castle {
    public int hitPoint = 10;
    private Canvas canvas;
    private Bitmap castle;
    private Paint paint = new Paint();
    private int castleHeight;

    public final double Y_POSITION = 934;
    public final double X_POSITION = 300;

    public Castle(Canvas canvas) {
        this.canvas = canvas;
        this.castleHeight = canvas.getHeight() + 300;

        //draw();
    }


    protected void draw(Context context, Canvas canvas) {
        castle = BitmapFactory.decodeResource(context.getResources(), R.drawable.castle2);
        canvas.drawBitmap(castle, canvas.getWidth()-450, canvas.getHeight()-1045, paint);
        //paint.setColor(Color.rgb(91, 57, 23));
        //canvas.drawRect(canvas.getWidth() - 400, castleHeight, canvas.getWidth(), canvas.getHeight() / 2, paint);
    }
}

//enemy = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
//        canvas.drawBitmap(enemy, enemyXCoordinate, canvas.getHeight()/2+100, paint);
