package com.summercrow.spacetip.servidor.proxy.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


public class SpaceTipRestApplication extends Application{
	
	
	@Override
	public Set<Object> getSingletons() {
		FrontControlerRest frontControlerRest = new FrontControlerRest();
		
		Set<Object> singletons = new HashSet<Object>();
		singletons.add(frontControlerRest);
		
		return singletons;
	}
	

}
