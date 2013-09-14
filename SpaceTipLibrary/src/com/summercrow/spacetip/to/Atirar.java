package com.summercrow.spacetip.to;

public class Atirar extends ReqCliente{
	
	private static final long serialVersionUID = 1L;
	
	private Tiro tiro;
	
	public Atirar(){
		super(ATIRAR);
	}
	
	public Tiro getTiro() {
		return tiro;
	}

	public void setTiro(Tiro tiro) {
		this.tiro = tiro;
	}

	

}
