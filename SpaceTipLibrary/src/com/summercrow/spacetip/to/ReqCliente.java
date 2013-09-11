package com.summercrow.spacetip.to;

import java.io.Serializable;

public abstract class ReqCliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static int LOGIN = 1;
	public static int NAVES_POSICIONADAS = 2;
	public static int ATIRAR = 3;
	
	private int tipo;
	
	public ReqCliente(int tipo){
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

}
