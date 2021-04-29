package org.alkemy.Alkemytestjava;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;

@SpringBootApplication
public class AlkemyTestJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlkemyTestJavaApplication.class, args);
		System.out.println("Hello,world");
	}

	@Bean
	public CommandLineRunner initData(TeachersRepository teachersRepository,
									  SubjetsRepository subjetsRepository,
									  UserRepository userRepository){
		return (args) -> {

			//Teacher parameters constructor: (String name, String last_name, int dni, boolean active)

			Teacher teacher1 = teachersRepository.save(new Teacher("Carla", "Riveiro", 26551955, false));
			Teacher teacher2 = teachersRepository.save(new Teacher("Ivan", "Mudryj", 29541935, true));
			Teacher teacher3 = teachersRepository.save(new Teacher("Severus", "Snape", 23265489, true));

			//Subject parameters constructor: (String name, LocalTime time, Integer availability)

			Subject subject1 = subjetsRepository.save(new Subject("Quantum_Physics", LocalTime.of(9,30), teacher1, 3));
			Subject subject2 = subjetsRepository.save(new Subject("Alchemy", LocalTime.of(11,30), teacher2,4));
			Subject subject3 = subjetsRepository.save(new Subject("Dark_Arts", LocalTime.of(17,30), teacher3,2));

			//User parameters constructor:(int dni, Integer file, String role)

			User user1 = userRepository.save(new User(29995559, 220, "student"));
			User user2 = userRepository.save(new User(24545766, 221, "student"));
		};
	}
}
