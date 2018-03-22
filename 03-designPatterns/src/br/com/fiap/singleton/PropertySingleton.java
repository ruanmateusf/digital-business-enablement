package br.com.fiap.singleton;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertySingleton {
	
	private static final Logger log = 
		LoggerFactory.getLogger(PropertySingleton.class);

	//1 - Atributo est�tico que mantem a �nica instancia
	private static Properties prop;
	
	//3 - Construtor private
	private PropertySingleton() { }
	
	//2 - M�todo est�tico getInstance que retorna a �nica instancia
	public static Properties getInstance() {
		if (prop == null) {
			log.trace("Criando o objeto properties");
			prop = new Properties();
			//carregar o arquivo com as propriedades
			try {
				log.info("Carregando o arquivo de propriedade do sistema");
				prop.load(PropertySingleton.class
						.getResourceAsStream("/config.properties"));
			} catch (IOException e) {
				log.error("Arquivo de configura��o n�o carregado");
				e.printStackTrace();
			}
		}
		log.debug("Retornando as configura��es do sistema");
		return prop;
	}
	
}