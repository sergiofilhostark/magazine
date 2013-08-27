package com.summercrow.spacetip.to;

public class DadosNave {
	
	private float x;
	private float y;
	private float altura;
	private float largura;
	
	public DadosNave(float x, float y, float largura, float altura){
		this.x = x;
		this.y = y;
		this.altura = altura;
		this.largura = largura;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
