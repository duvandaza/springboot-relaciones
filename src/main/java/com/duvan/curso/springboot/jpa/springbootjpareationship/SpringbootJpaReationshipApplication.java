package com.duvan.curso.springboot.jpa.springbootjpareationship;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.Address;
import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.Client;
import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.ClientDetails;
import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.Course;
import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.Invoice;
import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.Student;
import com.duvan.curso.springboot.jpa.springbootjpareationship.repositories.ClientDetailsRepository;
import com.duvan.curso.springboot.jpa.springbootjpareationship.repositories.ClientRepository;
import com.duvan.curso.springboot.jpa.springbootjpareationship.repositories.CourseRepository;
import com.duvan.curso.springboot.jpa.springbootjpareationship.repositories.InvoiceRepository;
import com.duvan.curso.springboot.jpa.springbootjpareationship.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaReationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaReationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToManyBidireccional();
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToManyBidireccional(){

		Student student1 = new Student("jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Andres");
		Course course2 = new Course("Curso de Spring Boot", "Andres");
		
		student1.addCourse(course1);
		student1.addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	@Transactional
	public void manyToManyRemove(){

		manyToManyFind();
		
		Optional<Student> studeOptionalDb = studentRepository.findOneWithCourses(1L);
		
		if(studeOptionalDb.isPresent()){
			
			Student studentDb = studeOptionalDb.get();
			Optional<Course> courseOptionalDb = courseRepository.findById(2L);

			if(courseOptionalDb.isPresent()){
				Course courseDb = courseOptionalDb.get();
				studentDb.getCourses().remove(courseDb);

				studentRepository.save(studentDb);
				System.out.println(studentDb);
			}

		}

	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToManyFind(){

		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();
		
		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course1));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	@SuppressWarnings("null")
	@Transactional
	public void manyToMany(){

		Student student1 = new Student("jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Curso de java master", "Andres");
		Course course2 = new Course("Curso de Spring Boot", "Andres");
		
		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course1));

		studentRepository.saveAll(List.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}


	@Transactional
	public void oneToOneBidireccional() {

		Client client = new Client("Erba", "Pura");
		ClientDetails clientDetails = new ClientDetails(true, 5000);

		client.setClientDetails(clientDetails);
		clientDetails.setClient(client);

		clientRepository.save(client);
		
		System.out.println(client);
	}

	@Transactional
	public void oneToOne() {

		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Erba", "Pura");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);
		
		System.out.println(client);
	}

	@Transactional
	public void RemoveIvoiceBidireccionalFindById() {

		oneToManyIvoiceBidireccionalFindById();

		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client ->{
			Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L);
			invoiceOptional.ifPresent(invoice -> {
				client.getInvoices().remove(invoice);
				invoice.setClient(null);
				clientRepository.save(client);
				System.out.println(client);
			});
		});
	}

	@Transactional
	public void oneToManyIvoiceBidireccional() {

		Client client = new Client("fran", "Mora");

		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de oficina", 8000L);
		
		
		client.addInvoice(invoice1).addInvoice(invoice2); 

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToMany() {

		Client client = new Client("fran", "Mora");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco da gama", 12141);
		
		client.getAddresses().add(address1);		
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToManyIvoiceBidireccionalFindById() {

		Optional<Client> optionalClient = clientRepository.findOne(1L) ;

		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de oficina", 8000L);
			
			client.addInvoice(invoice1).addInvoice(invoice2); 
			clientRepository.save(client);

			System.out.println(client);
		});
	}

	@Transactional
	public void oneToManyFindById() {

		Optional<Client> optionalClient = clientRepository.findById(2L) ;

		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El Carmen", 2141);
			Address address2 = new Address("Corazones", 12412);
			
			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);

			clientRepository.save(client);
			System.out.println(client);
		});
	}

	@Transactional
	public void removeAddresFindById() {

		Optional<Client> optionalClient = clientRepository.findById(2L) ;

		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El Carmen", 2141);
			Address address2 = new Address("Corazones", 12412);
			
			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);
			clientRepository.save(client);
			System.out.println(client);

			Optional<Client> optionalClient2 = clientRepository.findOneWhitAddresses(2L);
			optionalClient2.ifPresent(c -> {
			c.getAddresses().remove(address2);
			clientRepository.save(c);
			System.out.println(c);
		});

		});
	}

	@Transactional
	public void removeAddres() {

		Client client = new Client("fran", "Mora");

		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco da gama", 12141);
		
		client.getAddresses().add(address1);		
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void manyToOne() {

		Client client = new Client("john", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("Compras de oficina", 2000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByIdClient() {

		Optional<Client> optionalClient = clientRepository.findById(1L);

		if (optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice("Compras de oficina", 2000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);
		}
	}
}
