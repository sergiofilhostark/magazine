package com.summercrow.spacetip.servidor;

import com.summercrow.spacetip.cliente.proxy.ProxyClienteLocal;
import com.summercrow.spacetip.servidor.proxy.ProxyServidorLocal;

public class Controlador {
	
	private ProxyServidorLocal proxyServidor;
	
	public Controlador(ProxyServidorLocal proxyServidor) {
		this.proxyServidor = proxyServidor;
	}

}
