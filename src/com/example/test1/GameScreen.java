package com.example.test1;

import java.util.List;
import java.util.ArrayList;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

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
    float autoSpawnTime = 0;
    float runningTime = 0; 
    boolean isBound;
    float autoSpawnRate = 0;
    float spawnRate = 1;
    float carryOver = 0;
    float carryOver2 = 0;
    Paint paint;
    int totalSugar = 0;
    
    //debuggin vars
    int extraSpawned = 0;
    
    UpgradeTab tab;
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
        tab  = new UpgradeTab(game.getGraphics());
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
    	if(spawnTime >= 0.02/spawnRate){
    		carryOver += spawnRate;
    		for(int i = 0; i < carryOver; i++){
    			if((int)carryOver%2 == 1)
    				sugar.add(new SugarGrain(x, y));
    			totalSugar++;
    			carryOver--;
    		}
    	}
    }
    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        
        //This is identical to the update() method from our Unit 2/3 game.
        // 1. All touch input is handled here:
        //int len = touchEvents.size();
        //for (int i = 0; i < len; i++) {
    	try{
            TouchEvent event = touchEvents.get(0);

            if (event.type == TouchEvent.TOUCH_DOWN) {
                handlePress(event);
            }else
            
            if(event.type == TouchEvent.TOUCH_HOLD) {
            	handlePress(event);
            }else
            
            if(event.type == TouchEvent.TOUCH_DRAGGED){
            	handlePress(event);
            }else
            	isBound = false;
            
            if (event.type == TouchEvent.TOUCH_UP) {
            	spawnTime = 0;
            	isBound = false;
            	int ypos = 64;
            	for(int i = 0; i < tab.upgrades.size(); i++){
            		if(isInBounds(event.x, event.y, tab.upgrades.get(i)) && tab.upgrades.get(i).canAfford(totalSugar) && tab.isOpen()){
            			totalSugar -= tab.upgrades.get(i).price;
            			tab.buyUpgrade(i);
            		}
            	}
            	/*for(int j = 0; j < upgradesUnlocked.length; j++){
	            	if(event.x > game.getGraphics().getWidth() - 256 && event.y <= ypos){
	            		if(upgradesUnlocked[0]){
	            			totalSugar -= upgradeCosts[0];
	            			upgradeCosts[0] *= 10;
	            			upgradeCosts[0]++;
	            			autoSpawnRate += 0.2;
	            			ypos+=64;
	            		}else if(upgradesUnlocked[1]){
	            			totalSugar -= upgradeCosts[1];
	            			upgradeCosts[1] *= 2;
	            			spawnRate+=0.2;
	            			ypos+=64;
	            		}
	            		
	            	}
            	}*/
            }else{
            	
            	spawnTime += deltaTime/100;
            	
            }
            
        }catch(Exception e){
        	isBound = false;
        }
        
        // 2. Check miscellaneous events like death:
        
        if (livesLeft == 0) {
            state = GameState.GameOver;
        }
        
        spawnRate = 1+.2f*tab.upgrades.get(0).level;
        autoSpawnRate = .2f*tab.upgrades.get(1).level;
        
        // 3. Call individual update() methods here.
        tab.update(game.getGraphics());
        
        autoSpawnTime += deltaTime;
        
        if(autoSpawnRate != 0 && autoSpawnTime >= 0.02/autoSpawnRate){
    		carryOver2 += autoSpawnRate;
    		for(int i = 0; i < carryOver2; i++){
    			sugar.add(new SugarGrain(256, 0));
    			totalSugar++;
    			carryOver2--;
    		}
    		autoSpawnTime = 0;
    		
    	}
        
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

    
    private void handlePress(TouchEvent event) {
    	if(isInBounds(event.x+tab.xdir, tab.getY(), tab) || isBound){
    		tab.move(event.x);
    		isBound = true;
    	}else{
    		addSugar(event.x, event.y);
    	}
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
        g.drawString("Press anywhere to make some sugar.", 640, 300, paint);
        drawRunningUI();

    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.drawString(sugar.size()+" sugars drawn", (sugar.size()+" sugars drawn").length()*8, 32, paint);
        g.drawString(totalSugar+" sugars in bank", (totalSugar+" sugars in bank").length()*7, 64, paint);
        tab.draw(g, totalSugar);
        
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
    
    public boolean isInBounds(float x, float y, GameObject o){
    	if(x >= o.getX() && x <= o.getX() + o.getWidth() && y >= o.getY() && y <= o.getY() + o.getHeight()){
    		return true;
    	}
    	return false;
    }
}