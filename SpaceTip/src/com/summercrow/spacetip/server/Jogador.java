package com.summercrow.spacetip.server;

import java.util.ArrayList;
import java.util.List;

import com.summercrow.spacetip.Nave;

public class Jogador {
	
	private List<Nave> naves = new ArrayList<Nave>();
	
	public List<Nave> getNaves() {
		return naves;
	}
	
	public void addNave(Nave nave){
		naves.add(nave);
	}
	

}
