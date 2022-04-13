package com.example.strongtower.drawBattlefield;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

public class GameCore {
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    // массив со стрелами
    public static ArrayList<Arrow> arrows = new ArrayList<Arrow>();
    private Context context;




    public GameCore(Context context) {
        this.context = context;

        // ставим ограничение на создание новых врагов
        if (enemies.size() < 4) {
            // Генератор врагов
            new EnemyGenerator();
        }


        // Крепость
      //  new Castle(this.canvas);  // Убрал и перенес замок в основной поток, пусть рисуется там

        // отрисовывыем врагов
      //  drawEnemies();    // перенесено в главный поток
    }


    // рисовка всех врагов
    public static void drawEnemies(Context context, Canvas canvas) {
        for (Enemy enemy : enemies) {
            enemy.enemyXCoordinate += enemy.enemySpeed;

            if (enemy.enemyXCoordinate - 30 > (canvas.getWidth() - 460)) {
                enemy.enemyXCoordinate = -1000;
            }

            enemy.draw(context, canvas);
        }
    }
//    public static void drawBows(Context context,Canvas canvas){
//        for (Arrow arrow : arrows) {
//            arrow.draw(context,canvas);
//        }
//    }

    // замена, чтобы проект не падал
    public static void drawBows(Context context,Canvas canvas){
        for (int i = 0; i < arrows.size(); i ++){
            arrows.get(i).draw(context,canvas);
        }
    }

    public static void CreateArrow(int x, int y){
        arrows.add(new Arrow(x,y));
    }

    public static void coordinate_delete_arrow(int width, int height) {
        for (int i = 0; i < arrows.size(); i ++){
            arrows.get(i).changeCoordinate();
            if (arrows.get(i).isOut(width,height)){
                arrows.remove(i);
            }
        }
    }

}
