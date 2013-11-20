package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.List;

import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class Partida {
	
	private List<Jogador> jogadores;

	
	
	


	public Partida(){
		jogadores = new ArrayList<Jogador>();
	}
	
	public synchronized void entrar(Jogador jogador){
		jogadores.add(jogador);
		jogador.setPosicao(jogadores.size() - 1);
		
		jogador.getProxyServidor().enviarLoginEfetuado();
		
		
		
		if(jogadores.size() > 1){
			pedirPosicionamento();
		}
		
	}
	
	private void pedirPosicionamento() {
		for (Jogador cadaJogador: jogadores) {
			cadaJogador.getProxyServidor().enviarPedidoPosicionamento();
		}
	}



	public void navesPosicionadas(Jogador jogador, List<DadosNave> dadosNaves) {
		for (DadosNave dadosNave : dadosNaves) {
			Nave nave = new Nave(dadosNave.getX(), dadosNave.getY(), dadosNave.getLargura(), dadosNave.getAltura());
			jogador.addNave(nave);
		}
		
		boolean todasPosicionadas = true;
		for (Jogador cadaJogador: jogadores) {
			if(cadaJogador.getNaves().isEmpty()){
				todasPosicionadas = false;
				break;
			}
		}
		if(todasPosicionadas){
			
			Jogador jogador1 = jogadores.get(0);
			Jogador jogador2 = jogadores.get(1);
			
			enviarInicioDeJogo(jogador1, true, jogador2);
			enviarInicioDeJogo(jogador2, false, jogador1);
		}
	}

	private void enviarInicioDeJogo(Jogador jogador, boolean meuTurno, Jogador adversario) {
		InicioDeJogo inicioDeJogo = new InicioDeJogo();
		inicioDeJogo.setMeuTurno(meuTurno);
		List<DadosNave> dadosNavesAdversario = montarDadosNaveAdversario(adversario);
		inicioDeJogo.setNavesAdversario(dadosNavesAdversario);
		jogador.getProxyServidor().enviarInicioDeJogo(inicioDeJogo);
	}

	private List<DadosNave> montarDadosNaveAdversario(Jogador jogador) {
		List<DadosNave> dadosNavesAdversario = new ArrayList<DadosNave>();
		List<Nave> naves = jogador.getNaves();
		for (Nave nave : naves) {
			DadosNave dadosNave = new DadosNave();
			dadosNave.setAltura(nave.getAltura());
			dadosNave.setLargura(nave.getLargura());
			dadosNave.setX(nave.getX());
			dadosNave.setY(nave.getY());
			dadosNavesAdversario.add(dadosNave);
		}
		return dadosNavesAdversario;
	}

	public void atirar(Jogador jogador, Tiro tiro) {
		Jogador adversario = getAdversario(jogador);
		
		Integer naveAtingida = verificarAcerto(tiro.getX(), tiro.getY(), tiro.getDistancia(), adversario);
		
		ResultadoTiro resultadoTiroMeu = new ResultadoTiro();
		resultadoTiroMeu.setMeuTiro(true);
		resultadoTiroMeu.setTiro(tiro);
		resultadoTiroMeu.setNaveAtingida(naveAtingida);
		resultadoTiroMeu.setDerrotou(adversario.isDerrotado());
		jogador.getProxyServidor().enviarResultadoTiro(resultadoTiroMeu);
		
		ResultadoTiro resultadoTiroAdversario = new ResultadoTiro();
		resultadoTiroAdversario.setMeuTiro(false);
		resultadoTiroAdversario.setTiro(tiro);
		resultadoTiroAdversario.setNaveAtingida(naveAtingida);
		resultadoTiroAdversario.setDerrotou(adversario.isDerrotado());
		adversario.getProxyServidor().enviarResultadoTiro(resultadoTiroAdversario);
		

	}

	private Jogador getAdversario(Jogador jogador) {
		if(jogadores.size() <=1){
			return null;
		}
		int posicao = jogador.getPosicao();
		Jogador adversario;
		if(posicao == 0){
			adversario = jogadores.get(1);
		} else {
			adversario = jogadores.get(0);
		}
		return adversario;
	}

	private Integer verificarAcerto(float x, float y, float yT, Jogador adversario) {
		List<Nave> navesInimigas = adversario.getNaves();
		for (int i = 0; i < navesInimigas.size(); i++) {
			Nave nave = navesInimigas.get(i);
			if(!nave.isAtingido() && nave.isAcertou(x, y)){
				nave.setAtingido(true);
				adversario.incrementarNavesAbatidas();
				return i;
			}
		}
		return null;
	}

	public void abandonarJogo(Jogador jogador) {
		Jogador adversario = getAdversario(jogador);
		if(adversario != null){
			adversario.getProxyServidor().enviarJogoAbandonado();
		}
	}

}
