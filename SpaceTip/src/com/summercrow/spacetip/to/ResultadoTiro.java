package com.summercrow.spacetip.to;

import java.util.List;

public class ResultadoTiro {
	
	private Tiro tiro;
	private boolean meuTiro;
	private Integer naveAtingida;

	
	
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
