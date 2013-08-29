package com.summercrow.spacetip.to;

import java.util.List;

public class InicioDeJogo {
	
	private boolean seuTurno;
	private List<DadosNave> navesAdversario;
	
	public boolean isSeuTurno() {
		return seuTurno;
	}
	public void setSeuTurno(boolean seuTurno) {
		this.seuTurno = seuTurno;
	}
	public List<DadosNave> getNavesAdversario() {
		return navesAdversario;
	}
	public void setNavesAdversario(List<DadosNave> navesAdversario) {
		this.navesAdversario = navesAdversario;
	}

}
