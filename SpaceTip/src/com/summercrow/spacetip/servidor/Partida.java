package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.summercrow.spacetip.servidor.proxy.ProxyServidorLocal;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.Resposta;

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
	
	private ProxyServidorLocal proxyServidor;
	
	public void setProxyServidor(ProxyServidorLocal proxyServidor) {
		this.proxyServidor = proxyServidor;
	}

	public Partida(){
		jogadores = new ArrayList<Jogador>();
		jogadoresId = new HashMap<Long, Jogador>();
		turno = -1;
	}
	
	public synchronized void entrar(Jogador jogador){
		jogadores.add(jogador);
		jogador.setPosicao(jogadores.size() - 1);
		
		proxyServidor.enviarLoginEfetuado(jogador);
		
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
			proxyServidor.enviarPedidoPosicionamento(cadaJogador);
		}
	}

	private void iniciar() {
		turno = 0;
		for (Jogador cadaJogador: jogadores) {
			proxyServidor.enviarIniciar(cadaJogador, turno);
		}
	}
	
	public synchronized Resposta entrar(Long id, String nick){
		
		if(jogadores.size() >=2){
			return null;
		}
		
		Jogador jogador = new Jogador();
		jogador.setId(id);
		jogador.setNome(nick);
		
		jogadores.add(jogador);
		jogadoresId.put(id, jogador);
		
		Resposta resposta = new Resposta();
		resposta.setId(ENTROU);
		resposta.setStatus(OK);
		return resposta;
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

	private void enviarInicioDeJogo(Jogador jogador, boolean seuTurno, Jogador adversario) {
		InicioDeJogo inicioDeJogo = new InicioDeJogo();
		inicioDeJogo.setSeuTurno(seuTurno);
		List<DadosNave> dadosNavesAdversario = montarDadosNaveAdversario(adversario);
		inicioDeJogo.setNavesAdversario(dadosNavesAdversario);
		proxyServidor.enviarInicioDeJogo(jogador, inicioDeJogo);
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
	 */

}
