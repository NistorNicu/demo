package com.example;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(AnimalRepository ar){
		return args -> {
			Arrays.asList("Cat,Pony,Dog".split(","))
			.forEach(name -> ar.save(new Animal(name)) );
			
			ar.findAll().forEach(System.out::println);
		};
	}
}

@Repository
interface AnimalRepository extends JpaRepository<Animal, Long> {
}

@RestController
class Controller {
	
	@Autowired
	AnimalRepository animalRepository;
	
	@RequestMapping("/animals")
	public List<Animal> getAnimals(){
		return animalRepository.findAll();
	}
}

@Entity(name = "animals")
class Animal {
	@Id
	@GeneratedValue
	Long id;

	String name;
	
	public Animal(){
		
	}

	public Animal(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", name=" + name + "]";
	}

}