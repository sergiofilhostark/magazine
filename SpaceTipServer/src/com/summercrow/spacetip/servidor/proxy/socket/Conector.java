package com.summercrow.spacetip.servidor.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.servidor.proxy.ProxyServidor;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.PedirPosicionamento;
import com.summercrow.spacetip.to.Resposta;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class Conector implements Runnable, ProxyServidor{
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Jogador jogador;
	private Controlador controlador;
	
	public Conector(Socket socket){
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Controlador getControlador() {
		return controlador;
	}
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	
	public Jogador getJogador() {
		return jogador;
	}
	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	@Override
	public void run() {
		
		try {
		
			Object object = in.readObject();
			String nome = (String) object;
			
			System.out.println("recebi "+nome);
			
			Jogador jogador = controlador.criarJogador(nome);			
			setJogador(jogador);
			jogador.setProxyServidor(this);
			controlador.entrarPartida(jogador);
		
		
			object = in.readObject();
			while(object != null){
				System.out.println("Recebi algo");
				object = in.readObject();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		
	}
	private void close(){
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void login(String nome) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enviarAguardar(Jogador jogador) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enviarIniciar(Jogador jogador, int turno) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enviarPedidoPosicionamento(Jogador jogador) {
		PedirPosicionamento pedirPosicionamento = new PedirPosicionamento();
		enviarResposta(pedirPosicionamento);
	}
	@Override
	public void enviarLoginEfetuado(Jogador jogador) {
		LoginEfetuado loginEfetuado = new LoginEfetuado();
		loginEfetuado.setId(jogador.getId());
		loginEfetuado.setPosicao(jogador.getPosicao());
		enviarResposta(loginEfetuado);
	}

	private void enviarResposta(Resposta resposta) {
		try {
			out.writeObject(resposta);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void navesPosicionadas(NavesPosicionadas navesPosicionadas) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enviarInicioDeJogo(Jogador jogador, InicioDeJogo inicioDeJogo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void atirar(Tiro tiro) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void enviarResultadoTiro(Jogador jogador, ResultadoTiro resultadoTiro) {
		// TODO Auto-generated method stub
		
	}
	

}
