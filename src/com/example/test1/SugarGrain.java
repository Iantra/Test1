package com.example.test1;
import android.graphics.Color;

import com.iantra.framework.*;

public class SugarGrain extends GameObject{
	boolean isDead;
	
	public SugarGrain(float xx, float yy){
		setX(xx);
		setY(yy);
		setWidth(6);
		setHeight(6);
		isDead = false;
	}

	public void update(Graphics g){
		if(getY() < g.getHeight()){
			setY(getY()+3);
			setX(getX()+(int)(Math.random()*7)-3);
		}
		if(getY() >= g.getHeight()){
			isDead = true;
		}
	}
	
	public void draw(Graphics g){
			g.drawRect(getX()-getWidth()/2, getY()-getHeight()/2, getWidth(), getHeight(), Color.WHITE);
	}
}
