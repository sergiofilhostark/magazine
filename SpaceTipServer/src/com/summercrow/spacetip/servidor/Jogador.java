package com.summercrow.spacetip.servidor;

import java.util.ArrayList;
import java.util.List;

import com.summercrow.spacetip.servidor.proxy.ProxyServidor;

public class Jogador {
	
	private String nome;
	private Long id;
	private int posicao;
	private List<Nave> naves = new ArrayList<Nave>();
	private Partida partida;
	private int navesAbatidas;
	private ProxyServidor proxyServidor;
	
	public void setProxyServidor(ProxyServidor proxyServidor) {
		this.proxyServidor = proxyServidor;
	}
	
	public ProxyServidor getProxyServidor() {
		return proxyServidor;
	}
	
	public int getNavesAbatidas() {
		return navesAbatidas;
	}

	public void setNavesAbatidas(int navesAbatidas) {
		this.navesAbatidas = navesAbatidas;
	}
	
	public void incrementarNavesAbatidas() {
		this.navesAbatidas++;
	}

	public void setNaves(List<Nave> naves) {
		this.naves = naves;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	
	public List<Nave> getNaves() {
		return naves;
	}
	
	public void addNave(Nave nave){
		naves.add(nave);
	}
	
	public boolean isDerrotado(){
		return (navesAbatidas >= naves.size());
	}

	
	

}
