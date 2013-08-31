package com.summercrow.spacetip.cliente.proxy;

import java.util.List;

import com.summercrow.spacetip.cliente.Batalha;
import com.summercrow.spacetip.cliente.MainActivity;
import com.summercrow.spacetip.servidor.proxy.ProxyServidorLocal;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class ProxyClienteLocal {
	
	private MainActivity activity;
	private ProxyServidorLocal proxyServidor;
	
	public ProxyClienteLocal(MainActivity activity) {
		this.activity = activity;
		proxyServidor = new ProxyServidorLocal(this);
	}

	public void enviarLogin(String nome) {
		proxyServidor.login(nome);
		
	}

	public void aguardar(Long id, int posicao) {
		// TODO Auto-generated method stub
		
	}

	public void iniciar(Long id, int posicao, int turno) {
		// TODO Auto-generated method stub
		
	}

	public void loginEfetuado(Long id, int posicao) {
		activity.loginEfetuado(id, posicao);
	}

	public void pedirPosicionamento() {
		activity.pedirPosicionamento();
	}

	public void enviarNavesPosicionadas(Long idJogador, List<DadosNave> dadosNaves) {
		proxyServidor.navesPosicionadas(idJogador, dadosNaves);
	}

	public void inicioDeJogo(InicioDeJogo inicioDeJogo) {
		activity.inicioDeJogo(inicioDeJogo);
	}

	public void atirar(Long idJogador, Tiro tiro) {
		proxyServidor.atirar(idJogador, tiro);
	}

	public void resultadoTiro(ResultadoTiro resultadoTiro) {
		activity.resultadoTiro(resultadoTiro);
	}

	
	
	

}
