package com.summercrow.spacetip.cliente.proxy;

import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public interface ProxyCliente {

	public void enviarLogin(String nome);
	
	public void enviarNavesPosicionadas(NavesPosicionadas navesPosicionadas);
	
	public void enviarAtirar(Tiro tiro, Long idJogador);
	

	public void loginEfetuado(Long id, int posicao);

	public void pedirPosicionamento();	

	public void inicioDeJogo(InicioDeJogo inicioDeJogo);	

	public void resultadoTiro(ResultadoTiro resultadoTiro);

}