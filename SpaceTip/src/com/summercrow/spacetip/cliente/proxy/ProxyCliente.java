package com.summercrow.spacetip.cliente.proxy;

import java.io.IOException;

import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.Tiro;

public interface ProxyCliente {

	public void enviarLogin(String nome);
	
	public void enviarNavesPosicionadas(NavesPosicionadas navesPosicionadas);
	
	public void enviarAtirar(Tiro tiro, Long idJogador);

	public void enviarFimDeJogo(Long idJogador);
	
	public void enviarAbandonoDeJogo(Long idJogador) throws IOException;
	

	public void desconectar();

}