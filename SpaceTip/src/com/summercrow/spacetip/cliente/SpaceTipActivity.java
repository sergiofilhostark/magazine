package com.summercrow.spacetip.cliente;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.summercrow.spacetip.R;
import com.summercrow.spacetip.cliente.proxy.ProxyCliente;
import com.summercrow.spacetip.cliente.proxy.ProxyClienteFactory;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;
import com.summercrow.spacetip.to.NavesPosicionadas;
import com.summercrow.spacetip.to.ResultadoTiro;
import com.summercrow.spacetip.to.Tiro;

public class SpaceTipActivity extends Activity {
	
	private RelativeLayout meuLayout;
	private ImageView nave;
	
	
	//TODO organizar estado do cliente
	private int estado;
	private final int POSICIONANDO = 1;
	private final int AGUARDANDO_INICIO = 2;
	private final int EM_JOGO = 3;
	private final int JOGO_ACABOU = 4;
	
	
	private boolean meuTurno = false;
	private ProgressDialog aguardeDialog;
	
	private ProxyCliente proxyCliente;
	
	private Long idJogador;
	private Integer posicaoJogador;

	private List<NaveCliente> navesMinhas;
	private List<NaveCliente> navesAdversario;
	
	//TODO sera que precisa dessas variaveis globais??
	private int alturaNave;
	private int larguraNave;
	private int alturaNaveInimiga;
	private int larguraNaveInimiga;
	private int alturaTorpedo;
	private int larguraTorpedo;
	private int alturaFogo;
	private int larguraFogo;
	
	private ImageView torpedo;
	private ImageView torpedoInimigo;
	private ImageView fogo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_space_tip);
		
		meuLayout = (RelativeLayout)findViewById(R.id.layout_space_tip);
		
		navesMinhas = new ArrayList<NaveCliente>();
		
		Drawable drwNave = this.getResources().getDrawable(R.drawable.nave);
		alturaNave = drwNave.getIntrinsicHeight();
		larguraNave = drwNave.getIntrinsicWidth();
		
		Drawable drwNaveInimiga = this.getResources().getDrawable(R.drawable.nave_inimiga);
		alturaNaveInimiga = drwNaveInimiga.getIntrinsicHeight();
		larguraNaveInimiga = drwNaveInimiga.getIntrinsicWidth();
		
		Drawable drwTorpedo = this.getResources().getDrawable(R.drawable.torpedo);
		alturaTorpedo = drwTorpedo.getIntrinsicHeight();
		larguraTorpedo = drwTorpedo.getIntrinsicWidth();
		
		Drawable drwFogo = this.getResources().getDrawable(R.drawable.fogo);
		alturaFogo = drwFogo.getIntrinsicHeight();
		larguraFogo = drwFogo.getIntrinsicWidth();
		
		torpedo = new ImageView(this);
		torpedo.setImageResource(R.drawable.torpedo);
		torpedo.setX(0);
		torpedo.setY(0);
//		torpedo.setVisibility(View.INVISIBLE);
		torpedo.setAlpha(0f);
		meuLayout.addView(torpedo);
		
		torpedoInimigo = new ImageView(this);
		torpedoInimigo.setImageResource(R.drawable.torpedo_inimigo);
		torpedoInimigo.setX(0);
		torpedoInimigo.setY(0);
//		torpedoInimigo.setVisibility(View.INVISIBLE);
		torpedoInimigo.setAlpha(0f);
		meuLayout.addView(torpedoInimigo);
		
		fogo = new ImageView(this);
		fogo.setImageResource(R.drawable.fogo);
		fogo.setX(0);
		fogo.setY(0);
//		fogo.setVisibility(View.INVISIBLE);
		fogo.setAlpha(0f);
		meuLayout.addView(fogo);
		
		aguardeDialog = new ProgressDialog(this);
		aguardeDialog.setIndeterminate(true);
		aguardeDialog.setCancelable(false);
		aguardeDialog.setMessage(getString(R.string.aguarde));
		
		proxyCliente = ProxyClienteFactory.newProxyCliente(this);
		
		//exibirTelaLogin();
		
	}

	private void exibirAlerta(int messageId) {
		Builder dialog = new AlertDialog.Builder(this);
		
		dialog.setMessage(messageId);
		dialog.setPositiveButton(R.string.ok, null);
		dialog.show();
	}
	
	private void exibirAlertaErroFatal(int messageId) {
		Builder dialog = new AlertDialog.Builder(this);
		
		dialog.setMessage(messageId);
		dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		dialog.show();
	}
	
	public void exibirLogin() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				exibirTelaLogin();
			}
		});
	}
	
	private void exibirTelaLogin() {
		
		LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.login_dialog, null);
		
		final EditText editLoginName = (EditText)view.findViewById(R.id.edit_login_name);
		
		Builder dialog = new AlertDialog.Builder(this);
		
