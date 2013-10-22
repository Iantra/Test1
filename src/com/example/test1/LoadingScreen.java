package com.example.test1;


import com.iantra.framework.Game;
import com.iantra.framework.Graphics;
import com.iantra.framework.Screen;
import com.iantra.framework.Graphics.ImageFormat;


public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }


    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("NASAEarth-01.jpg", ImageFormat.RGB565);
        //Assets.click = game.getAudio().createSound("explode.ogg");


        
        game.setScreen(new GameScreen(game));


    }


    @Override
    public void paint(float deltaTime) {


    }


    @Override
    public void pause() {


    }


    @Override
    public void resume() {


    }


    @Override
    public void dispose() {


    }


    @Override
    public void backButton() {


    }
}
