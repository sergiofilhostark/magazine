package com.summercrow.spacetip.servidor.proxy;

import com.summercrow.spacetip.cliente.proxy.ProxyClienteLocal;
import com.summercrow.spacetip.servidor.Controlador;

public class ProxyServidorLocal {
	
	private ProxyClienteLocal proxyCliente;
	private Controlador controlador;
	
	public ProxyServidorLocal(ProxyClienteLocal proxyCliente) {
		this.proxyCliente = proxyCliente;
		this.controlador = new Controlador(this);
	}

}
