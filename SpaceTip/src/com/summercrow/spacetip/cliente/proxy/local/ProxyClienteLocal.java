package com.summercrow.spacetip.cliente.proxy.local;

import com.summercrow.spacetip.cliente.SpaceTipActivity;
import com.summercrow.spacetip.cliente.proxy.ProxyCliente;
import com.summercrow.spacetip.servidor.proxy.local.ProxyServidorLocal;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class ProxyClienteLocal implements ProxyCliente {
	
	private SpaceTipActivity activity;
//	private ProxyServidor proxyServidor;
	
	public ProxyClienteLocal(SpaceTipActivity activity) {
		this.activity = activity;
//		proxyServidor = new ProxyServidorLocal(this);
	}

	@Override
	public void enviarLogin(String nome) {
//		proxyServidor.login(nome);
		
	}
	
	@Override
	public void enviarNavesPosicionadas(NavesPosicionadas navesPosicionadas) {
//		proxyServidor.navesPosicionadas(navesPosicionadas);
	}
	
	@Override
	public void enviarAtirar(Tiro tiro) {
//		proxyServidor.atirar(tiro);
	}
	

	@Override
	public void loginEfetuado(Long id, int posicao) {
		activity.loginEfetuado(id, posicao);
	}

	@Override
	public void pedirPosicionamento() {
		activity.pedirPosicionamento();
	}

	

	@Override
	public void inicioDeJogo(InicioDeJogo inicioDeJogo) {
		activity.inicioDeJogo(inicioDeJogo);
	}

	

	@Override
	public void resultadoTiro(ResultadoTiro resultadoTiro) {
		activity.resultadoTiro(resultadoTiro);
	}

	
	
	

}
