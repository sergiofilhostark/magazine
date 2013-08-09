package com.summercrow.spacetip.server;

public class Partida {
	
	private Jogador jogador1;
	private Jogador jogador2;
	private int turno;
	
	//TODO colocar numa enum??
	private final int POSICIONANDO = 1;
	private final int AGUARDANDO_INICIO = 2;
	private final int EM_JOGO = 3;
	private final int JOGO_ACABOU = 4;
	
	public void iniciar(){
		jogador1 = new Jogador();
		jogador2 = new Jogador();
		turno = 1;
	}

}
