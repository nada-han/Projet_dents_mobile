package ma.ensa.backProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "ma.ensa.backProject.entities")
@ComponentScan(basePackages = {"ma.ensa.backProject.controllers",
		"ma.ensa.backProject.service"
		
})

@EnableJpaRepositories(basePackages = "ma.ensa.backProject.repository")


public class BackProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackProjectApplication.class, args);
	}

}
