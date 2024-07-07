package test.topia.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ExchangeApplication {

	@Bean
	public RestTemplate getRestTemplate(){
//		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
//		clientHttpRequestFactory.setConnectTimeout(3000);
//		return new RestTemplate(clientHttpRequestFactory);

		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExchangeApplication.class, args);
	}

}
