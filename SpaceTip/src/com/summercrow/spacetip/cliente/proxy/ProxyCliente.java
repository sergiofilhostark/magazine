package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.Tiro;

public interface ProxyCliente {

	public void enviarLogin(String nome);
	
	public void enviarNavesPosicionadas(NavesPosicionadas navesPosicionadas);
	
	public void enviarAtirar(Tiro tiro, Long idJogador);

	public void enviarFimDeJogo(Long idJogador);
	
	public void enviarAbandonarJogo(Long idJogador);
	

	public void desconectar();

}