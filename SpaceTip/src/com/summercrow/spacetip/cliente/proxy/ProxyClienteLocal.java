package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.cliente.Batalha;
import com.summercrow.spacetip.servidor.proxy.ProxyServidorLocal;

public class ProxyClienteLocal {
	
	private Batalha batalha;
	private ProxyServidorLocal proxyServidor;
	
	public ProxyClienteLocal(Batalha batalha) {
		this.batalha = batalha;
		proxyServidor = new ProxyServidorLocal(this);
	}
	
	

}
