package com.summercrow.spacetip.to;

import java.util.List;

public class InicioDeJogo extends ReqServidor{
	
	private static final long serialVersionUID = 1L;
	
	private boolean meuTurno;
	private List<DadosNave> navesAdversario;
	
	public InicioDeJogo() {
		super(INICIO_DE_JOGO);
	}
	
	public boolean isMeuTurno() {
		return meuTurno;
	}
	public void setMeuTurno(boolean meuTurno) {
		this.meuTurno = meuTurno;
	}
	public List<DadosNave> getNavesAdversario() {
		return navesAdversario;
	}
	public void setNavesAdversario(List<DadosNave> navesAdversario) {
		this.navesAdversario = navesAdversario;
	}

}
