package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.cliente.Batalha;
import com.summercrow.spacetip.cliente.MainActivity;
import com.summercrow.spacetip.servidor.proxy.ProxyServidorLocal;

public class ProxyClienteLocal {
	
	private MainActivity activity;
	private ProxyServidorLocal proxyServidor;
	
	public ProxyClienteLocal(MainActivity activity) {
		this.activity = activity;
		proxyServidor = new ProxyServidorLocal(this);
	}

	public void enviarLogin(String nome) {
		proxyServidor.login(nome);
		
	}

	public void aguardar(Long id, int posicao) {
		// TODO Auto-generated method stub
		
	}

	public void iniciar(Long id, int posicao, int turno) {
		// TODO Auto-generated method stub
		
	}

	
	
	

}
