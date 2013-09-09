package com.summercrow.spacetip.to;

import java.io.Serializable;
import java.util.List;

public class ResultadoTiro implements Serializable{
	
	private Tiro tiro;
	private boolean meuTiro;
	private Integer naveAtingida;
	private boolean derrotou;
	
	
	public boolean isDerrotou() {
		return derrotou;
	}
	public void setDerrotou(boolean derrotou) {
		this.derrotou = derrotou;
	}
	public boolean isMeuTiro() {
		return meuTiro;
	}
	public void setMeuTiro(boolean meuTiro) {
		this.meuTiro = meuTiro;
	}
	public Tiro getTiro() {
		return tiro;
	}
	public void setTiro(Tiro tiro) {
		this.tiro = tiro;
	}
	public Integer getNaveAtingida() {
		return naveAtingida;
	}
	public void setNaveAtingida(Integer naveAtingida) {
		this.naveAtingida = naveAtingida;
	}

}
