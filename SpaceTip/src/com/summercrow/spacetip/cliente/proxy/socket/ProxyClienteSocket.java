package com.summercrow.spacetip.cliente.proxy.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.summercrow.spacetip.cliente.SpaceTipActivity;
import com.summercrow.spacetip.cliente.proxy.ProxyCliente;
import com.summercrow.spacetip.to.Atirar;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.Login;
import com.summercrow.spacetip.to.LoginEfetuado;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ReqServidor;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

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
	public void enviarAtirar(Tiro tiro, Long idJogador) {
		try {
			Atirar atirar = new Atirar();
			atirar.setTiro(tiro);
			atirar.setIdJogador(idJogador);
			
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
			
			
			
			
	        try {
	        	
	        	NavesPosicionadas na = new NavesPosicionadas();
	        	na.setIdJogador(55L);
	        	
	        	List<DadosNave> dadosNaves = new ArrayList<DadosNave>();
	        	DadosNave dn = new DadosNave();
	        	dn.setAltura(1F);
	        	dn.setLargura(2F);
	        	dn.setX(3F);
	        	dn.setY(4F);
	        	dadosNaves.add(dn);
	        	DadosNave dn2 = new DadosNave();
	        	dn2.setAltura(5F);
	        	dn2.setLargura(6F);
	        	dn2.setX(7F);
	        	dn2.setY(8F);
	        	dadosNaves.add(dn2);
	        	na.setDadosNaves(dadosNaves);
	        	
	        	ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(na);
				
				String url = "http://192.168.0.7:8080/SpaceTipServerWeb/services/spacetip/navesposicionadas";
				
				HttpClient httpclient = new DefaultHttpClient();
				
				HttpPost httpPost = new HttpPost(url);
				StringEntity st = new StringEntity(json);
				st.setContentType("application/json");
				st.setContentEncoding(HTTP.UTF_8);
	            httpPost.setEntity(st);

				HttpResponse response = httpclient.execute(httpPost);

				HttpEntity entity = response.getEntity();
				
				if (entity != null) {
					

					String jsonText = EntityUtils.toString(entity, HTTP.UTF_8);
					
					
					ObjectMapper mapper2 = new ObjectMapper();
					
					JSONObject jsonObject = new JSONObject(jsonText);
					int tipo = jsonObject.getInt("tipo");
					
			        Atirar atirar2 = mapper2.readValue(jsonText, Atirar.class);
			        
			        System.out.println(1);
				}



				
			} catch (Exception e1) {

				e1.printStackTrace();
			}
	        
			/* */
			
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
