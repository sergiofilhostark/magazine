package com.summercrow.spacetip.server;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
	
	private String nick;
	private Long id;
	private int posicao;
	private List<Nave> naves = new ArrayList<Nave>();
	
	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	
	
	public List<Nave> getNaves() {
		return naves;
	}
	
	public void addNave(Nave nave){
		naves.add(nave);
	}
	

}
