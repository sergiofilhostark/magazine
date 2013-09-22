package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.cliente.SpaceTipActivity;
import com.summercrow.spacetip.cliente.proxy.socket.ProxyClienteSocket;

public class ProxyClienteFactory {
	
	public static ProxyCliente newProxyCliente(SpaceTipActivity activity){
		
//		return new ProxyClienteLocal(activity);
		
		return new ProxyClienteSocket(activity);
		
//		return new ProxyClienteRest(activity);
		
	}

}
