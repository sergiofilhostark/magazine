package com.summercrow.spacetip.cliente.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.summercrow.spacetip.cliente.MainActivity;
import com.summercrow.spacetip.cliente.proxy.ProxyCliente;
import com.summercrow.spacetip.to.Atirar;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.Login;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ReqServidor;
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
			Login login = new Login();
			login.setNome(nome);
			
			out.writeObject(login);
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
			Atirar atirar = new Atirar();
			atirar.setTiro(tiro);
			
			out.writeObject(atirar);
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
	public void inicioDeJogo(final InicioDeJogo inicioDeJogo) {
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				activity.inicioDeJogo(inicioDeJogo);
				
			}
		});
	}

	

	@Override
	public void resultadoTiro(final ResultadoTiro resultadoTiro) {
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				activity.resultadoTiro(resultadoTiro);
				
			}
		});
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
		ReqServidor reqServidor;
		try {
			reqServidor = (ReqServidor)in.readObject();
			while(reqServidor != null){
				if(reqServidor.getTipo() == ReqServidor.LOGIN_EFETUADO ){
					LoginEfetuado loginEfetuado = (LoginEfetuado) reqServidor;
					loginEfetuado(loginEfetuado.getId(), loginEfetuado.getPosicao());
				} 
				else if(reqServidor.getTipo() == ReqServidor.PEDIR_POSICIONAMENTO){
					pedirPosicionamento();
				} 
				else if(reqServidor.getTipo() == ReqServidor.INICIO_DE_JOGO){
					InicioDeJogo inicioDeJogo = (InicioDeJogo)reqServidor;
					inicioDeJogo(inicioDeJogo);
				}
				else if(reqServidor.getTipo() == ReqServidor.RESULTADO_TIRO){
					ResultadoTiro resultadoTiro = (ResultadoTiro)reqServidor;
					resultadoTiro(resultadoTiro);
				}
				reqServidor = (ReqServidor)in.readObject();
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
