package in.icitinstitute;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JwtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtDemoApplication.class, args);
	}

	@Bean
	public Map<String, String> userStore() {
		Map<String, String> users = new HashMap<>();
		users.put("user1", "$2y$12$VWRJJJE.wZeXwFZFOFqzmuO/8WcRCmpZmwYRQPXTnmTET9swJjzmW");
		users.put("user2", "$2y$12$VWRJJJE.wZeXwFZFOFqzmuO/8WcRCmpZmwYRQPXTnmTET9swJjzmW");
		return users;
	}
}
