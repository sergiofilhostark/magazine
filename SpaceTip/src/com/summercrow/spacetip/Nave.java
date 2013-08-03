package com.summercrow.spacetip;

public class Nave {
	
	private float x;
	private float y;

	private boolean atingido;
	
	public Nave(float x, float y){
		this.x = x;
		this.y = y;

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
	
	public boolean isAcertou(float xTiro, float yTiro, int largura, int altura){
		
		
		System.out.println(xTiro+ " " +yTiro+ " " +x+ " " +y+ " " +largura+ " " +altura);
		
		boolean acertou = (xTiro >= x) 
		&& (xTiro <= (x + largura))
		&& (yTiro >= y) 
		&& (yTiro <= (y + altura));
		
		if(acertou){
			System.out.println("Acertou");
		}
		
		return acertou;
	}

}
