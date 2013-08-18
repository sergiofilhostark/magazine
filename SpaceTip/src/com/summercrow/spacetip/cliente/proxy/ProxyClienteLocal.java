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

	public void login(String nome) {
		proxyServidor.login(nome);
		
	}
	
	

}
