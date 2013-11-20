package com.summercrow.spacetip.servidor.proxy.rest;

import java.util.LinkedList;
import java.util.List;

import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.servidor.Partida;
import com.summercrow.spacetip.servidor.proxy.ProxyServidor;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.PedirPosicionamento;
import com.summercrow.spacetip.to.ReqCliente;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class ProxyServidorRest implements ProxyServidor{
	
	private Jogador jogador;
	
	private List<ReqServidor> requisicoes = new LinkedList<ReqServidor>();
	

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	private synchronized void addRequisicao(ReqServidor requisicao) {
		requisicoes.add(requisicao);
	}
	
	public synchronized ReqServidor getRequisicao() {
		if(requisicoes.size() == 0){
			return null;
		}
		return requisicoes.remove(0);
	}

	// A maioria dos metodos nao precisa receber jogador, acho que ne precisa guardar como atributos
	
	@Override
	public void enviarPedidoPosicionamento() {
		PedirPosicionamento pedirPosicionamento = new PedirPosicionamento();
		addRequisicao(pedirPosicionamento);
	}

	@Override
	public void enviarLoginEfetuado() {
		LoginEfetuado loginEfetuado = new LoginEfetuado();
		loginEfetuado.setId(jogador.getId());
		loginEfetuado.setPosicao(jogador.getPosicao());
		addRequisicao(loginEfetuado);
	}

	@Override
	public void enviarInicioDeJogo(InicioDeJogo inicioDeJogo) {
		addRequisicao(inicioDeJogo);
	}
	
	@Override
	public void enviarResultadoTiro(ResultadoTiro resultadoTiro) {
		addRequisicao(resultadoTiro);
	}
	
	
	
	
	@Override
	public void login(String nome) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void navesPosicionadas(NavesPosicionadas navesPosicionadas) {
		Partida partida = jogador.getPartida();
		partida.navesPosicionadas(jogador, navesPosicionadas.getDadosNaves());
	}

	@Override
	public void atirar(Tiro tiro) {
		Partida partida = jogador.getPartida();
		partida.atirar(jogador, tiro);
	}

	@Override
	public void abandonarJogo() {
		// TODO Auto-generated method stub
		
	}

}
