package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.cliente.MainActivity;
import com.summercrow.spacetip.cliente.proxy.local.ProxyClienteLocal;
import com.summercrow.spacetip.cliente.proxy.socket.ProxyClienteSocket;

public class ProxyClienteBuilder {
	
	public static ProxyCliente buildProxyCliente(MainActivity activity){
		
//		return new ProxyClienteLocal(activity);
		
		return new ProxyClienteSocket(activity);
		
	}

}
