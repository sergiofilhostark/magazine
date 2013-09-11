package com.summercrow.spacetip.to;


public class ResultadoTiro extends ReqServidor{
	
	private static final long serialVersionUID = 1L;
	
	private Tiro tiro;
	private boolean meuTiro;
	private Integer naveAtingida;
	private boolean derrotou;
	
	public ResultadoTiro(){
		super(RESULTADO_TIRO);
	}
	
	
	public boolean isDerrotou() {
		return derrotou;
	}
	public void setDerrotou(boolean derrotou) {
		this.derrotou = derrotou;
	}
	public boolean isMeuTiro() {
		return meuTiro;
	}
	public void setMeuTiro(boolean meuTiro) {
		this.meuTiro = meuTiro;
	}
	public Tiro getTiro() {
		return tiro;
	}
	public void setTiro(Tiro tiro) {
		this.tiro = tiro;
	}
	public Integer getNaveAtingida() {
		return naveAtingida;
	}
	public void setNaveAtingida(Integer naveAtingida) {
		this.naveAtingida = naveAtingida;
	}

}
