package com.summercrow.spacetip.servidor.proxy.local;


public class ProxyServidorLocal {
	
//	private ProxyCliente proxyCliente;
//	private Controlador controlador;
//	private Map<Long, Jogador> jogadores = new HashMap<Long, Jogador>();
//	
//	public ProxyServidorLocal(ProxyCliente proxyCliente) {
//		this.proxyCliente = proxyCliente;
//		this.controlador = new Controlador();
//	}
//	
//	private Jogador getJogador(Long id){
//		return jogadores.get(id);
//	}
//	
//	private Partida getPartida(Long id){
//		Jogador jogador = getJogador(id);
//		if(jogador == null){
//			return null;
//		}
//		return jogador.getPartida();
//	}
//
//	@Override
//	public void login(String nome) {
//		Jogador jogador = controlador.criarJogador(nome);
//		jogador.setProxyServidor(this);
//		controlador.entrarPartida(jogador);
//		jogadores.put(jogador.getId(), jogador);
//	}
//
//
//	@Override
//	public void enviarPedidoPosicionamento(Jogador jogador) {
//		//REMOVER
//		if(jogador.getId().longValue() > 1){
//			return;
//		}
//		proxyCliente.pedirPosicionamento();
//	}
//
//	@Override
//	public void enviarLoginEfetuado(Jogador jogador) {
//		//REMOVER
//		if(jogador.getId().longValue() > 1){
//			return;
//		}
//		proxyCliente.loginEfetuado(jogador.getId(), jogador.getPosicao());
//	}
//
//	@Override
//	public void navesPosicionadas(NavesPosicionadas navesPosicionadas) {
//		Jogador jogador = getJogador(navesPosicionadas.getIdJogador());
//		Partida partida = jogador.getPartida();
//		partida.navesPosicionadas(jogador, navesPosicionadas.getDadosNaves());
//	}
//
//	@Override
//	public void enviarInicioDeJogo(Jogador jogador, InicioDeJogo inicioDeJogo) {
//		//REMOVER
//		if(jogador.getId().longValue() > 1){
//			return;
//		}
//		proxyCliente.inicioDeJogo(inicioDeJogo);
//	}
//
//	@Override
//	public void atirar(Tiro tiro) {
//		Jogador jogador = getJogador(tiro.getIdJogador());
//		Partida partida = jogador.getPartida();
//		partida.atirar(jogador, tiro);
//	}
//
//	@Override
//	public void enviarResultadoTiro(Jogador jogador, ResultadoTiro resultadoTiro) {
//		//REMOVER
//		if(jogador.getId().longValue() > 1){
//			return;
//		}
//		proxyCliente.resultadoTiro(resultadoTiro);
//	}

}
