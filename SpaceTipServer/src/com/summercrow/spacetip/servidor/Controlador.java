package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.List;



public class Controlador {
	
	private List<Partida> partidasAguardando = new ArrayList<Partida>();
	
	private long idJogadores = 1;

	public Jogador criarJogador(String nome) {
		Jogador jogador = new Jogador();
		jogador.setNome(nome);
		jogador.setId(proximoIdJogador());
		Partida partida = getPartida();
		jogador.setPartida(partida);
		
		return jogador;
	}
	
	//TODO verificar a necessidade desse metodo
	public void entrarPartida(Jogador jogador) {
		Partida partida = jogador.getPartida();
		partida.entrar(jogador);
	}
	
	private synchronized Partida getPartida(){
		if(partidasAguardando.size() > 0){
			return partidasAguardando.remove(0);
		}
		else {
			Partida partida = new Partida();
			partidasAguardando.add(partida);
			return partida;
		}
	}
	
	private synchronized long proximoIdJogador(){
		return idJogadores++;
	}

}
