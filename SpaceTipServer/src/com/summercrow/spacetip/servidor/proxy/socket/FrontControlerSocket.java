package com.summercrow.spacetip.servidor.proxy.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.summercrow.spacetip.servidor.Controlador;

public class FrontControlerSocket{
	
	private ServerSocket serverSocket;
	private Controlador controlador;
	
	
	public FrontControlerSocket(){
		
		this.controlador = new Controlador();
		
		try {
			serverSocket = new ServerSocket(7777);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		while(true){
			try {
				Socket socket = serverSocket.accept();
				
				
				
				
				Conector conector = new Conector(socket);
				conector.setControlador(controlador);
				
				Thread thread = new Thread(conector);
				
				thread.start();
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}

	
	
	public static void main(String[] args) {
		System.out.println("Iniciando");
		FrontControlerSocket proxyServidorSocket = new FrontControlerSocket();
		
		System.out.println("Fim");
	}

}
