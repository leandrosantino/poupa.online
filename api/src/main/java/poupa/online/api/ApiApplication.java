package poupa.online.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import com.mercadopago.MercadoPagoConfig;

import poupa.online.api.config.ApiConfig;

@SpringBootApplication
@EnableAsync
public class ApiApplication {

	public static void main(String[] args) {
		var apiConfig = new ApiConfig();
		String apiToken = apiConfig.getApiToken();

		MercadoPagoConfig.setAccessToken(apiToken);

		SpringApplication.run(ApiApplication.class, args);
		System.out.println("\033[H\033[2J");
		System.out.println("Server Online!! \n");

	}

}
