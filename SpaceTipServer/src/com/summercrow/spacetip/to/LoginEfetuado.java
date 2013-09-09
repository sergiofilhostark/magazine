package com.summercrow.spacetip.to;

import java.io.Serializable;

public class LoginEfetuado implements Serializable{
	
	private Long id;
	private int posicao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPosicao() {
		return posicao;
	}
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

}
