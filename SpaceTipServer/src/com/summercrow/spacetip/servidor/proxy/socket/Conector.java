package com.summercrow.spacetip.servidor.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.servidor.proxy.ProxyServidor;

public class Conector implements Runnable{
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Jogador jogador;
	private ProxyServidor proxyServidor;
	private Controlador controlador;
	
	public Controlador getControlador() {
		return controlador;
	}
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public ObjectInputStream getIn() {
		return in;
	}
	public void setIn(ObjectInputStream in) {
		this.in = in;
	}
	public ObjectOutputStream getOut() {
		return out;
	}
	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}
	public Jogador getJogador() {
		return jogador;
	}
	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}
	public ProxyServidor getProxyServidor() {
		return proxyServidor;
	}
	public void setProxyServidor(ProxyServidor proxyServidor) {
		this.proxyServidor = proxyServidor;
	}
	@Override
	public void run() {
		
		try {
		
			Object object = in.readObject();
			String nome = (String) object;
			
			System.out.println("recebi "+nome);
			
			Jogador jogador = controlador.criarJogador(nome);
			
			setJogador(jogador);
		
		
			while((object = in.readObject()) != null){
				System.out.println("Recebi algo");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
	

}
