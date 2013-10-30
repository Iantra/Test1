package com.example.test1;

import android.graphics.Color;

import com.iantra.framework.Graphics;

public class UpgradeTab extends GameObject{
	
	private boolean open = false;
	private boolean moving = false;
	private float prevx = 0, xspeed = 0;
	public int xdir = 32;
	
	public UpgradeTab(Graphics g){
		setHeight(64);
		setWidth(64);
		setX(g.getWidth()-getWidth());
		setY(64);
	}
	
	public void draw(Graphics g){
			g.drawRect(getX(), getY(), getWidth(), getHeight(), Color.rgb(0, 255, 0));
			g.drawRect(getX()+3, getY()+3, getWidth()-6, getHeight()-6, Color.BLACK);
			g.drawRect(getX()+64+5, 5, g.getWidth()-10, g.getHeight()-10, Color.rgb(0, 200, 0));
	}
	
	public void move(float xx){
		xspeed = xx - prevx;
		moving = true;
		setX(xx);
		if(!open && xspeed < -10){
			open = true;
			xdir = -32;
		}else if(open && xspeed >= 10){
			open = false;
			xdir = 32;
		}
		prevx = xx;
	}
	
	public void update(Graphics g){
		if(!moving){
			if(xspeed < -10 && xspeed > -16)
				xspeed = -16;
			if(xspeed > 10 && xspeed < 16)
				xspeed = 16;
			/* BUGGY if(xspeed == 0 && getX() != g.getWidth()-getWidth() && getX() != -getWidth())
				xspeed = 16;*/
			if(open)
				 if(getX() > -getWidth())
					 setX(getX()+xspeed);
				 else{
					 setX(-getWidth());
					 xspeed = 0;
					 }
			else
				if(getX() < g.getWidth()-getWidth())
					setX(getX()+xspeed);
				else{
					setX(g.getWidth()-getWidth());
					xspeed = 0;
				}
			}
		moving = false;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	public void setOpen(boolean o){
		open = o;
	}
}
