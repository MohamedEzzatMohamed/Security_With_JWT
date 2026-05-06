package com.mezzat.security_with_jwt;

import com.mezzat.security_with_jwt.data.entity.Role;
import com.mezzat.security_with_jwt.data.entity.User;
import com.mezzat.security_with_jwt.domain.service.UserService;
import org.hibernate.mapping.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityWithJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityWithJwtApplication.class, args);
	}



//	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "omarezzat@glamera.com", "mohamed", "ezzat", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "wsmith@glamera.com", "will", "smith", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "arnolds@glamera.com", "arnold", "schwartzenegger", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "jcarry@glamera.com", "jim", "carry", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "mostafas@glamera.com", "mostafa", "saeed", "1234", new ArrayList<>()));

			userService.addRoleToUser("omarezzat@glamera.com", "ROLE_USER");
			userService.addRoleToUser("omarezzat@glamera.com", "ROLE_MANAGER");
			userService.addRoleToUser("wsmith@glamera.com", "ROLE_USER");
			userService.addRoleToUser("arnolds@glamera.com", "ROLE_USER");
			userService.addRoleToUser("jcarry@glamera.com", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("mostafas@glamera.com", "ROLE_USER");


		};
	}

}
