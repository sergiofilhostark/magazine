package com.summercrow.spacetip.cliente.proxy;

import java.util.List;

import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public interface ProxyCliente {

	public void enviarLogin(String nome);

	public void aguardar(Long id, int posicao);

	public void iniciar(Long id, int posicao, int turno);

	public void loginEfetuado(Long id, int posicao);

	public void pedirPosicionamento();

	public void enviarNavesPosicionadas(Long idJogador,
			List<DadosNave> dadosNaves);

	public void inicioDeJogo(InicioDeJogo inicioDeJogo);

	public void atirar(Long idJogador, Tiro tiro);

	public void resultadoTiro(ResultadoTiro resultadoTiro);

}