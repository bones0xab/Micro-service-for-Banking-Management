package com.example.microservice.Web;


import com.example.microservice.DTO.BankAccountRequestDTO;
import com.example.microservice.DTO.BankAccountResponseDTO;
import com.example.microservice.JPA.BankAccount;
import com.example.microservice.JPA.Customer;
import com.example.microservice.Repos.BankAccountRepository;
import com.example.microservice.Repos.CustomerRepository;
import com.example.microservice.Service.AccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;

import java.awt.image.BandedSampleModel;
import java.util.List;

@Controller
public class BankAccountGraphQLController {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerRepository customerRepository;



    @QueryMapping
    public List<BankAccount> accountsList() {
        return bankAccountRepository.findAll();
    }

    @QueryMapping
    public BankAccount getBankAccountById(@Argument String id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Bank Account Not Found with id %s : ", id)));
    }

    @MutationMapping
    public BankAccountResponseDTO addBankAccount(@Argument BankAccountRequestDTO bankAccount) {
        return accountService.addAccount(bankAccount);
    }

    @MutationMapping
    public BankAccountResponseDTO updateAccountBank(@Argument String id , @Argument BankAccountRequestDTO bankAccount) {
        return accountService.updateAccount(id, bankAccount);
    }

    @MutationMapping
    public Boolean deleteAccountBank(@Argument String id ) {
        return accountService.deleteAccount(id);
    }


    @QueryMapping
    public List<Customer> getCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }


}

