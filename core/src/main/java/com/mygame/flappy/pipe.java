package com.mygame.flappy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
public class pipe {
    public int x;

    public int y;

    public int h_botton;
    public int h_top;
    public int gap = 300;

    public pipe(int x){
        this.h_botton = MathUtils.random(50, Gdx.graphics.getHeight() - gap - 100);
         this.x = x;
        // 2. Вычисляем точку начала верхней трубы (Y)
        // Это высота нижней + зазор
        this.y = h_botton + gap;

        // 3. Вычисляем высоту самой верхней трубы
        // Это общая высота экрана минус точка начала трубы
        this.h_top = Gdx.graphics.getHeight() - y;

    }

   public void get_random(){
       this.h_botton=MathUtils.random(50,Gdx.graphics.getHeight() - gap - 100);
       this.y=h_botton+gap;
       this.h_top=Gdx.graphics.getHeight()-h_botton;
   }

   public void move_x(){
        x-=5;
   }
}
