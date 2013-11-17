package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.cliente.PropertiesSpaceTip;
import com.summercrow.spacetip.cliente.SpaceTipActivity;
import com.summercrow.spacetip.cliente.proxy.rest.ProxyClienteRest;
import com.summercrow.spacetip.cliente.proxy.socket.ProxyClienteSocket;

public class ProxyClienteFactory {
	
	public static ProxyCliente newProxyCliente(SpaceTipActivity activity){
		
		String serverType = PropertiesSpaceTip.getInstance().getProperty("server.type");
		
		if(serverType.equals("socket")){
			return new ProxyClienteSocket(activity);
		}
		if(serverType.equals("rest")) {
			return new ProxyClienteRest(activity);
		}
		
		return null;
		
	}

}
