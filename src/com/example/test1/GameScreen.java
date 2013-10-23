package com.example.test1;

import java.util.List;
import java.util.ArrayList;
import android.graphics.Color;
import android.graphics.Paint;

import com.iantra.framework.Game;
import com.iantra.framework.Graphics;
import com.iantra.framework.Image;
import com.iantra.framework.Screen;
import com.iantra.framework.Input.TouchEvent;

public class GameScreen extends Screen {
    enum GameState {
        Ready, Running, Paused, GameOver
    }

    GameState state = GameState.Ready;

    // Variable Setup
    // You would create game objects here.

    int livesLeft = 1;
    float spawnTime = 0;
    float runningTime = 0;
    boolean checkedLive = false;
    Paint paint;
    int totalSugar = 0;
    ArrayList<SugarGrain> sugar;
    

    public GameScreen(Game game) {
        super(game);

        // Initialize game objects here

        // Defining a paint object
        paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        sugar = new ArrayList<SugarGrain>();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        // We have four separate update methods in this example.
        // Depending on the state of the game, we call different update methods.
        // Refer to Unit 3's code. We did a similar thing without separating the
        // update methods.
        runningTime += deltaTime/100;
        
        if (state == GameState.Ready)
            updateReady(touchEvents);
        if (state == GameState.Running)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.Paused)
            updatePaused(touchEvents);
        if (state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<TouchEvent> touchEvents) {
        
        // This example starts with a "Ready" screen.
        // When the user touches the screen, the game begins. 
        // state now becomes GameState.Running.
        // Now the updateRunning() method will be called!
        
        if (touchEvents.size() > 0)
            state = GameState.Running;
    }
    
    private void addSugar(float x, float y){
    	if(spawnTime >= 0.02){
    		sugar.add(new SugarGrain(x, y));
    		spawnTime = 0;
    		totalSugar++;
    	}
    }
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        
        //This is identical to the update() method from our Unit 2/3 game.
        // 1. All touch input is handled here:
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {        	
            TouchEvent event = touchEvents.get(i);
            
            if (event.type == TouchEvent.TOUCH_DOWN) {
                addSugar(event.x, event.y);
            	
            }
            
            if(event.type == TouchEvent.TOUCH_HOLD) {
            	addSugar(event.x, event.y);
            	
            }
            
            if(event.type == TouchEvent.TOUCH_DRAGGED){
            	addSugar(event.x, event.y);
            	
            }
            
            
            if (event.type == TouchEvent.TOUCH_UP) {
            	spawnTime = 0;
            	if(totalSugar >= 1000 && event.x > game.getGraphics().getWidth() - "Waste 1000 sugars".length()*8 && event.y <= 64){
            		totalSugar -= 1000;
            	}
            }else{
            	
            	spawnTime += deltaTime/100;
            	
            }
            
        }
        
        // 2. Check miscellaneous events like death:
        
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }
        
        
        // 3. Call individual update() methods here.
        for(int i = 0; i < sugar.size(); i++){
        	if(!sugar.get(i).isDead)
        		sugar.get(i).update(game.getGraphics());
        	else{
        		sugar.remove(i);
        		i--;
        	}
        }
        // This is where all the game updates happen.
        // For example, robot.update();
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 300 && event.x < 980 && event.y > 100
                        && event.y < 500) {
                    nullify();
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }

    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();

        // First draw the game elements.
        g.clearScreen(0x000000);
        
        for(int i = 0; i < sugar.size(); i++){
        	if(!sugar.get(i).isDead)
        		sugar.get(i).draw(g);
        }
        // Example:
        // g.drawImage(Assets.background, 0, 0);
        // g.drawImage(Assets.character, characterX, characterY);

        // Secondly, draw the UI above the game elements.
        if (state == GameState.Ready)
            drawReadyUI();
        if (state == GameState.Running)
            drawRunningUI();
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUI();

    }

    private void nullify() {

        // Set all variables to null. You will be recreating them in the
        // constructor.
        paint = null;

        // Call garbage collector to clean up memory.
        System.gc();
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Tap each side of the screen to move in that direction.",
                640, 300, paint);

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.drawString(sugar.size()+" sugars drawn", (sugar.size()+" sugars drawn").length()*8, 32, paint);
        g.drawString(totalSugar+" sugars in bank", (totalSugar+" sugars in bank").length()*8, 64, paint);
        if(totalSugar >= 1000){
        	g.drawString("Waste 1000 sugars", g.getWidth() - "Waste 1000 sugars".length()*8, 32, paint);
        }
        //g.drawString(runningTime+"", (runningTime+"").length()*12, 64, paint);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        // Darken the entire screen so you can display the Paused screen.
        g.drawARGB(155, 0, 0, 0);

    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 640, 300, paint);

    }

    @Override
    public void pause() {
        /*if (state == GameState.Running)
            state = GameState.Paused;*/

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        pause();
    }
}