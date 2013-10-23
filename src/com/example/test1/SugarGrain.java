package com.example.test1;
import android.graphics.Color;

import com.iantra.framework.*;

public class SugarGrain {
	private float x,y;
	boolean isDead;
	
	public SugarGrain(float xx, float yy){
		x = xx;
		y = yy;
		isDead = false;
	}

	public void update(Graphics g){
		if(y < g.getHeight()){
			y += 3;
			x += (int)(Math.random()*7)-3;
		}
		if(y >= g.getHeight()){
			isDead = true;
		}
	}
	
	public void draw(Graphics g){
			g.drawRect(x-3, y-7, 8, 8, Color.WHITE);
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
}
