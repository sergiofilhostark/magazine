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

public class ProxyServidorSocket{
	
	private ServerSocket serverSocket;
	private Controlador controlador;
	
	
	public ProxyServidorSocket(){
		
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
		ProxyServidorSocket proxyServidorSocket = new ProxyServidorSocket();
		
		System.out.println("Fim");
	}

}
