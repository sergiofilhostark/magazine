package com.summercrow.spacetip.to;

import java.io.Serializable;

public abstract class Resposta implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static int LOGIN_EFETUADO = 1;
	public static int PEDIR_POSICIONAMENTO = 2;
	
	private int tipo;
	
	public Resposta(int tipo){
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	
	
	

}
