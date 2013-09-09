package com.summercrow.spacetip.to;

import java.io.Serializable;
import java.util.List;

public class NavesPosicionadas implements Serializable{
	
	private Long idJogador;
	private List<DadosNave> dadosNaves;
	
	public Long getIdJogador() {
		return idJogador;
	}
	public void setIdJogador(Long idJogador) {
		this.idJogador = idJogador;
	}
	public List<DadosNave> getDadosNaves() {
		return dadosNaves;
	}
	public void setDadosNaves(List<DadosNave> dadosNaves) {
		this.dadosNaves = dadosNaves;
	}

}
