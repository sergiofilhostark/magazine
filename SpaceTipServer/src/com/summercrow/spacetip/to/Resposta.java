package com.summercrow.spacetip.to;

import java.io.Serializable;

public class Resposta implements Serializable{
	
	private int id;
	private int status;
	private String mensagem;
	
	//teste

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
