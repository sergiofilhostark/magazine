package com.summercrow.spacetip.to;


public class LoginEfetuado extends Resposta{
	

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private int posicao;
	
	public LoginEfetuado(){
		super(LOGIN_EFETUADO);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPosicao() {
		return posicao;
	}
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

}
