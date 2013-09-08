package com.summercrow.spacetip.cliente;

import java.util.ArrayList;
import java.util.List;

import com.summercrow.spacetip.R;
import com.summercrow.spacetip.cliente.proxy.ProxyClienteLocal;
import com.summercrow.spacetip.servidor.proxy.local.ProxyServidorLocal;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class Batalha {
	
	private List<ImageView> navesMinhasImg;
	private List<ImageView> navesInimigoImg;
	private List<Nave> navesMinhas;
	private List<Nave> navesInimigo;
	
	private ImageView torpedo;
	private ImageView torpedoInimigo;
	private ImageView fogo;
	
//	private float meioCampo;
	
	private final int NUMERO_NAVES;
	
	private final int JOGADOR = 1;
	private final int INIMIGO = 2;
	private ViewGroup viewGroup;
	
	private int abatidasMinhas;
	private int abatidasInimigo;
	
	private Drawable drwNave;
	private Drawable drwFogo;
	private Drawable drwTorpedo;
	
	int alturaNave;
	int larguraNave;
	int alturaFogo;
	int larguraFogo;
	int alturaTorpedo;
	int larguraTorpedo;
	
	
	
	//teste 2
	public Batalha(Context context, ViewGroup viewGroup) {
		NUMERO_NAVES = 4;
		
		navesMinhasImg = new ArrayList<ImageView>(NUMERO_NAVES);
		navesInimigoImg = new ArrayList<ImageView>(NUMERO_NAVES);
		navesMinhas = new ArrayList<Nave>(NUMERO_NAVES);
		navesInimigo = new ArrayList<Nave>(NUMERO_NAVES);
		
		abatidasMinhas = 0;
		abatidasInimigo = 0;
		
		this.viewGroup = viewGroup;
		
		torpedo = new ImageView(context);
		torpedo.setImageResource(R.drawable.torpedo);
		torpedo.setX(0);
		torpedo.setY(0);
		torpedo.setVisibility(View.INVISIBLE);
		viewGroup.addView(torpedo);
		
		torpedoInimigo = new ImageView(context);
		torpedoInimigo.setImageResource(R.drawable.torpedo_inimigo);
		torpedoInimigo.setX(0);
		torpedoInimigo.setY(0);
		torpedoInimigo.setVisibility(View.INVISIBLE);
		viewGroup.addView(torpedoInimigo);
		
		fogo = new ImageView(context);
		fogo.setImageResource(R.drawable.fogo);
		fogo.setX(0);
		fogo.setY(0);
		fogo.setVisibility(View.INVISIBLE);
		viewGroup.addView(fogo);
		
		drwNave = context.getResources().getDrawable(R.drawable.nave);
		alturaNave = drwNave.getIntrinsicHeight();
		larguraNave = drwNave.getIntrinsicWidth();
		
		drwTorpedo = context.getResources().getDrawable(R.drawable.torpedo);
		alturaTorpedo = drwTorpedo.getIntrinsicHeight();
		larguraTorpedo = drwTorpedo.getIntrinsicWidth();
		
		drwFogo = context.getResources().getDrawable(R.drawable.fogo);
		alturaFogo = drwFogo.getIntrinsicHeight();
		larguraFogo = drwFogo.getIntrinsicWidth();
		
//		this.meioCampo = meioCampo;
		
	}
	
	public boolean posicionarNaveMinha(Context context, float x, float y){
		
		return posicionarNave(navesMinhasImg, navesMinhas, JOGADOR, R.drawable.nave, context,  x, y);
	}
	
	public boolean posicionarMinhaNaveInimiga(Context context, float x, float y){
		
		return posicionarNave(navesInimigoImg, navesInimigo, INIMIGO, R.drawable.nave_inimiga, context,  x, y);
	}
	
	private boolean posicionarNave(List<ImageView> navesImg, List<Nave> naves, int quem, int id_draw, Context context, float x, float y){
		
		if(navesImg.size() >= NUMERO_NAVES){
			return false;
		}
		
//		if((quem == JOGADOR) && (y >= meioCampo)){
//			return false;
//		}
//		
//		if((quem == INIMIGO) && (y <= meioCampo)){
//			return false;
//		}
		
		ImageView naveImg = new ImageView(context);
		naveImg.setImageResource(id_draw);
		naveImg.setX(x -(larguraNave/2));
		naveImg.setY(y - (alturaNave/2));
		viewGroup.addView(naveImg);
		navesImg.add(naveImg);
		
		Nave nave = new Nave(x -(larguraNave/2), y - (alturaNave/2), larguraNave, alturaNave);
		naves.add(nave);
		
		return true;
	}
	
	public boolean isTodasNavesMinhasPosicionadas(){
		return (navesMinhasImg.size() >= NUMERO_NAVES);
	}
	
	public void atirar(float x, float y, float yT){
		
		animarTiro(x, y, yT);
		
		
		
	}

	private void verificarAcerto(float x, float y, float yT) {
		for (int i = 0; i < navesInimigoImg.size(); i++) {
			ImageView naveImg = navesInimigoImg.get(i);
			Nave nave = navesInimigo.get(i);
			if(!nave.isAtingido() && nave.isAcertou(x, y + yT)){
				nave.setAtingido(true);
				abatidasInimigo++;
				naveImg.setImageResource(R.drawable.nave_inimiga_atingida);
			}
		}
	}
	
	public boolean isGanhei(){
		return (abatidasInimigo >= 4);
	}

	private void animarTiro(final float x, final float y, final float yT) {
		
		torpedo.setX(x - (larguraTorpedo/2));
		torpedo.setY(y - (alturaTorpedo/2));
		torpedo.bringToFront();
		
		AlphaAnimation aparecer = new AlphaAnimation(0, 1);
		aparecer.setDuration(100);
		
		long tempoTiro = 2000;
		
		Animation movimento = new TranslateAnimation(0, 0 ,0, yT);
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
		fogo.setY(y + yT - (alturaFogo/2));
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				verificarAcerto(x, y, yT);
			}
		});
		
		torpedo.startAnimation(animacao);
		fogo.startAnimation(explosao);
	}

}
