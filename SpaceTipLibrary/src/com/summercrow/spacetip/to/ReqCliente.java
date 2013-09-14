package com.summercrow.spacetip.to;

import java.io.Serializable;

public abstract class ReqCliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int LOGIN = 1;
	public static final int NAVES_POSICIONADAS = 2;
	public static final int ATIRAR = 3;
	
	private int tipo;
	private Long idJogador;
	
	public ReqCliente(int tipo){
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}
	
	public Long getIdJogador() {
		return idJogador;
	}
	
	public void setIdJogador(Long idJogador) {
		this.idJogador = idJogador;
	}

}
