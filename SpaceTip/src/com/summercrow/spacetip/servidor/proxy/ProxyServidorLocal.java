package com.summercrow.spacetip.servidor.proxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.summercrow.spacetip.cliente.proxy.ProxyClienteLocal;
import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.servidor.Partida;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;

public class ProxyServidorLocal {
	
	private ProxyClienteLocal proxyCliente;
	private Controlador controlador;
	private Map<Long, Jogador> jogadores = new HashMap<Long, Jogador>();
	
	public ProxyServidorLocal(ProxyClienteLocal proxyCliente) {
		this.proxyCliente = proxyCliente;
		this.controlador = new Controlador(this);
	}
	
	private Jogador getJogador(Long id){
		return jogadores.get(id);
	}
	
	private Partida getPartida(Long id){
		Jogador jogador = getJogador(id);
		if(jogador == null){
			return null;
		}
		return jogador.getPartida();
	}

	public void login(String nome) {
		Jogador jogador = controlador.login(nome);
		jogadores.put(jogador.getId(), jogador);
	}

	public void enviarAguardar(Jogador jogador) {
		proxyCliente.aguardar(jogador.getId(), jogador.getPosicao());
	}

	public void enviarIniciar(Jogador jogador, int turno) {
		proxyCliente.iniciar(jogador.getId(), jogador.getPosicao(), turno);
	}

	public void enviarPedidoPosicionamento(Jogador jogador) {
		//REMOVER
		if(jogador.getId().longValue() > 1){
			return;
		}
		proxyCliente.pedirPosicionamento();
	}

	public void enviarLoginEfetuado(Jogador jogador) {
		//REMOVER
		if(jogador.getId().longValue() > 1){
			return;
		}
		proxyCliente.loginEfetuado(jogador.getId(), jogador.getPosicao());
	}

	public void navesPosicionadas(Long idJogador, List<DadosNave> dadosNaves) {
		Jogador jogador = getJogador(idJogador);
		Partida partida = jogador.getPartida();
		partida.navesPosicionadas(jogador, dadosNaves);
	}

	public void enviarInicioDeJogo(Jogador jogador, InicioDeJogo inicioDeJogo) {
		//REMOVER
		if(jogador.getId().longValue() > 1){
			return;
		}
		proxyCliente.inicioDeJogo(inicioDeJogo);
	}

}
