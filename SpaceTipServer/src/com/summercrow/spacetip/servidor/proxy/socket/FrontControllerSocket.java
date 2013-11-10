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
				Socket socket = serverSocket.accept();
				
				ProxyServidorSocket proxy = new ProxyServidorSocket(socket);
				proxy.setControlador(controlador);
				
				Thread thread = new Thread(proxy);
				
				thread.start();
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}

	
	
	public static void main(String[] args) {
		System.out.println("Iniciando");
		FrontControllerSocket proxyServidorSocket = new FrontControllerSocket();
		
		System.out.println("Fim");
	}

}