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

public class Conector implements Runnable, ProxyServidor{
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Jogador jogador;
	private Controlador controlador;
	
	public Conector(Socket socket){
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			
			
				reqCliente = (ReqCliente)in.readObject();
				while(reqCliente != null){
					
					if(reqCliente.getTipo() == ReqCliente.NAVES_POSICIONADAS){
						NavesPosicionadas navesPosicionadas = (NavesPosicionadas) reqCliente;
						navesPosicionadas(navesPosicionadas);
					}
					else if(reqCliente.getTipo() == ReqCliente.ATIRAR){
						Atirar atirar = (Atirar)reqCliente;
						atirar(atirar.getTiro());
					}
					
					reqCliente = (ReqCliente)in.readObject();
				}
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
	@Override
	public void login(String nome) {
		// TODO Verificar a utilidade desse metoddo
		
	}
	@Override
	public void enviarPedidoPosicionamento(Jogador jogador) {
		PedirPosicionamento pedirPosicionamento = new PedirPosicionamento();
		enviarReqServidor(pedirPosicionamento);
	}
	@Override
	public void enviarLoginEfetuado(Jogador jogador) {
		LoginEfetuado loginEfetuado = new LoginEfetuado();
		loginEfetuado.setId(jogador.getId());
		loginEfetuado.setPosicao(jogador.getPosicao());
		enviarReqServidor(loginEfetuado);
	}
	
	@Override
	public void enviarInicioDeJogo(Jogador jogador, InicioDeJogo inicioDeJogo) {
		enviarReqServidor(inicioDeJogo);
	}
	
	@Override
	public void enviarResultadoTiro(Jogador jogador, ResultadoTiro resultadoTiro) {
		enviarReqServidor(resultadoTiro);
	}

	private void enviarReqServidor(ReqServidor resposta) {
		try {
			out.writeObject(resposta);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	

}
