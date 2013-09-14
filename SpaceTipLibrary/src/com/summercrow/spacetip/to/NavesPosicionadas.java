package com.summercrow.spacetip.to;

import java.util.List;

public class NavesPosicionadas extends ReqCliente{
	
	private static final long serialVersionUID = 1L;
	
	
	private List<DadosNave> dadosNaves;
	
	public NavesPosicionadas(){
		super(NAVES_POSICIONADAS);
	}
	
	
	public List<DadosNave> getDadosNaves() {
		return dadosNaves;
	}
	public void setDadosNaves(List<DadosNave> dadosNaves) {
		this.dadosNaves = dadosNaves;
	}

}
