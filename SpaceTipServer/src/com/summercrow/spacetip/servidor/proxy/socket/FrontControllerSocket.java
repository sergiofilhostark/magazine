package com.summercrow.spacetip.servidor.proxy.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.summercrow.spacetip.servidor.Controlador;

public class FrontControllerSocket{
	
	private ServerSocket serverSocket;
	private Controlador controlador;
	
	
	public FrontControllerSocket(){
		
		this.controlador = new Controlador();
		
		try {
			serverSocket = new ServerSocket(7777);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		while(true){
			try {
				Socket socket = acceptSocket();
				
				ProxyServidorSocket proxy = new ProxyServidorSocket(socket);
				proxy.setControlador(controlador);
				
				Thread thread = new Thread(proxy);
				
				thread.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}



	private Socket acceptSocket(){
		Socket socket;
		try {
			socket = serverSocket.accept();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return socket;
	}

	
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		System.out.println("Iniciando");
		FrontControllerSocket proxyServidorSocket = new FrontControllerSocket();
		
		System.out.println("Fim");
	}

}
