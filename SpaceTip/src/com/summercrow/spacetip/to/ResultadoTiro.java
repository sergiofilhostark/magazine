package com.summercrow.spacetip.to;

import java.util.List;

public class ResultadoTiro {
	
	private boolean meuTiro;
	private boolean meuTurno;
	private List<Integer> navesAtingidas;
	
	public boolean isMeuTiro() {
		return meuTiro;
	}
	public void setMeuTiro(boolean meuTiro) {
		this.meuTiro = meuTiro;
	}
	public boolean isMeuTurno() {
		return meuTurno;
	}
	public void setMeuTurno(boolean meuTurno) {
		this.meuTurno = meuTurno;
	}
	public List<Integer> getNavesAtingidas() {
		return navesAtingidas;
	}
	public void setNavesAtingidas(List<Integer> navesAtingidas) {
		this.navesAtingidas = navesAtingidas;
	}

}
