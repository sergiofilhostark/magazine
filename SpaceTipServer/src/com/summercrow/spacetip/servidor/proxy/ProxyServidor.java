package com.summercrow.spacetip.servidor.proxy;

import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public interface ProxyServidor {


	public void enviarPedidoPosicionamento(Jogador jogador);

	public void enviarLoginEfetuado(Jogador jogador);

	public void enviarInicioDeJogo(Jogador jogador, InicioDeJogo inicioDeJogo);

	public void enviarResultadoTiro(Jogador jogador, ResultadoTiro resultadoTiro);
	
	
	
	public void login(String nome);
	
	public void navesPosicionadas(NavesPosicionadas navesPosicionadas);
	
	public void atirar(Tiro tiro);

}