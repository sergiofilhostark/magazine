package com.summercrow.spacetip.cliente;

import android.widget.ImageView;

public class Nave {

	private float x;
	private float y;
	private float altura;
	private float largura;
	private boolean atingido;
	private ImageView imageView;

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
	
	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public float getLargura() {
		return largura;
	}

	public void setLargura(float largura) {
		this.largura = largura;
	}
	
	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
	public boolean isAcertou(float xTiro, float yTiro ){
		
		
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
