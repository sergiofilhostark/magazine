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
	public void loginEfetuado(Long id, int posicao) {
		activity.loginEfetuado(id, posicao);
	}

	@Override
	public void pedirPosicionamento() {
		// TODO Auto-generated method stub
		
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
			socket = new Socket("192.168.0.7", 5555);
			
			out = new ObjectOutputStream(socket.getOutputStream());
		
			in = new ObjectInputStream(socket.getInputStream());			
			
			
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Object object;
		try {
			while((object = in.readObject()) != null){
				if(object instanceof LoginEfetuado){
					LoginEfetuado loginEfetuado = (LoginEfetuado) object;
					loginEfetuado(loginEfetuado.getId(), loginEfetuado.getPosicao());
				}
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
