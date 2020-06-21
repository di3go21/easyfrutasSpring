package com.easyfrutas.util;


public class Numeros {
	
	
	public static double redondeaAdos(double decimal) {
		
		double result=(double) Math.round(decimal * 100) / 100;
		return result;
	
	}

	
	
}
