package com.example.strongtower.drawBattlefield;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;

import com.example.strongtower.MainActivity;
import com.example.strongtower.R;

public class DrawBattlefield extends Thread {
    // здесь хранятся все о статической отрисовке(все то что не меняется)
    public int groundHeight = 0;
    public int towerHeight = 0;

    private SurfaceHolder surfaceHolder;

    private volatile boolean running = true; //флаг для остановки потока
    private Bitmap back;
    private Bitmap bow;
    private Context context;
    private Enemy enemy;
    private GameCore gamecore;
    public int count_kill;

    public int towardPointX;
    public int towardPointY;

    public void setTowardPoint(int x, int y) {
        towardPointX = x;
        towardPointY = y;
    }
    public int getTowardPointX(){
        return towardPointX;
    }

    public int getTowardPointY(){
        return towardPointY;
    }

    // Функция для подсчета угла между точками
    public static float calculateAngle(float x1, float y1, float x2, float y2) {
        float angle = (float) Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        angle = (float) (angle + Math.ceil(- angle / 360) *360);
        return angle;
    }


    public DrawBattlefield(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;
        new GameCore(this.context);
        /* Добавляем картинку лука */
        bow = BitmapFactory.decodeResource(context.getResources(), R.drawable.bow);
        /* Добавляем картинку стрелы*/
        //arrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.m_arrow);
        back = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    Paint paint = new Paint();

                    // ХОЛСТ
                    paint.setAntiAlias(true); // сглаживание
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.rgb(128, 166, 255));
                    canvas.drawPaint(paint);

                    // Солнце
                    new Sun(canvas, paint);

                    // Земля
                    //new Ground(canvas, paint);

                    // Фон
                    canvas.drawBitmap(back, 0, 0, paint);

                    // Замок
                    //new Castle(context, canvas);
//                    Castle castle = new Castle(canvas);
//                    castle.draw(context, canvas);

                    GameCore.coordinate_delete_arrow(canvas.getWidth(), canvas.getHeight());
                    GameCore.drawBows(context,canvas);
                    GameCore.drawEnemies(context, canvas);

                    // убийство врагов
                    for (Enemy enemy : GameCore.enemies) {
                        for (Arrow arrow : GameCore.arrows){
                            if ((enemy.enemyXCoordinate + 20 > arrow.curr_x && enemy.enemyXCoordinate - 20 < arrow.curr_x) && (arrow.curr_y > (canvas.getHeight() / 2 + 200)) && (arrow.curr_y < (canvas.getHeight() / 2 + 500) )) {
                                count_kill++;
                                enemy.enemyXCoordinate = -1000;
                            }
                        }
                    }


                    /* Рисуем лук */
                    int bow_x = canvas.getWidth()/2 + 675 + 15 + 165;
                    int bow_y = canvas.getHeight()/2 - 275 + 75 - 100;

                    float rotate_bow_x_center = bow_x ; //центр поворота по оси X + (bow.getWidth()/2) т.к LANDSCAPE -- это Y!!! -- ТУТ ВСЁ ЗАЕБИСЬ, РАБОТАЕМ С X
                    float rotate_bow_y_center = bow_y + 200; // центр поворота по оси Y + (bow.getHeight()/2) -- ТУТ ВСЁ ЗАЕБИСЬ, РАБОТАЕМ С X

                    //запись координат лука в стрелу
                    Arrow.arrowXCoordinate = (int)rotate_bow_x_center;
                    Arrow.arrowYCoordinate = (int)rotate_bow_y_center;
                    //Находим угол по двум точкам
                    float rotate_bow_angle = calculateAngle(towardPointX, towardPointY, rotate_bow_x_center, rotate_bow_y_center);

                    //Поворачиваем холст
                    canvas.rotate(-(rotate_bow_angle-90), rotate_bow_x_center, rotate_bow_y_center);
                    //Рисуем лук
                    canvas.drawBitmap(bow, bow_x, bow_y, paint);

                    //Переворачиваем холст обратно, на прежний угол
                    canvas.rotate(rotate_bow_angle-90, rotate_bow_x_center, rotate_bow_y_center);

                    paint.setColor(Color.rgb(100, 126, 185));
                    paint.setTextSize(80);
                    canvas.drawText("Убито врагов: "+ count_kill, canvas.getWidth()/2-1000, canvas.getHeight()/2-400, paint);
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
