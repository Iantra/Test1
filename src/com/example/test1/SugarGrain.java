package com.example.test1;
import com.iantra.framework.*;

public class SugarGrain {
	private float x,y;
	
	public SugarGrain(float xx, float yy){
		x = xx;
		y = yy;
	}

	public void update(){
		y++;
	}
	
	public void draw(Graphics g){
			g.drawRect(x, y, 10, 10, 0xFF);
	}
	
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
}
