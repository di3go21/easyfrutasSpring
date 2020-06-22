package com.easyfrutas.util;

import org.apache.commons.codec.digest.DigestUtils;



public class Codificador {

	public static String codifica(String crudo) {
		String codificado= DigestUtils.md5Hex(crudo+System.currentTimeMillis());
		return codificado;
	}

	public static boolean verifica(String prueba,String codificado) {
		

		return DigestUtils.md5Hex(prueba).contentEquals(codificado);
	}

	
	
}
