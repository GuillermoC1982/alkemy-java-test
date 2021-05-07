package org.alkemy.Alkemytestjava;

import org.alkemy.Alkemytestjava.Models.Subject;
import org.alkemy.Alkemytestjava.Models.Subscription;
import org.alkemy.Alkemytestjava.Models.Teacher;
import org.alkemy.Alkemytestjava.Models.User;
import org.alkemy.Alkemytestjava.Repositories.SubjetsRepository;
import org.alkemy.Alkemytestjava.Repositories.TeachersRepository;
import org.alkemy.Alkemytestjava.Repositories.UserRepository;
import org.alkemy.Alkemytestjava.Repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalTime;

@SpringBootApplication
public class AlkemyTestJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlkemyTestJavaApplication.class, args);
		System.out.println("Hello,world");
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(TeachersRepository teachersRepository,
									  SubjetsRepository subjetsRepository,
									  UserRepository userRepository,
									  SubscriptionRepository subscriptionRepository){
		return (args) -> {

			//Teacher parameters constructor: (String name, String last_name, int dni, boolean active)

			Teacher teacher1 = teachersRepository.save(new Teacher("Carla", "Riveiro", 26551955, false));
			Teacher teacher2 = teachersRepository.save(new Teacher("Ivan", "Mudryj", 29541935, true));
			Teacher teacher3 = teachersRepository.save(new Teacher("Severus", "Snape", 23265489, true));

			//Subject parameters constructor: (String name, LocalTime time, Integer availability)

			Subject subject1 = subjetsRepository.save(new Subject("Quantum_Physics", LocalTime.of(9,30), teacher1, 3));
			Subject subject2 = subjetsRepository.save(new Subject("Alchemy", LocalTime.of(11,30), teacher2,4));
			Subject subject3 = subjetsRepository.save(new Subject("Dark_Arts", LocalTime.of(17,30), teacher3,2));
			Subject subject4 = subjetsRepository.save(new Subject("Mathematics", LocalTime.of(17,30), teacher3,1));
			Subject subject5 = subjetsRepository.save(new Subject("Spanish", LocalTime.of(17,30), teacher1,1));
			Subject subject6 = subjetsRepository.save(new Subject("Javascript", LocalTime.of(17,40), teacher1,0));

			//User parameters constructor:(int dni, Integer file, String role)

			User user1 = userRepository.save(new User("111", passwordEncoder.encode ("111"), "student"));
			User user2 = userRepository.save(new User("24545766", passwordEncoder.encode ("221"), "student"));
			User user3 = userRepository.save(new User("28313880", passwordEncoder.encode ("123"), "admin"));

			Subscription sus1 = subscriptionRepository.save(new Subscription(subject2, user1));
			Subscription sus2 = subscriptionRepository.save(new Subscription(subject1, user1));
		};
	}
}
