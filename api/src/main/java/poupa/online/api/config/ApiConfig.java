package poupa.online.api.config;

import javax.management.RuntimeErrorException;

import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class ApiConfig {

    private final Dotenv dotenv;

    public ApiConfig() {
        this.dotenv = Dotenv.load();

        if (this.getApiToken() == null) {
            throw new RuntimeErrorException(null, "api token is not defined");
        }

        if (this.getApplicationUrl() == null) {
            throw new RuntimeErrorException(null, "application url is not defined");
        }
    }

    public String getApiToken() {
        return dotenv.get("API_TOKEN");
    }

    public String getApplicationUrl() {
        return dotenv.get("APPLICATION_URL");
    }

}
