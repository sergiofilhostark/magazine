package com.summercrow.spacetip.to;

public class Login extends ReqCliente{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public Login(){
		super(LOGIN);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	

}
