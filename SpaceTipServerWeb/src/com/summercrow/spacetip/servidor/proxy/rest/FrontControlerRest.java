package com.summercrow.spacetip.servidor.proxy.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

@Path("/spacetip")
public class FrontControlerRest {
	
	@GET
	@Path("/teste")
	@Produces("application/json")
	public ReqServidor getBooks() {
		
		Tiro tiro = new Tiro();
		tiro.setDistancia(2.3F);
		tiro.setX(4);
		tiro.setY(0.0045F);
		
		ResultadoTiro rt = new ResultadoTiro();
		rt.setTiro(tiro);
		rt.setDerrotou(true);
		rt.setMeuTiro(false);
		rt.setNaveAtingida(5);
		
		
		return rt;
	}

}
