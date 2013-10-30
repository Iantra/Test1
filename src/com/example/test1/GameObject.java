package com.example.test1;

public class GameObject {
	private float x, y;
	private int width, height;
	
	public void setX(float xx){
		this.x = xx;
	}
	
	public void setY(float yy){
		this.y = yy;
	}
	
	public float getX(){
		return this.x;
	}
	
	public float getY(){
		return this.y;
	}
	
	public void setWidth(int w){
		this.width = w;
	}
	
	public void setHeight(int h){
		this.height = h;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public int getHeight(){
		return this.height;
	}
}
