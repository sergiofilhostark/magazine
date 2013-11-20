package com.summercrow.spacetip.cliente.proxy.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

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

public class ProxyClienteRest implements ProxyCliente, Runnable{
	
	private SpaceTipActivity activity;
	private String urlBase;
	
	private final int STATUS_OK = 200;
	
	private final long TEMPO_DE_VERIFICACAO = 1000;
	private boolean verificarCliente;
	private Long idJogador;
	
	public ProxyClienteRest(SpaceTipActivity activity){
		
		urlBase = PropertiesSpaceTip.getInstance().getProperty("server.rest.url");
		
		this.activity = activity;
		
		activity.exibirLogin();
	}
	
	private HttpResponse enviarReqCliente(ReqCliente reqCliente, String servico) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(reqCliente);
			
			HttpClient httpclient = new DefaultHttpClient();
			
			String url = urlBase + servico;
			
			HttpPost httpPost = new HttpPost(url);
			StringEntity st = new StringEntity(json);
			st.setContentType("application/json");
			st.setContentEncoding(HTTP.UTF_8);
	        httpPost.setEntity(st);

			return httpclient.execute(httpPost);
			
		} catch (Exception e) {
			e.printStackTrace();
			activity.reportarErroFatal(R.string.falha_comunicar_servidor);
			return null;
		}
		
		
	}

	@Override
	public void run() {
		
		try {
		
			while(verificarCliente){
			
				HttpClient httpclient = new DefaultHttpClient();
				
				String url = urlBase + "verificar_req_servidor";
				
				HttpPost httpPost = new HttpPost(url);
				
				List<NameValuePair> parametros = new ArrayList<NameValuePair>();
				parametros.add(new BasicNameValuePair("idJogador", Long.toString(idJogador)));
		        UrlEncodedFormEntity form = new UrlEncodedFormEntity(parametros, "UTF-8");
		        httpPost.setEntity(form);

				HttpResponse response = httpclient.execute(httpPost);
				
				HttpEntity entity = response.getEntity();
				
				if (entity != null) {

					String json = EntityUtils.toString(entity, HTTP.UTF_8);
					
					ObjectMapper mapper = new ObjectMapper();
					
					JSONObject jsonObject = new JSONObject(json);					
					int tipo = jsonObject.getInt("tipo");
					
					switch (tipo) {
						case ReqServidor.LOGIN_EFETUADO:
							LoginEfetuado login = mapper.readValue(json, LoginEfetuado.class);
							activity.loginEfetuado(login.getId(), login.getPosicao());
							break;
						
						case ReqServidor.PEDIR_POSICIONAMENTO:
							activity.pedirPosicionamento();
							break;
							
						case ReqServidor.INICIO_DE_JOGO:
							InicioDeJogo inicioDeJogo = mapper.readValue(json, InicioDeJogo.class);
							activity.inicioDeJogo(inicioDeJogo);
							break;
							
						case ReqServidor.RESULTADO_TIRO:
							ResultadoTiro resultadoTiro = mapper.readValue(json, ResultadoTiro.class);
							activity.resultadoTiro(resultadoTiro);
							break;
						case ReqServidor.JOGO_ABANDONADO:
							//TODO	IMPLEMENTAR JOGO ABANDONADO
							break;
	
						default:
							break;
					}
				}
			
				esperar();
			}
		
		} catch (Exception e) {
			activity.reportarErroFatal(R.string.falha_comunicar_servidor);
		}
		
	}

	private void esperar() {
		try {
			Thread.sleep(TEMPO_DE_VERIFICACAO);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void enviarLogin(final String nome) {
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Login login = new Login();
				login.setNome(nome);
				
				HttpResponse response = enviarReqCliente(login, "login");
				if(response != null){
					int status = response.getStatusLine().getStatusCode();
					if(status == STATUS_OK){
						
						HttpEntity entity = response.getEntity();
						
						if (entity != null) {
							try {
								String idJogadorText = EntityUtils.toString(entity, HTTP.UTF_8);
								idJogador = Long.parseLong(idJogadorText);
								verificarCliente = true;
								Thread threadVerificacao = new Thread(ProxyClienteRest.this);
								threadVerificacao.start();
							} catch (Exception e) {
								activity.reportarErroFatal(R.string.falha_comunicar_servidor);
							}
						}
					}
				}
			}
		});
		
		thread.start();
	}

	@Override
	public void enviarNavesPosicionadas(final NavesPosicionadas navesPosicionadas) {
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				enviarReqCliente(navesPosicionadas, "naves_posicionadas");
			}
		});
		
		thread.start();
	}

	@Override
	public void enviarAtirar(final Tiro tiro, final Long idJogador) {
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Atirar atirar = new Atirar();
				atirar.setTiro(tiro);
				atirar.setIdJogador(idJogador);
				
				enviarReqCliente(atirar, "atirar");
			}
		});
		
		thread.start();
	}

	@Override
	public void enviarFimDeJogo(Long idJogador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enviarAbandonoDeJogo(Long idJogador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desconectar() {
		// TODO Auto-generated method stub
		
	}
	

}
