package com.summercrow.spacetip.to;

import java.io.Serializable;

public class Tiro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idJogador;
	public Long getIdJogador() {
		return idJogador;
	}
	public void setIdJogador(Long idJogador) {
		this.idJogador = idJogador;
	}
	private float x;
	private float distancia;
	private float y;
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getDistancia() {
		return distancia;
	}
	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

}
