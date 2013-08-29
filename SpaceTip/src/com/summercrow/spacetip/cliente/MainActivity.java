package com.summercrow.spacetip.cliente;

import java.util.ArrayList;
import java.util.List;

import com.summercrow.spacetip.R;
import com.summercrow.spacetip.cliente.proxy.ProxyClienteLocal;
import com.summercrow.spacetip.to.DadosNave;
import com.summercrow.spacetip.to.InicioDeJogo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private RelativeLayout meuLayout;
	private ImageView nave;
	private ImageView torpedo;
	
	private Batalha batalha;
	
	
	//TODO organizar estado do cliente
	private int estado;
	private final int POSICIONANDO = 1;
	private final int AGUARDANDO_INICIO = 2;
	private final int EM_JOGO = 3;
	private final int JOGO_ACABOU = 4;
	
	
	private boolean meuTurno = false;
	private ProgressDialog aguardeDialog;
	
	private ProxyClienteLocal proxyCliente;
	
	private Long idJogador;
	private Integer posicaoJogador;

	private List<Nave> navesMinhas;
	private List<Nave> navesAdversario;
	
	//TODO sera que precisa dessas variaveis globais??
	private int alturaNave;
	private int larguraNave;
	private int alturaNaveInimiga;
	private int larguraNaveInimiga;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		proxyCliente = new ProxyClienteLocal(this);
		
		setContentView(R.layout.activity_main);
		
		meuLayout = (RelativeLayout)findViewById(R.id.layout_main);
		
		navesMinhas = new ArrayList<Nave>();
		
		Drawable drwNave = this.getResources().getDrawable(R.drawable.nave);
		alturaNave = drwNave.getIntrinsicHeight();
		larguraNave = drwNave.getIntrinsicWidth();
		
		Drawable drwNaveInimiga = this.getResources().getDrawable(R.drawable.nave_inimiga);
		alturaNaveInimiga = drwNaveInimiga.getIntrinsicHeight();
		larguraNaveInimiga = drwNaveInimiga.getIntrinsicWidth();
		
		batalha = new Batalha(this, meuLayout);
		
//		nave = new ImageView(this);
//		nave.setImageResource(R.drawable.nave);
//		nave.setX(0);
//		nave.setY(0);
//		meuLayout.addView(nave);
		
//		torpedo = new ImageView(this);
//		torpedo.setImageResource(R.drawable.torpedo);
//		torpedo.setX(0);
//		torpedo.setY(0);
//		meuLayout.addView(torpedo);
		
