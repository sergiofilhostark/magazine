package com.summercrow.spacetip.servidor;

import com.summercrow.spacetip.cliente.ProxyClienteLocal;

public class ProxyServidorLocal {
	
	private ProxyClienteLocal proxyCliente;
	
	public ProxyServidorLocal(ProxyClienteLocal proxyCliente) {
		this.proxyCliente = proxyCliente;
	}

}
