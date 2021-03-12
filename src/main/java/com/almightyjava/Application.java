package com.almightyjava;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ApplicationRunner run(BookRepository bookRepository) {
		return args -> Stream.of("book1", "book2", "book3", "book4")
				.forEach(book -> bookRepository.save(new Book(null, book)));
	}
}

@RestController
class BookRestController {

	private final BookRepository bookRepository;

	public BookRestController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@GetMapping("/books")
	List<Book> findAllBooks() {
		return this.bookRepository.findAll();
	}
}

@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	UserDetailsService user() {
		return new InMemoryUserDetailsManager(
				Collections.singleton(User.withUsername("test").password("{noop}test").roles("admin").build()));
	}
}

interface BookRepository extends JpaRepository<Book, Long> {

}

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Book {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
}