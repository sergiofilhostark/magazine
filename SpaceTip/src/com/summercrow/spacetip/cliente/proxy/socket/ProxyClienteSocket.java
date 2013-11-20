package com.summercrow.spacetip.cliente.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.summercrow.spacetip.R;
import com.summercrow.spacetip.cliente.PropertiesSpaceTip;
import com.summercrow.spacetip.cliente.SpaceTipActivity;
import com.summercrow.spacetip.cliente.proxy.ProxyCliente;
import com.summercrow.spacetip.to.Atirar;
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
	
	
	public ProxyClienteSocket(SpaceTipActivity activity){
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
			activity.reportarErroFatal(R.string.falha_comunicar_servidor);
		}
	}
	
	@Override
	public void enviarNavesPosicionadas(NavesPosicionadas navesPosicionadas) {
		try {
			out.writeObject(navesPosicionadas);
			out.flush();
		} catch (IOException e) {
			activity.reportarErroFatal(R.string.falha_comunicar_servidor);
		}
	}
	
	@Override
	public void enviarAtirar(Tiro tiro, Long idJogador) {
		try {
			Atirar atirar = new Atirar();
			atirar.setTiro(tiro);
			atirar.setIdJogador(idJogador);
			
			out.writeObject(atirar);
			out.flush();
		} catch (IOException e) {
			activity.reportarErroFatal(R.string.falha_comunicar_servidor);
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
			
			PropertiesSpaceTip propertiesSpaceTip = PropertiesSpaceTip.getInstance();
			
			String ip = propertiesSpaceTip.getProperty("server.socket.ip");
			
			socket = new Socket(ip, 7777);
			
			out = new ObjectOutputStream(socket.getOutputStream());
		
			in = new ObjectInputStream(socket.getInputStream());
			
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
			activity.reportarErroFatal(R.string.falha_comunicar_servidor);
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
		try {
			ReqCliente reqCliente = new ReqCliente(ReqCliente.FIM_DE_JOGO);
			
			out.writeObject(reqCliente);
			out.flush();
		} catch (IOException e) {
			activity.reportarErroFatal(R.string.falha_comunicar_servidor);
		}
	}

	@Override
	public void enviarAbandonoDeJogo(Long idJogador) throws IOException {
		ReqCliente reqCliente = new ReqCliente(ReqCliente.ABANDONAR_JOGO);
		
		out.writeObject(reqCliente);
		out.flush();
		
	}

}
