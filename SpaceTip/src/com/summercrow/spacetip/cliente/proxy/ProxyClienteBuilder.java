package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.cliente.SpaceTipActivity;
import com.summercrow.spacetip.cliente.proxy.rest.ProxyClienteRest;

public class ProxyClienteBuilder {
	
	public static ProxyCliente buildProxyCliente(SpaceTipActivity activity){
		
//		return new ProxyClienteLocal(activity);
		
//		return new ProxyClienteSocket(activity);
		
		return new ProxyClienteRest(activity);
		
	}

}
