package br.com.fiap.teste;

import br.com.fiap.singleton.PropertySingleton;

public class Teste {

	public static void main(String[] args) {
		//Recupera uma configura��o do arquivo .properties
		String b = PropertySingleton
				.getInstance().getProperty("banco");
		System.out.println(b);
	}
	
}
