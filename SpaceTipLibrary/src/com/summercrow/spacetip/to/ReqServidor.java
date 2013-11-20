package com.summercrow.spacetip.to;

import java.io.Serializable;

public class ReqServidor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int LOGIN_EFETUADO = 1;
	public static final int PEDIR_POSICIONAMENTO = 2;
	public static final int INICIO_DE_JOGO = 3;
	public static final int RESULTADO_TIRO = 4;
	public static final int JOGO_ABANDONADO = 5;
	
	private int tipo;
	
	public ReqServidor(int tipo){
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	
	
	

}
