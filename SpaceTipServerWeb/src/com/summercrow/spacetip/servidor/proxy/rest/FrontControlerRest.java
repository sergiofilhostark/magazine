package com.summercrow.spacetip.servidor.proxy.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.to.Atirar;
import com.summercrow.spacetip.to.Login;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

@Path("/spacetip")
public class FrontControlerRest {
	
	private Controlador controlador;
	private Map<Long, ProxyServidorRest> proxys = new HashMap<Long, ProxyServidorRest>();
	
	public FrontControlerRest(){
		this.controlador = new Controlador();
	}
	
	private synchronized void putProxy(Jogador jogador,
			ProxyServidorRest proxyServidorRest) {
		proxys.put(jogador.getId(), proxyServidorRest);
	}
	
	private synchronized ProxyServidorRest getProxy(Long idJogador) {
		ProxyServidorRest proxyServidor = proxys.get(idJogador);
		return proxyServidor;
	}
	
	@POST
	@Path("/login")
	@Consumes("application/json")
	public Response login(Login login){
		
		String nome = login.getNome();
		
		Jogador jogador = controlador.criarJogador(nome);
		ProxyServidorRest proxyServidorRest = new ProxyServidorRest();
		proxyServidorRest.setJogador(jogador);
		jogador.setProxyServidor(proxyServidorRest);
		
		putProxy(jogador, proxyServidorRest);
		
		controlador.entrarPartida(jogador);
		
		return Response
				.status(Response.Status.OK)
				.entity(jogador.getId().toString())
				.build();
	}
	
	
	@POST
	@Path("/naves_posicionadas")
	@Consumes("application/json")
	public Response navesPosicionadas(NavesPosicionadas navesPosicionadas){
		
		Long idJogador = navesPosicionadas.getIdJogador();
		
		ProxyServidorRest proxyServidor = getProxy(idJogador);
		
		if(proxyServidor != null){
			proxyServidor.navesPosicionadas(navesPosicionadas);
			
			return Response.status(Response.Status.OK).build();
		}
		
		return Response.status(Response.Status.FORBIDDEN).build();
	}
	
	@POST
	@Path("/atirar")
	@Consumes("application/json")
	public Response atirar(Atirar atirar){
		
		Long idJogador = atirar.getIdJogador();
		
		ProxyServidorRest proxyServidor = getProxy(idJogador);
		
		if(proxyServidor != null){
			proxyServidor.atirar(atirar.getTiro());
			
			return Response.status(Response.Status.OK).build();
		}
		
		return Response.status(Response.Status.FORBIDDEN).build();
	}
	
	@POST
	@Path("/verificar_req_servidor")
	@Produces("application/json")
	public ReqServidor verificarReqServidor(@FormParam("idJogador") Long idJogador) {
		ProxyServidorRest proxyServidor = getProxy(idJogador);
		
		if(proxyServidor != null){
			ReqServidor reqServidor = proxyServidor.getRequisicao();
			return reqServidor;
		}
		
		return null;
	}
	
	

}
