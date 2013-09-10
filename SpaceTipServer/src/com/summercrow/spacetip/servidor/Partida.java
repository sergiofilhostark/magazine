package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.summercrow.spacetip.servidor.proxy.ProxyServidor;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class Partida {
	
	private List<Jogador> jogadores;
	private int turno;
	//TODO ver a necessidade desse mapa
	private Map<Long, Jogador> jogadoresId;
	
	//TODO colocar numa enum??
	private final int POSICIONANDO = 1;
	private final int AGUARDANDO_INICIO = 2;
	private final int JOGO_INICIOU = 3;
	private final int SUA_VEZ = 4;
	private final int JOGO_ACABOU = 4;
	
	private final int ENTROU = 1;
	private final int POSICIONOU = 2;
	private final int POSICIONOU_TODOS = 3;
	
	private final int OK = 1;
	private final int NOK = 2;
	
	private final int NUMERO_NAVES = 4;
	


	public Partida(){
		jogadores = new ArrayList<Jogador>();
		jogadoresId = new HashMap<Long, Jogador>();
		turno = -1;
	}
	
	public synchronized void entrar(Jogador jogador){
		jogadores.add(jogador);
		jogador.setPosicao(jogadores.size() - 1);
		
		jogador.getProxyServidor().enviarLoginEfetuado(jogador);
		
		//REMOVER
//		if(jogador.getId().longValue() > 1){
//			return;
//		}
		
		
		if(jogadores.size() > 1){
			pedirPosicionamento();
		}
		
//		if(jogadores.size() == 1){
//			proxyServidor.enviarAguardar(jogador);
//		} else {
//			pedirPosicionamento();
//		}
	}
	
	private void pedirPosicionamento() {
		for (Jogador cadaJogador: jogadores) {
			cadaJogador.getProxyServidor().enviarPedidoPosicionamento(cadaJogador);
		}
	}

	
//	public Resposta posicionarNave(Long id, long x, long y, long altura, long largura){
//		
//		Jogador jogador = jogadoresId.get(id);
//		
//		Nave nave = new Nave(x, y, largura, altura);
//		jogador.addNave(nave);
//		
//		int idResposta = POSICIONOU;
//		if (idResposta == NUMERO_NAVES){
//			idResposta = POSICIONOU_TODOS;
//		}
//		
//		Resposta resposta = new Resposta();
//		resposta.setId(idResposta);
//		resposta.setStatus(OK);
//		return resposta;
//	}

	public void navesPosicionadas(Jogador jogador, List<DadosNave> dadosNaves) {
		for (DadosNave dadosNave : dadosNaves) {
			Nave nave = new Nave(dadosNave.getX(), dadosNave.getY(), dadosNave.getLargura(), dadosNave.getAltura());
			jogador.addNave(nave);
		}
		
		boolean todasPosicionadas = true;
		for (Jogador cadaJogador: jogadores) {
			if(cadaJogador.getNaves().isEmpty()){
				todasPosicionadas = false;
				break;
			}
		}
		if(todasPosicionadas){
			
			Jogador jogador1 = jogadores.get(0);
			Jogador jogador2 = jogadores.get(1);
			
			enviarInicioDeJogo(jogador1, true, jogador2);
			enviarInicioDeJogo(jogador2, false, jogador1);
			
			turno = 0;
		}
	}

	private void enviarInicioDeJogo(Jogador jogador, boolean meuTurno, Jogador adversario) {
		InicioDeJogo inicioDeJogo = new InicioDeJogo();
		inicioDeJogo.setMeuTurno(meuTurno);
		List<DadosNave> dadosNavesAdversario = montarDadosNaveAdversario(adversario);
		inicioDeJogo.setNavesAdversario(dadosNavesAdversario);
		jogador.getProxyServidor().enviarInicioDeJogo(jogador, inicioDeJogo);
	}

	private List<DadosNave> montarDadosNaveAdversario(Jogador jogador) {
		List<DadosNave> dadosNavesAdversario = new ArrayList<DadosNave>();
		List<Nave> naves = jogador.getNaves();
		for (Nave nave : naves) {
			DadosNave dadosNave = new DadosNave();
			dadosNave.setAltura(nave.getAltura());
			dadosNave.setLargura(nave.getLargura());
			dadosNave.setX(nave.getX());
			dadosNave.setY(nave.getY());
			dadosNavesAdversario.add(dadosNave);
		}
		return dadosNavesAdversario;
	}

	public void atirar(Jogador jogador, Tiro tiro) {
		int posicao = jogador.getPosicao();
		Jogador adversario;
		if(posicao == 0){
			adversario = jogadores.get(1);
		} else {
			adversario = jogadores.get(0);
		}
		Integer naveAtingida = verificarAcerto(tiro.getX(), tiro.getY(), tiro.getDistancia(), adversario);
		
		ResultadoTiro resultadoTiro = new ResultadoTiro();
		resultadoTiro.setTiro(tiro);
		resultadoTiro.setNaveAtingida(naveAtingida);
		resultadoTiro.setDerrotou(adversario.isDerrotado());
		
		resultadoTiro.setMeuTiro(true);
		jogador.getProxyServidor().enviarResultadoTiro(jogador, resultadoTiro);
		
		resultadoTiro.setMeuTiro(false);
		adversario.getProxyServidor().enviarResultadoTiro(adversario, resultadoTiro);
	}
	
	private Integer verificarAcerto(float x, float y, float yT, Jogador adversario) {
		List<Nave> navesInimigas = adversario.getNaves();
		for (int i = 0; i < navesInimigas.size(); i++) {
			Nave nave = navesInimigas.get(i);
			if(!nave.isAtingido() && nave.isAcertou(x, y)){
				nave.setAtingido(true);
				adversario.incrementarNavesAbatidas();
				return i;
			}
		}
		return null;
	}
	
	
	/**
	 * Problemas:
	 * pool eh ineficiente (e sincrono)
	 * sincronia impossibilita de mandar mensagens aleatorias para todos os usuarios
	 * seguranca dos dados (id do usuario pode ser alterado)
	 * sincronizacao dos metodos!!
	 * travar o cliente para multiplos toques. esperar o resultado antes de aceitar outro toque
	 * 
	 * a resposta pode ser generica, entao nao compensa criar um protocolo assincrono
	 * 
	 * turno em variaves locais, ruim mas necessario
	 * MVC (todos os dados no cliente e pacote sendo enviado sempre, embora seja dificil de fazer aqui)
	 * 
	 * conversao das dimensoes para valores relativos (ver se isso eh mesmo necessario pois as vezes as dimensoes 
	 * ja sao relativas (ou tem algo que faz isso)
	 * 
	 * verificacoes no lado cliente para saber se o turno é dele mesmo (nao estao feitas ainda)
	 * 
	 * protocolo de mensagens para o cliente e trantamento de excessao
	 * 
	 * Projetos referenciar projetos no Android
	 * 
	 * metodos do proxy nao precisam mais incluir o jogador pois o proxy agora é do jogador
	 * 
	 */

}