//		estado = POSICIONANDO;
//		exibirAlerta(R.string.posicione);
		
		aguardeDialog = new ProgressDialog(this);
		aguardeDialog.setIndeterminate(true);
		aguardeDialog.setCancelable(false);
		aguardeDialog.setMessage(getString(R.string.aguarde));
		
		exibirTelaLogin();
		
	}

	private void exibirAlerta(int messageId) {
		Builder dialog = new AlertDialog.Builder(this);
		
//		dialog.setTitle(R.string.sua_vez);
		dialog.setMessage(messageId);
		dialog.setPositiveButton(R.string.ok, null);
		dialog.show();
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
		
		
		
		
//		
		
//		System.out.println(x + " " + y + " "+ meuLayout.getY());
//		
//		nave.setX(x);
//		nave.setY(y);
		
//		Animation mira = AnimationUtils.loadAnimation(this, R.anim.mira);
//		nave.startAnimation(mira);
		
//		Animation tiro = AnimationUtils.loadAnimation(this, R.anim.tiro);
//		tiro.setStartOffset(1000);
//		tiro.setFillAfter(true);
//		torpedo.setX(x);
//		torpedo.setY(y);
//		torpedo.startAnimation(tiro);
		
//		torpedo.setX(x);
//		torpedo.setY(y);
//		Animation animation = new TranslateAnimation(0, 0 ,0, 300);
//		animation.setDuration(5000);
//		animation.setInterpolator(new LinearInterpolator());
//		animation.setFillAfter(true);
//		torpedo.startAnimation(animation);
//		torpedo.setVisibility(0);
		
		return true;
	}

	private void tratarEstado(MotionEvent event) {
		switch (estado) {
			case POSICIONANDO:
				posicionarNaveMinha(event);
				break;
				
			case EM_JOGO:
				jogar(event);
	
			default:
				break;
		}
	}

	private void jogar(MotionEvent event) {
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
		
		batalha.atirar(x, y, distancia);
		if(batalha.isGanhei()){
			estado = JOGO_ACABOU;
			exibirAlerta(R.string.ganhou);
		}
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
		
		
		posicionarNave(x, y, navesMinhas, R.drawable.nave, larguraNave, alturaNave);
		
		if(navesMinhas.size() >= 4){
			enviarNavesPosicionadas();
		}
		
		
		
		
		
//		batalha.posicionarNaveMinha(this, x, y);
//		
//		if(batalha.isTodasNavesMinhasPosicionadas()){
//			aguardarInicio();
//		}
		
	}

	private void enviarNavesPosicionadas() {
		estado = AGUARDANDO_INICIO;
		aguardeDialog.setMessage(getString(R.string.aguardando_inicio));
		aguardeDialog.show();
		
		List<DadosNave> dadosNaves = ajustarDimensoes(navesMinhas);
		
		
		proxyCliente.enviarNavesPosicionadas(idJogador, dadosNaves);
		
		//REMOVER		
		List<Nave> navesAdversarioTemp = new ArrayList<Nave>();
		Nave nave1 = new Nave(80 -(larguraNave/2), 80 - (alturaNave/2), larguraNave, alturaNave);
		navesAdversarioTemp.add(nave1);
		Nave nave2 = new Nave(160 -(larguraNave/2), 160 - (alturaNave/2), larguraNave, alturaNave);
		navesAdversarioTemp.add(nave2);
		Nave nave3 = new Nave(240 -(larguraNave/2), 240 - (alturaNave/2), larguraNave, alturaNave);
		navesAdversarioTemp.add(nave3);
		Nave nave4 = new Nave(320 -(larguraNave/2), 320 - (alturaNave/2), larguraNave, alturaNave);
		navesAdversarioTemp.add(nave4);
		List<DadosNave> dadosAdversario = ajustarDimensoes(navesAdversarioTemp);
		proxyCliente.enviarNavesPosicionadas(idJogador + 1, dadosAdversario);
	}

	
	
	private void posicionarNave(float x, float y, List<Nave> naves, int imageId, int largura, int altura){
		
		ImageView naveImg = new ImageView(this);
		naveImg.setImageResource(imageId);
		naveImg.setX(x -(largura/2));
		naveImg.setY(y - (altura/2));
		meuLayout.addView(naveImg);
		
		Nave nave = new Nave(x -(largura/2), y - (altura/2), largura, altura);
		nave.setImageView(naveImg);
		naves.add(nave);
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

	private void aguardarInicio() {
		estado = AGUARDANDO_INICIO;
		
		batalha.posicionarMinhaNaveInimiga(this, 100, 100);
		batalha.posicionarMinhaNaveInimiga(this, 200, 200);
		batalha.posicionarMinhaNaveInimiga(this, 300, 300);
		batalha.posicionarMinhaNaveInimiga(this, 400, 400);
		
		iniciarJogo();
	}

	private void iniciarJogo() {
		estado = EM_JOGO;
		exibirAlerta(R.string.mire);
		meuTurno = true;
	}

	public void loginEfetuado(Long id, int posicao) {
		
		this.idJogador = id;
		this.posicaoJogador = posicao;
		
		aguardeDialog.hide();
		aguardeDialog.setMessage(getString(R.string.aguardando_adversario));
		aguardeDialog.show();
		
		
	}
	
	private List<DadosNave> ajustarDimensoes(List<Nave> naves) {
		int larguraJogo = meuLayout.getWidth();
		int alturaJogo = meuLayout.getHeight();
		List<DadosNave> dadosNaves = new ArrayList<DadosNave>();
		for (Nave nave: naves) {
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
	
	private List<Nave> ajustarDimensoesAdversario(List<DadosNave> dadosNavesAdversario) {
		int larguraJogo = meuLayout.getWidth();
		int alturaJogo = meuLayout.getHeight();
		List<Nave> naves = new ArrayList<Nave>();
		for (DadosNave dados: dadosNavesAdversario) {
			float largura = dados.getLargura() * larguraJogo;
			float x = dados.getX() * larguraJogo;
			float altura = dados.getAltura() * alturaJogo;
			float y = dados.getY() * alturaJogo;
			
			Nave nave = new Nave(x, y, largura, altura);
			
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
	
	private void posicionarImagemNaveAdversario(float x, float y, int imageId){
		
		ImageView naveImg = new ImageView(this);
		naveImg.setImageResource(imageId);
		naveImg.setX(x);
		naveImg.setY(y);
		meuLayout.addView(naveImg);
	}

	public void inicioDeJogo(InicioDeJogo inicioDeJogo) {
		estado = EM_JOGO;
		
		List<DadosNave> dadosNavesAdversario = inicioDeJogo.getNavesAdversario();
		navesAdversario = ajustarDimensoesAdversario(dadosNavesAdversario);
		for (Nave nave : navesAdversario) {
			posicionarImagemNaveAdversario(nave.getX(), nave.getY(), R.drawable.nave_inimiga);
		}
		
		aguardeDialog.hide();
		
		if(inicioDeJogo.isSeuTurno()){
			meuTurno = true;
			exibirAlerta(R.string.mire);
		} else {
			meuTurno = false;
			aguardeDialog.setMessage(getString(R.string.aguardando_jogada_adversario));
			aguardeDialog.show();
		}
		
		
		
		
	}

	private void enviarLogin(String nome) {
		proxyCliente.enviarLogin(nome);
		
		//REMOVER
		proxyCliente.enviarLogin("Jogador 2");
	}
	
	
	
	

}
