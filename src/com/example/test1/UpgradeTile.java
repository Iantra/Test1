package com.example.test1;

public class UpgradeTile extends GameObject{
	String name;
	int index;
	int price;
	int level;
	public UpgradeTile(String n, int p, int i){
		name = n;
		price = p;
		index = i;
		level = 0;
		setX(128+128*i);
		setY(128);
		setWidth(64);
		setHeight(64);
	}
	
	public void buy(){
		level++;
		switch(index){
			case 0:	price*=10;
					price++;
					break;
					
			case 1:	price*=2;
					break;
		}
	}
	
	public boolean canAfford(int balance){
			return (balance >= price);
	}
	
}
