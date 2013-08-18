package com.summercrow.spacetip;

import com.summercrow.spacetip.servidor.ProxyServidorLocal;

public class ProxyClienteLocal {
	
	private Batalha batalha;
	private ProxyServidorLocal proxyServidor;
	
	public ProxyClienteLocal(Batalha batalha) {
		this.batalha = batalha;
		proxyServidor = new ProxyServidorLocal(this);
	}
	
	

}
