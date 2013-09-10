package com.summercrow.spacetip.cliente.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.summercrow.spacetip.cliente.MainActivity;
import com.summercrow.spacetip.cliente.proxy.ProxyCliente;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.Resposta;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class ProxyClienteSocket implements ProxyCliente, Runnable{
	
	private MainActivity activity;
	private Socket socket;
	
	private ObjectInputStream in;
	
	private ObjectOutputStream out;
	
	
	public ProxyClienteSocket(MainActivity activity){
		this.activity = activity;
		
		Thread thread = new Thread(this);
		thread.start();
		
		
	}

	@Override
	public void enviarLogin(String nome) {
		try {
			out.writeObject(nome);
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void enviarNavesPosicionadas(NavesPosicionadas navesPosicionadas) {
		try {
			out.writeObject(navesPosicionadas);
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void enviarAtirar(Tiro tiro) {
		try {
			out.writeObject(tiro);
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void aguardar(Long id, int posicao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void iniciar(Long id, int posicao, int turno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginEfetuado(final Long id, final int posicao) {
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				activity.loginEfetuado(id, posicao);
				
			}
		});
		
	}

	@Override
	public void pedirPosicionamento() {
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				activity.pedirPosicionamento();
				
			}
		});
	}

	

	@Override
	public void inicioDeJogo(InicioDeJogo inicioDeJogo) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void resultadoTiro(ResultadoTiro resultadoTiro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		
		try {
			
			String ip;
			
			ip = "192.168.0.7";
//			ip = "10.0.1.162";
			
			socket = new Socket(ip, 7777);
			
			out = new ObjectOutputStream(socket.getOutputStream());
		
			in = new ObjectInputStream(socket.getInputStream());			
			
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//TODO coloar o in.readObject dentro do while de novo, nesse e no do servidor
		Resposta resposta;
		try {
			resposta = (Resposta)in.readObject();
			while(resposta != null){
				if(resposta.getTipo() == Resposta.LOGIN_EFETUADO ){
					LoginEfetuado loginEfetuado = (LoginEfetuado) resposta;
					loginEfetuado(loginEfetuado.getId(), loginEfetuado.getPosicao());
				} else if(resposta.getTipo() == Resposta.PEDIR_POSICIONAMENTO ){
					pedirPosicionamento();
				}
				resposta = (Resposta)in.readObject();
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

}
