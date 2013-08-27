package com.summercrow.spacetip.servidor.proxy;

import com.summercrow.spacetip.cliente.proxy.ProxyClienteLocal;
import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;

public class ProxyServidorLocal {
	
	private ProxyClienteLocal proxyCliente;
	private Controlador controlador;
	
	public ProxyServidorLocal(ProxyClienteLocal proxyCliente) {
		this.proxyCliente = proxyCliente;
		this.controlador = new Controlador(this);
	}

	public void login(String nome) {
		controlador.login(nome);
	}

	public void enviarAguardar(Jogador jogador) {
		proxyCliente.aguardar(jogador.getId(), jogador.getPosicao());
	}

	public void enviarIniciar(Jogador jogador, int turno) {
		proxyCliente.iniciar(jogador.getId(), jogador.getPosicao(), turno);
	}

	public void enviarPedidoPosicionamento(Jogador jogador) {
		if(jogador.getId().longValue() > 1){
			return;
		}
		proxyCliente.pedirPosicionamento();
	}

	public void enviarLoginEfetuado(Jogador jogador) {
		if(jogador.getId().longValue() > 1){
			return;
		}
		proxyCliente.loginEfetuado(jogador.getId(), jogador.getPosicao());
	}

}
