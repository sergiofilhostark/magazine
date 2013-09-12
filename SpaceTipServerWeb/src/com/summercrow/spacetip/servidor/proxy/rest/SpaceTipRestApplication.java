package com.summercrow.spacetip.servidor.proxy.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


public class SpaceTipRestApplication extends Application{
	
	
	
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> cla = new HashSet<Class<?>>();
		cla.add(FrontControlerRest.class);
		return cla;
	}

}
