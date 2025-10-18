package com.example.microservice;

import com.example.microservice.JPA.BankAccount;
import com.example.microservice.JPA.Customer;
import com.example.microservice.Repos.BankAccountRepository;
import com.example.microservice.Repos.CustomerRepository;
import com.example.microservice.enums.AccountType;
import com.sun.source.doctree.SeeTree;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class MicroserviceApplication {

	public static void main(String[] args) {
        SpringApplication.run(MicroserviceApplication.class, args);
	}

    @Bean
    CommandLineRunner  start(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository) {
        return args -> {
            Stream.of("Mohammed", "Yassine", "Hanae", "Imane").forEach(c -> {
                Customer customer = Customer.builder()
                        .name(c)
                        .build();
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(c -> {
                for(int i = 0 ; i < 10 ; i++) {
                    BankAccount bankAccount = BankAccount.builder()
                            .id(UUID.randomUUID().toString())
                            .type(Math.random() > 0.5 ? AccountType.CURRENT_ACCOUNT : AccountType.SAVING_ACCOUNT)
                            .currency("MAD")
                            .balance(1000 + Math.random()*900)
                            .createdAt(new Date())
                            .customer(c)
                            .build();
                    bankAccountRepository.save(bankAccount);
                }
            });
        };

    }






}
