package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.List;



public class Controlador {
	
	private List<Partida> partidasAguardando = new ArrayList<Partida>();
	
	private long idJogadores = 1;
	private long idPartidas = 1;

	public Jogador criarJogador(String nome) {
		Jogador jogador = new Jogador();
		jogador.setNome(nome);
		jogador.setId(proximoIdJogador());
		Partida partida = getPartida();
		jogador.setPartida(partida);
		
		return jogador;
	}
	
	public void entrarPartida(Jogador jogador) {
		Partida partida = jogador.getPartida();
		partida.entrar(jogador);
	}
	
	public synchronized void removerPardidaAguardando(Jogador jogador){
		Partida partida = jogador.getPartida();
		if(partida.isAguardandoJogador()){
			for (int i = 0; i < partidasAguardando.size(); i++) {
				Partida partidaAguardando = partidasAguardando.get(i);
				if(partidaAguardando.equals(partida)){
					partidasAguardando.remove(i);
					break;
				}
			}
		}
	}
	
	private synchronized Partida getPartida(){
		if(partidasAguardando.size() > 0){
			Partida partida = partidasAguardando.remove(0);
			partida.setAguardandoJogador(false);
			return partida;
		}
		else {
			Partida partida = new Partida(proximoIdPartida());
			partida.setAguardandoJogador(true);
			partidasAguardando.add(partida);
			return partida;
		}
	}
	
	private synchronized long proximoIdJogador(){
		return idJogadores++;
	}
	
	private synchronized long proximoIdPartida(){
		return idPartidas++;
	}

}
