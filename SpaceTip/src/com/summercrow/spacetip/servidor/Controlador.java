package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.summercrow.spacetip.servidor.proxy.ProxyServidorLocal;

public class Controlador {
	
	private ProxyServidorLocal proxyServidor;
	
	private Map<Long, Jogador> jogadores = new HashMap<Long, Jogador>();
	
	private List<Partida> partidasAguardando = new ArrayList<Partida>();
	
	private long idJogadores = 1;
	
	public Controlador(ProxyServidorLocal proxyServidor) {
		this.proxyServidor = proxyServidor;
	}

	public Jogador login(String nome) {
		Jogador jogador = new Jogador();
		jogador.setNome(nome);
		jogador.setId(proximoIdJogador());
		Partida partida = getPartida();
		jogador.setPartida(partida);
		
		partida.entrar(jogador);
		
		return jogador;
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
