package com.summercrow.spacetip.servidor.proxy.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.to.Atirar;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

@Path("/spacetip")
public class FrontControlerRest {
	
	private Controlador controlador;
	
	public FrontControlerRest(){
		this.controlador = new Controlador();
	}
	
	@POST
	@Path("/atirar")
	@Consumes("application/json")
	@Produces("application/json")
	public Atirar atirar(Atirar atirar){
		
		System.out.println("atirei "+ atirar.getTipo());
		
		
		return atirar;
	}
	
	@GET
	@Path("/teste")
	@Produces("application/json")
	public ReqServidor getBooks() {
		
		// http://localhost:8080/SpaceTipServerWeb/services/spacetip/teste
		
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