//		dialog.setTitle(R.string.sua_vez);
		dialog.setView(view);
		dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String nome = editLoginName.getText().toString();
				
				aguardeDialog.show();
				
				enviarLogin(nome);
				
			}
		});
		dialog.show();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_sair){
			finish();
		}
		return true;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				tratarEstado(event);
				break;
	
			default:
				break;
		}
		
		return true;
	}

	private void tratarEstado(MotionEvent event) {
		switch (estado) {
			case POSICIONANDO:
				posicionarNaveMinha(event);
				break;
				
			case EM_JOGO:
				atirar(event);
	
			default:
				break;
		}
	}

	private void atirar(MotionEvent event) {
		if(!meuTurno){
			Toast toast = Toast.makeText(this, "Aguarde a sua vez", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		float x = event.getX();
		float y = event.getY();
		
		y = corrigirY(y);
		
		float metade = getMetade();
		
		if(y <= metade){
			Toast toast = Toast.makeText(this, "Atire no seu lado do campo", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		float distancia = -2 * (y - metade);
		
		enviarAtirar(x, y, distancia);
	}

	private void enviarAtirar(float x, float y, float distancia) {
		int larguraJogo = meuLayout.getWidth();
		int alturaJogo = meuLayout.getHeight();
		
		float xRelativo = x / larguraJogo;
		float yRelativo = y / alturaJogo;
		float distanciaRelativa = distancia / alturaJogo;
		
		Tiro tiro = new Tiro();
		tiro.setX(xRelativo);
		tiro.setY(yRelativo);
		tiro.setDistancia(distanciaRelativa);
		tiro.setIdJogador(idJogador);
		
		proxyCliente.enviarAtirar(tiro, idJogador);
	}

	private void posicionarNaveMinha(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		
		y = corrigirY(y);
		
		float metade = getMetade();
		
		if(y <= metade){
			Toast toast = Toast.makeText(this, "Posicione no seu lado do campo", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		System.out.println(x +" " + y);
		
		ImageView naveImg = new ImageView(this);
		naveImg.setImageResource(R.drawable.nave);
		naveImg.setX(x -(larguraNave/2));
		naveImg.setY(y - (alturaNave/2));
		meuLayout.addView(naveImg);
		
		NaveCliente nave = new NaveCliente(x -(larguraNave/2), y - (alturaNave/2), larguraNave, alturaNave);
		nave.setImageView(naveImg);
		navesMinhas.add(nave);
		
		if(navesMinhas.size() >= 4){
			enviarNavesPosicionadas();
		}
		
	}

	private void enviarNavesPosicionadas() {
		estado = AGUARDANDO_INICIO;
		aguardeDialog.setMessage(getString(R.string.aguardando_inicio));
		aguardeDialog.show();
		
		List<DadosNave> dadosNaves = ajustarDimensoes(navesMinhas);
		
		NavesPosicionadas navesPosicionadas = new NavesPosicionadas();
		navesPosicionadas.setIdJogador(idJogador);
		navesPosicionadas.setDadosNaves(dadosNaves);
		
		proxyCliente.enviarNavesPosicionadas(navesPosicionadas);
	}

	
	
	

	private float getMetade() {
		View fundo = findViewById(R.id.fundo);
		float metade = fundo.getHeight() / 2;
		return metade;
	}

	private float corrigirY(float y) {
		int[] location = {0 , 0};
		meuLayout.getLocationOnScreen(location);
		
		y = y - location[1];
		return y;
	}

	public void loginEfetuado(Long id, int posicao) {
		
		this.idJogador = id;
		this.posicaoJogador = posicao;
		
		aguardeDialog.hide();
		aguardeDialog.setMessage(getString(R.string.aguardando_adversario));
		aguardeDialog.show();
		
		
	}
	
	private List<DadosNave> ajustarDimensoes(List<NaveCliente> naves) {
		int larguraJogo = meuLayout.getWidth();
		int alturaJogo = meuLayout.getHeight();
		List<DadosNave> dadosNaves = new ArrayList<DadosNave>();
		for (NaveCliente nave: naves) {
			float larguraRelativa = nave.getLargura() / larguraJogo;
			float xRelativo = nave.getX() / larguraJogo;
			float alturaRelativa = nave.getAltura() / alturaJogo;
			float yRelativo = nave.getY() / alturaJogo;
			
			DadosNave dados = new DadosNave();
			dados.setAltura(alturaRelativa);
			dados.setLargura(larguraRelativa);
			dados.setX(xRelativo);
			dados.setY(yRelativo);
			
			dadosNaves.add(dados);
		}
		return dadosNaves;
	}
	
	private List<NaveCliente> ajustarDimensoesAdversario(List<DadosNave> dadosNavesAdversario) {
		int larguraJogo = meuLayout.getWidth();
		int alturaJogo = meuLayout.getHeight();
		List<NaveCliente> naves = new ArrayList<NaveCliente>();
		for (DadosNave dados: dadosNavesAdversario) {
			float largura = dados.getLargura() * larguraJogo;
			float x = dados.getX() * larguraJogo;
			float altura = dados.getAltura() * alturaJogo;
			
			float y = dados.getY() - 2 * (dados.getY() - 0.5F) - dados.getAltura();
			
			y = y * alturaJogo;
			
			NaveCliente nave = new NaveCliente(x, y, largura, altura);
			
			naves.add(nave);
		}
		return naves;
	}

	//TODO criar uma nomenclatura para os metodos de envio e de recebimento
	//TODO melhorar esse aguarde, criar metodos como o exibirAlerta
	public void pedirPosicionamento() {
		estado = POSICIONANDO;
		aguardeDialog.hide();
		exibirAlerta(R.string.posicione);
	}
	
	private ImageView posicionarImagemNaveAdversario(float x, float y, int imageId){
		
		ImageView naveImg = new ImageView(this);
		naveImg.setImageResource(imageId);
		naveImg.setX(x);
		naveImg.setY(y);
		meuLayout.addView(naveImg);
		return naveImg;
	}

	public void inicioDeJogo(InicioDeJogo inicioDeJogo) {
		estado = EM_JOGO;
		
		List<DadosNave> dadosNavesAdversario = inicioDeJogo.getNavesAdversario();
		navesAdversario = ajustarDimensoesAdversario(dadosNavesAdversario);
		for (NaveCliente nave : navesAdversario) {
			ImageView imageView = posicionarImagemNaveAdversario(nave.getX(), nave.getY(), R.drawable.nave_inimiga);
			nave.setImageView(imageView);
		}
		
		aguardeDialog.hide();
		
		if(inicioDeJogo.isMeuTurno()){
			meuTurno = true;
			exibirAlerta(R.string.mire);
		} else {
			meuTurno = false;
			aguardeDialog.setMessage(getString(R.string.aguardando_jogada_adversario));
			aguardeDialog.show();
		}
		
		
		
		
	}
	
	private void animarTiro(float x, float y, float distancia, final boolean meuTiro, final Integer idAtingida, final boolean derrotou) {
		ImageView imageTorpedo;
		
		if(meuTiro){
			imageTorpedo = torpedo;
			
		} else {
			float metade = getMetade();
			y = y - 2 * (y - metade);
			imageTorpedo = torpedoInimigo;
			distancia = distancia * -1;
		}
		
		imageTorpedo.setX(x - (larguraTorpedo/2));
		imageTorpedo.setY(y - (alturaTorpedo/2));
		imageTorpedo.bringToFront();
		
		ObjectAnimator aparecer = ObjectAnimator.ofFloat(imageTorpedo, "alpha", 0, 1);
		aparecer.setDuration(100);
		
		ObjectAnimator movimento = ObjectAnimator.ofFloat(imageTorpedo, "y", imageTorpedo.getY() + distancia);
		movimento.setDuration(2000);
		
		ObjectAnimator desaparecer = ObjectAnimator.ofFloat(imageTorpedo, "alpha", 1, 0);
		desaparecer.setDuration(1000);
		
		fogo.setX(x - (larguraFogo/2));
		fogo.setY(y + distancia - (alturaFogo/2));
		fogo.bringToFront();
		
		long tempoExplosao = 1000;
		
		ObjectAnimator explosao1 = ObjectAnimator.ofFloat(fogo, "alpha", 0, 1);
		explosao1.setDuration(tempoExplosao);
		
		ObjectAnimator explosao2 = ObjectAnimator.ofFloat(fogo, "alpha", 1, 0);
		explosao2.setDuration(tempoExplosao);
		
		AnimatorSet animacao = new AnimatorSet();
		
		animacao.play(aparecer).before(movimento);
		animacao.play(movimento).before(desaparecer);
		animacao.play(desaparecer).with(explosao1);
		animacao.play(explosao1).before(explosao2);

		animacao.addListener(new Animator.AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				trocarNaveAtingida(idAtingida, meuTiro);
				
				if(derrotou){
					if(meuTiro){
						exibirAlerta(R.string.voce_venceu);
					} else {
						exibirAlerta(R.string.voce_perdeu);
					}
				}
				else if (meuTiro){
					meuTurno = false;
					aguardeDialog.setMessage(getString(R.string.aguardando_adversario));
					aguardeDialog.show();
				} else {
					meuTurno = true;
				}
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
		
		animacao.start();
		System.out.println("nova animacao");
	}
	
	private void animarTiroOld(float x, float y, float distancia, final boolean meuTiro, final Integer idAtingida, final boolean derrotou) {
		
		ImageView imageTorpedo;
		
		if(meuTiro){
			imageTorpedo = torpedo;
			
		} else {
			float metade = getMetade();
			y = y - 2 * (y - metade);
			imageTorpedo = torpedoInimigo;
			distancia = distancia * -1;
		}
		
		imageTorpedo.setX(x - (larguraTorpedo/2));
		imageTorpedo.setY(y - (alturaTorpedo/2));
		imageTorpedo.bringToFront();
		
		AlphaAnimation aparecer = new AlphaAnimation(0, 1);
		aparecer.setDuration(100);
		
		long tempoTiro = 2000;
		
		Animation movimento = new TranslateAnimation(0, 0 ,0, distancia);
		movimento.setDuration(tempoTiro);
		
		AlphaAnimation desaparecer = new AlphaAnimation(1, 0);
		desaparecer.setDuration(1000);
		desaparecer.setStartOffset(tempoTiro);
		
		
		AnimationSet animacao = new AnimationSet(false);
		animacao.addAnimation(aparecer);
		animacao.addAnimation(movimento);
		animacao.addAnimation(desaparecer);
		animacao.setFillAfter(true);
		
		fogo.setX(x - (larguraFogo/2));
		fogo.setY(y + distancia - (alturaFogo/2));
		fogo.bringToFront();
		
		long tempoExplosao = 1000;
		
		AlphaAnimation explosao1 = new AlphaAnimation(0, 1);
		explosao1.setDuration(tempoExplosao);
		explosao1.setStartOffset(tempoTiro);
		
		AlphaAnimation explosao2 = new AlphaAnimation(1, 0);
		explosao2.setDuration(tempoExplosao);
		explosao2.setStartOffset(tempoTiro + tempoExplosao);
		
		AnimationSet explosao = new AnimationSet(false);
		explosao.addAnimation(explosao1);
		explosao.addAnimation(explosao2);
		explosao.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				trocarNaveAtingida(idAtingida, meuTiro);
				
				if(derrotou){
					if(meuTiro){
						exibirAlerta(R.string.voce_venceu);
					} else {
						exibirAlerta(R.string.voce_perdeu);
					}
				}
				else if (meuTiro){
					meuTurno = false;
					aguardeDialog.setMessage(getString(R.string.aguardando_adversario));
					aguardeDialog.show();
				} else {
					meuTurno = true;
				}
				
			}
		});
		
		imageTorpedo.startAnimation(animacao);
		fogo.startAnimation(explosao);
		
		
		
		
	}

	private void enviarLogin(String nome) {
		proxyCliente.enviarLogin(nome);
	}

	public void resultadoTiro(ResultadoTiro resultadoTiro) {
		Tiro tiro = resultadoTiro.getTiro();
		
		int larguraJogo = meuLayout.getWidth();
		int alturaJogo = meuLayout.getHeight();
		
		float x = tiro.getX() * larguraJogo;
		float y = tiro.getY() * alturaJogo;
		float distancia = tiro.getDistancia() * alturaJogo;
		
		aguardeDialog.hide();
		
		animarTiro(x, y, distancia, resultadoTiro.isMeuTiro(), resultadoTiro.getNaveAtingida(), resultadoTiro.isDerrotou());
		
		
	}

	private void trocarNaveAtingida(Integer idAtingida, boolean meuTiro) {
		if(idAtingida != null){
			
			List<NaveCliente> naves;
			int idImgNaveAtingida;
			if(meuTiro){
				naves = navesAdversario;
				idImgNaveAtingida = R.drawable.nave_inimiga_atingida;
			} else {
				naves = navesMinhas;
				idImgNaveAtingida = R.drawable.nave_atingida;
			}
			
			NaveCliente naveAtingida = naves.get(idAtingida);
			naveAtingida.setAtingido(true);
			naveAtingida.getImageView().setImageResource(idImgNaveAtingida);
		}
	}

	public void reportarErroFatal(final int erro) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				exibirAlertaErroFatal(erro);
			}
		});
	}
	
	
	
	

}
;