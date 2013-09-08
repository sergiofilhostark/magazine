package com.summercrow.spacetip.servidor.proxy;

import java.util.List;

import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public interface ProxyServidor {

	public void login(String nome);

	public void enviarAguardar(Jogador jogador);

	public void enviarIniciar(Jogador jogador, int turno);

	public void enviarPedidoPosicionamento(Jogador jogador);

	public void enviarLoginEfetuado(Jogador jogador);

	public void navesPosicionadas(Long idJogador, List<DadosNave> dadosNaves);

	public void enviarInicioDeJogo(Jogador jogador, InicioDeJogo inicioDeJogo);

	public void atirar(Long idJogador, Tiro tiro);

	public void enviarResultadoTiro(Jogador jogador, ResultadoTiro resultadoTiro);

}