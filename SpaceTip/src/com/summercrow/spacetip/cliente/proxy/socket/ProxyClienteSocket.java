package com.summercrow.spacetip.cliente.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.summercrow.spacetip.R;
import com.summercrow.spacetip.cliente.PropertiesSpaceTip;
import com.summercrow.spacetip.cliente.SpaceTipActivity;
import com.summercrow.spacetip.cliente.proxy.ProxyCliente;
import com.summercrow.spacetip.to.AbandonarJogo;
import com.summercrow.spacetip.to.Atirar;
import com.summercrow.spacetip.to.FimDeJogo;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.Login;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ReqCliente;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;


public class ProxyClienteSocket implements ProxyCliente, Runnable{
	
	private SpaceTipActivity activity;
	private Socket socket;
	
	private ObjectInputStream in;
	
	private ObjectOutputStream out;
	
	private boolean conectado;
	
	
	public ProxyClienteSocket(SpaceTipActivity activity){
		this.activity = activity;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void enviarLogin(String nome) {
		
		Login login = new Login();
		login.setNome(nome);
		
		writeReqCliente(login);
	}
	
	@Override
	public void enviarNavesPosicionadas(NavesPosicionadas navesPosicionadas) {
		writeReqCliente(navesPosicionadas);
	}
	
	@Override
	public void enviarAtirar(Tiro tiro, Long idJogador) {
		
		Atirar atirar = new Atirar();
		atirar.setTiro(tiro);
		atirar.setIdJogador(idJogador);
		
		writeReqCliente(atirar);
	}

	@Override
	public void run() {
		
		try {
			
			PropertiesSpaceTip propertiesSpaceTip = PropertiesSpaceTip.getInstance();
			
			String ip = propertiesSpaceTip.getProperty("server.socket.ip");
			
			socket = new Socket(ip, 7777);
			
			out = new ObjectOutputStream(socket.getOutputStream());
		
			in = new ObjectInputStream(socket.getInputStream());
			
			conectado = true;
			
			activity.exibirLogin();
			
		} catch (Exception e) {
			e.printStackTrace();
			activity.reportarErroFatal(R.string.nao_conectar_servidor);
		}
		
		if(socket != null){
			esperarResposta();
		}
		
	}

	private void esperarResposta() {
		ReqServidor reqServidor;
		try {
			
			reqServidor = (ReqServidor)in.readObject();
			while(reqServidor != null){
				if(reqServidor.getTipo() == ReqServidor.LOGIN_EFETUADO ){
					LoginEfetuado loginEfetuado = (LoginEfetuado) reqServidor;
					activity.loginEfetuado(loginEfetuado.getId(), loginEfetuado.getPosicao());
				} 
				else if(reqServidor.getTipo() == ReqServidor.PEDIR_POSICIONAMENTO){
					activity.pedirPosicionamento();
				} 
				else if(reqServidor.getTipo() == ReqServidor.INICIO_DE_JOGO){
					InicioDeJogo inicioDeJogo = (InicioDeJogo)reqServidor;
					activity.inicioDeJogo(inicioDeJogo);
				}
				else if(reqServidor.getTipo() == ReqServidor.RESULTADO_TIRO){
					ResultadoTiro resultadoTiro = (ResultadoTiro)reqServidor;
					activity.resultadoTiro(resultadoTiro);
				}
				else if(reqServidor.getTipo() == ReqServidor.JOGO_ABANDONADO){
					activity.jogoAbandonado();
				}
				reqServidor = (ReqServidor)in.readObject();
			}
		} catch (Exception e) {
			if(conectado){
				activity.reportarErroFatal(R.string.falha_comunicar_servidor);
			}
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
			e.printStackTrace();
		}
		
	}

	@Override
	public void enviarFimDeJogo(Long idJogador) {
		
			FimDeJogo fimDeJogo = new FimDeJogo();
			
			writeReqCliente(fimDeJogo);
		
	}

	@Override
	public void enviarAbandonarJogo(Long idJogador) throws IOException {
		AbandonarJogo abandonarJogo = new AbandonarJogo();
		
		writeReqCliente(abandonarJogo);
		
	}

	private void writeReqCliente(ReqCliente reqCliente) {
		if(conectado){
			try {
				out.writeObject(reqCliente);
				out.flush();
			} catch (IOException e) {
				activity.reportarErroFatal(R.string.falha_comunicar_servidor);
			}
		}
	}

	@Override
	public void desconectar() {
		if(conectado){
			conectado = false;
			close();
		}
	}

}
