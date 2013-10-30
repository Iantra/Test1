package com.example.test1;

import android.graphics.Color;

import com.iantra.framework.Graphics;

public class UpgradeTab extends GameObject{
	
	private boolean open = false;
	private boolean moving = false;
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
			if(open || !moving)
				g.drawRect(getX()+64+5, 5, g.getWidth()-10, g.getHeight()-10, Color.rgb(0, 200, 0));
	}
	
	public void move(float xx){
		moving = true;
		setX(xx);
		if(!open && getX() <= 1000){
			open = true;
			xdir = -32;
		}else if(open && getX() >= 200){
			open = false;
			xdir = 32;
		}
	}
	
	public void update(Graphics g){
		if(!moving){
			if(open)
				 if(getX() > -getWidth())
					 setX(getX()-16);
				 else
					 setX(-getWidth());
			else
				if(getX() < g.getWidth()-getWidth())
					setX(getX()+16);
				else
					setX(g.getWidth()-getWidth());
		}
		else
			moving = false;
	}
	
	public boolean isOpen(){
		return open;
	}
	
	public void setOpen(boolean o){
		open = o;
	}
}
