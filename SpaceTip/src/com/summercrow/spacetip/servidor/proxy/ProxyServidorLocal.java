package com.summercrow.spacetip.servidor.proxy;

import com.summercrow.spacetip.cliente.proxy.ProxyClienteLocal;

public class ProxyServidorLocal {
	
	private ProxyClienteLocal proxyCliente;
	
	public ProxyServidorLocal(ProxyClienteLocal proxyCliente) {
		this.proxyCliente = proxyCliente;
	}

}
