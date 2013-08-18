package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.summercrow.spacetip.to.Resposta;

public class Partida {
	
	private List<Jogador> jogadores;
	private int turno;
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
	
	public void iniciar(){
		jogadores = new ArrayList<Jogador>();
		jogadoresId = new HashMap<Long, Jogador>();
		turno = -1;
	}
	
	public synchronized Resposta entrar(Long id, String nick){
		
		if(jogadores.size() >=2){
			return null;
		}
		
		Jogador jogador = new Jogador();
		jogador.setId(id);
		jogador.setNick(nick);
		
		jogadores.add(jogador);
		jogadoresId.put(id, jogador);
		
		Resposta resposta = new Resposta();
		resposta.setId(ENTROU);
		resposta.setStatus(OK);
		return resposta;
	}
	
	public Resposta posicionarNave(Long id, long x, long y, long altura, long largura){
		
		Jogador jogador = jogadoresId.get(id);
		
		Nave nave = new Nave(x, y, largura, altura);
		jogador.addNave(nave);
		
		int idResposta = POSICIONOU;
		if (idResposta == NUMERO_NAVES){
			idResposta = POSICIONOU_TODOS;
		}
		
		Resposta resposta = new Resposta();
		resposta.setId(idResposta);
		resposta.setStatus(OK);
		return resposta;
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
	 */

}
