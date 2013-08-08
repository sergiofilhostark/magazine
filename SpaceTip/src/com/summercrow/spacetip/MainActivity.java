package com.summercrow.spacetip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private RelativeLayout meuLayout;
	private ImageView nave;
	private ImageView torpedo;
	
	private Batalha batalha;
	private int estado;
	private final int POSICIONANDO = 1;
	private final int AGUARDANDO_INICIO = 2;
	private final int EM_JOGO = 3;
	private final int JOGO_ACABOU = 4;
	private boolean minhaVez = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		meuLayout = (RelativeLayout)findViewById(R.id.layout_main);
		
		
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
		
		estado = POSICIONANDO;
		exibirAlerta(R.string.posicione);
		
	}

	private void exibirAlerta(int messageId) {
		Builder dialog = new AlertDialog.Builder(this);
		
//		dialog.setTitle(R.string.sua_vez);
		dialog.setMessage(messageId);
		dialog.setPositiveButton(R.string.ok, null);
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
		if(!minhaVez){
			Toast toast = Toast.makeText(this, "Aguarde a sua vez", Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		
		float x = (int)event.getX();
		float y = (int)event.getY();
		
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
		
		batalha.posicionarNaveMinha(this, x, y);
		
		if(batalha.isTodasNavesMinhasPosicionadas()){
			aguardarInicio();
		}
		
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
		minhaVez = true;
	}
	
	

}
