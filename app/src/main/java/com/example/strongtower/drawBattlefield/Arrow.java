package com.example.strongtower.drawBattlefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.strongtower.R;


// вообще скопируй весь. тут совсем все не так, как было
public class Arrow {
    public static int arrowXCoordinate;
    public static int arrowYCoordinate;
    public int curr_x;
    public int curr_y;
    public int towardX;
    public int towardY;
    public float angle;
    private Bitmap arrow;
    private Paint paint;

    //////////////////////////////много изменено
    public Arrow(int towardX, int towardY) {
        this.paint = new Paint();
        this.towardX = towardX;
        this.towardY = towardY;
        curr_x = arrowXCoordinate;
        curr_y = arrowYCoordinate;
        this.paint = paint;
        float angles = (float) Math.toDegrees(Math.atan2(arrowXCoordinate - towardX, arrowYCoordinate - towardY));
        this.angle = (float) (angles + Math.ceil(- angles / 360) *360) + 40;
    }
    // тоже поменял
    public boolean isOut(int canvasWidth, int canvasHeight){                                                                             // косяк
        return (curr_x > canvasWidth+50) || (curr_x < -50 || curr_y < -50 || curr_y > canvasHeight+50);
    }
    // полностью поменял
    public void changeCoordinate(){
        //Вертикальная и горизонтальная скорость стрелы
        int arrowXSpeed = Math.abs(towardX-arrowXCoordinate)/10;
        int arrowYSpeed = Math.abs(towardY-arrowYCoordinate)/10;



        //Движение стрелы
        if (arrowXCoordinate >= towardX) curr_x -=arrowXSpeed;
        if (arrowXCoordinate <= towardX) curr_x += arrowXSpeed;
        if (arrowYCoordinate >= towardY) curr_y -=arrowYSpeed;
        if (arrowYCoordinate <= towardY) curr_y +=arrowYSpeed;

    }
    ///////////////////////////////////изменен метод отрисовки - стал поворачиваться канвас
    public void draw(Context context,Canvas canvas) {
        arrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.m_arrow);
        canvas.rotate(-(angle-90), curr_x, curr_y);
        canvas.drawBitmap(arrow, curr_x, curr_y, paint);
        canvas.rotate((angle-90), curr_x, curr_y);
    }

}