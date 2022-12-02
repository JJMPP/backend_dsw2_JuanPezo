package com.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendDsw2JuanPezoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendDsw2JuanPezoApplication.class, args);
	}
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
//	@Autowired
//	RoleService roleService;
//	
//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			Role roleAdmin = new Role(RoleName.ROLE_ADMIN);
//			Role roleUser = new Role(RoleName.ROLE_USER);
//			roleService.save(roleAdmin);
//			roleService.save(roleUser);
//			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_USER"));			
//			
//			userService.saveUser(new User(null, "Renato", "renato@correo.com", "rbhairava", "12345", new ArrayList<>()));
//			userService.saveUser(new User(null, "David", "david@correo.com", "david", "12345", new ArrayList<>()));
//			userService.saveUser(new User(null, "Alonso", "alonso@correo.com", "alonso", "12345", new ArrayList<>()));
//			userService.saveUser(new User(null, "Olinda", "olinda@correo.com", "olinda", "12345", new ArrayList<>()));
//			
//			userService.addRoleToUser("alonso", "ROLE_USER");
//			userService.addRoleToUser("olinda", "ROLE_USER");
//			userService.addRoleToUser("rbhairava", "ROLE_SUPER_ADMIN");
//			userService.addRoleToUser("david", "ROLE_ADMIN");
//		};
//	}

}
