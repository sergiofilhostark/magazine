package com.summercrow.spacetip.servidor;

public class Nave {
	
	private float x;
	private float y;
	private float altura;
	private float largura;

	private boolean atingido;
	
	public Nave(float x, float y, float largura, float altura){
		this.x = x;
		this.y = y;
		this.altura = altura;
		this.largura = largura;

		atingido = false;
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
	public boolean isAtingido() {
		return atingido;
	}
	public void setAtingido(boolean atingido) {
		this.atingido = atingido;
	}
	
	public boolean isAcertou(float xTiro, float yTiro ){
		
		boolean acertou = 
				(xTiro >= x) && 
				(xTiro <= (x + largura)) && 
				(yTiro >= y) && (yTiro <= (y + altura));
		
		return acertou;
	}

}
