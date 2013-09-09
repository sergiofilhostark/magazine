package com.summercrow.spacetip.servidor.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.servidor.proxy.ProxyServidor;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class ProxyServidorSocket implements ProxyServidor{
	
	private ServerSocket serverSocket;
	private Controlador controlador;
	private Map<Long , Conector> conectores = new HashMap<Long, Conector>();
	
	
	public ProxyServidorSocket(){
		
		this.controlador = new Controlador(this);
		
		try {
			serverSocket = new ServerSocket(5555);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		while(true){
			try {
				Socket socket = serverSocket.accept();
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				
				
				
				Conector conector = new Conector();
				conector.setIn(in);
				conector.setOut(out);
				conector.setSocket(socket);
//				conector.setJogador(jogador);
				conector.setProxyServidor(this);
				conector.setControlador(controlador);
				
//				conectores.put(jogador.getId(), conector);
				Thread thread = new Thread(conector);
				
				
//				controlador.entrarPartida(jogador);
				
				thread.start();
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	public void putConector(Long id, Conector conector){
		conectores.put(id, conector);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enviarLoginEfetuado(Jogador jogador) {
		Conector conector = conectores.get(jogador.getId());
		ObjectOutputStream out = conector.getOut();
		LoginEfetuado loginEfetuado = new LoginEfetuado();
		loginEfetuado.setId(jogador.getId());
		loginEfetuado.setPosicao(jogador.getPosicao());
		try {
			out.writeObject(loginEfetuado);
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
	
	public static void main(String[] args) {
		System.out.println("Iniciando");
		ProxyServidorSocket proxyServidorSocket = new ProxyServidorSocket();
		
		System.out.println("Fim");
	}

}
