package com.summercrow.spacetip.servidor.proxy;

import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public interface ProxyServidor {

	public void login(String nome);

	public void enviarPedidoPosicionamento(Jogador jogador);

	public void enviarLoginEfetuado(Jogador jogador);

	public void navesPosicionadas(NavesPosicionadas navesPosicionadas);

	public void enviarInicioDeJogo(Jogador jogador, InicioDeJogo inicioDeJogo);

	public void atirar(Tiro tiro);

	public void enviarResultadoTiro(Jogador jogador, ResultadoTiro resultadoTiro);

}