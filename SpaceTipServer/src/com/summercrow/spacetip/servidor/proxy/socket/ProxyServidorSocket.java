package com.summercrow.spacetip.servidor.proxy.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.summercrow.spacetip.servidor.Controlador;
import com.summercrow.spacetip.servidor.Jogador;
import com.summercrow.spacetip.servidor.Partida;
import com.summercrow.spacetip.servidor.proxy.ProxyServidor;
import com.summercrow.spacetip.to.Atirar;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.Login;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.PedirPosicionamento;
import com.summercrow.spacetip.to.ReqCliente;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class ProxyServidorSocket implements Runnable, ProxyServidor{
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Jogador jogador;
	private Controlador controlador;
	private boolean conectado;
	
	public ProxyServidorSocket(Socket socket) throws IOException{
		conectado = true;
		this.socket = socket;
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		
	}
	
	public Controlador getControlador() {
		return controlador;
	}
	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}
	
	
	public Jogador getJogador() {
		return jogador;
	}
	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	@Override
	public void run() {
		
		try {
			
			ReqCliente reqCliente = (ReqCliente)in.readObject();
			
			if(reqCliente.getTipo() == ReqCliente.LOGIN){
				Login login = (Login)reqCliente;
				String nome = login.getNome();
				
				System.out.println("recebi "+nome);
				
				Jogador jogador = controlador.criarJogador(nome);			
				setJogador(jogador);
				jogador.setProxyServidor(this);
				controlador.entrarPartida(jogador);
			
				
				
				while(conectado &&
						((reqCliente = (ReqCliente)in.readObject()) != null)){
					
					if(reqCliente.getTipo() == ReqCliente.NAVES_POSICIONADAS){
						NavesPosicionadas navesPosicionadas = (NavesPosicionadas) reqCliente;
						navesPosicionadas(navesPosicionadas);
					}
					else if(reqCliente.getTipo() == ReqCliente.ATIRAR){
						Atirar atirar = (Atirar)reqCliente;
						atirar(atirar.getTiro());
					}
					else if(reqCliente.getTipo() == ReqCliente.FIM_DE_JOGO){
						conectado = false;
					}
					else if(reqCliente.getTipo() == ReqCliente.ABANDONAR_JOGO){
						abandonarJogo();
						conectado = false;
					}
				}
			}
			
			
			
			
			
		} catch (Exception e) {
			abandonarJogo();
			e.printStackTrace();
		} finally {
			System.out.println("fechando");
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
	public void enviarPedidoPosicionamento() {
		PedirPosicionamento pedirPosicionamento = new PedirPosicionamento();
		enviarReqServidor(pedirPosicionamento);
	}
	@Override
	public void enviarLoginEfetuado() {
		LoginEfetuado loginEfetuado = new LoginEfetuado();
		loginEfetuado.setId(jogador.getId());
		loginEfetuado.setPosicao(jogador.getPosicao());
		enviarReqServidor(loginEfetuado);
	}
	
	@Override
	public void enviarInicioDeJogo(InicioDeJogo inicioDeJogo) {
		enviarReqServidor(inicioDeJogo);
	}
	
	@Override
	public void enviarResultadoTiro(ResultadoTiro resultadoTiro) {
		enviarReqServidor(resultadoTiro);
	}

	private void enviarReqServidor(ReqServidor resposta) {
		try {
			if(conectado){
				out.writeObject(resposta);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void navesPosicionadas(NavesPosicionadas navesPosicionadas) {
		Partida partida = jogador.getPartida();
		partida.navesPosicionadas(jogador, navesPosicionadas.getDadosNaves());
	}
	
	@Override
	public void atirar(Tiro tiro) {
		Partida partida = jogador.getPartida();
		partida.atirar(jogador, tiro);
	}
	
	@Override
	public void abandonarJogo() {
		Partida partida = jogador.getPartida();
		partida.abandonarJogo(jogador);
	}

	@Override
	public void enviarJogoAbandonado() {
		ReqServidor reqServidor = new ReqServidor(ReqServidor.JOGO_ABANDONADO);
		enviarReqServidor(reqServidor);
	}
	
	

}
