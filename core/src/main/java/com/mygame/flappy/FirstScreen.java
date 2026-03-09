package com.mygame.flappy;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.Rectangle;

public class FirstScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Texture birdTexture;
    private Texture backgroundTexture;

    private float birdY;
    private float velocity = 0;
    private float gravity = -0.5f;
    private float jumpForce = 10f;

    private Texture pipeTexture1; // Текстура для нижней трубы
    private Texture pipeTexture2; // Текстура для верхней трубы
    private Array<pipe> pipesArray;
    int pipes_space=700;

    private Texture playBtnTexture;
    private boolean isGameStarted = false;
    private float btnX, btnY, btnWidth = 400, btnHeight = 400;
    private Rectangle birdRect;
    private Rectangle pipeBottomRect;
    private Rectangle pipeTopRect;





    public FirstScreen() {
        batch = new SpriteBatch();
        birdTexture = new Texture("bird.png");
        backgroundTexture = new Texture("back.jpg");
        pipeTexture1 = new Texture("pipe.png");
        pipeTexture2 = new Texture("pipe2.png");
        birdY = Gdx.graphics.getHeight() / 2f;
        playBtnTexture = new Texture("button.png");
        btnX = (Gdx.graphics.getWidth() - btnWidth) / 2f;
        btnY = (Gdx.graphics.getHeight() - btnHeight) / 2f;
        birdRect = new Rectangle();
        pipeBottomRect = new Rectangle();
        pipeTopRect = new Rectangle();


        pipesArray= new Array<pipe>();
         for (int i=0; i<5; i++){

             pipesArray.add(new pipe(Gdx.graphics.getWidth() + i * pipes_space));
        }

    }



    @Override
    public void render(float delta) {



        if (Gdx.input.justTouched()) {
            velocity = jumpForce;
        }

        velocity += gravity;
        birdY += velocity;

        if (birdY < 0) birdY = 0;
        if (birdY > Gdx.graphics.getHeight() -150) {
            birdY = Gdx.graphics.getHeight() - 150;
            velocity = 0;

        }

        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();
        if (!isGameStarted){

            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(playBtnTexture, btnX, btnY, btnWidth, btnHeight);
            if (Gdx.input.justTouched()) {
                float touchX = Gdx.input.getX();
                float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

                if (touchX >= btnX && touchX <= btnX + btnWidth &&
                    touchY >= btnY && touchY <= btnY + btnHeight) {
                    isGameStarted = true;
                }
            }
        }
        else {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.draw(birdTexture, 100, birdY, 100, 100);
            birdRect.set(100,birdY,100,100);


            for (pipe p : pipesArray) {

                p.move_x();
                batch.draw(pipeTexture1, p.x, 0, 200, p.h_botton);
                batch.draw(pipeTexture2, p.x, p.y, 200, p.h_top);
                pipeTopRect.set(p.x, p.y, 200, p.h_top);
                pipeBottomRect.set(p.x, 0, 200, p.h_botton);

                if (birdRect.overlaps(pipeBottomRect )|| birdRect.overlaps(pipeTopRect)){
                    isGameStarted=false;
                    resetGame();
                }
                if (p.x < 0 && p.x <= -150) {
                    System.out.println("Дошел");
                    p.get_random();
                    p.x += pipesArray.size * pipes_space;
                }


            }


        }


        batch.end();

    }

    private  void  resetGame(){
        birdY = Gdx.graphics.getHeight() / 2f;
        velocity = 0;


        for (int i = 0; i < pipesArray.size; i++) {

            pipesArray.get(i).x = Gdx.graphics.getWidth() + i * pipes_space;
            pipesArray.get(i).get_random();
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
        birdTexture.dispose();
        backgroundTexture.dispose();
    }
}
