package com.example.microservice.Service;

import com.example.microservice.DTO.BankAccountRequestDTO;
import com.example.microservice.DTO.BankAccountResponseDTO;
import com.example.microservice.JPA.BankAccount;
import com.example.microservice.Repos.BankAccountRepository;
import com.example.microservice.mappers.AccountMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private  BankAccountRepository bankAccountRepository;
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO) {
        System.out.println("I m here !");
        BankAccount bankAccount = BankAccount.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(new Date())
                .balance(bankAccountDTO.getBalance())
                .type(bankAccountDTO.getType())
                .currency(bankAccountDTO.getCurrency())
                .build();

        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        BankAccountResponseDTO bankAccountResponseDTO = accountMapper.fromBankAccount(saveBankAccount);

        return bankAccountResponseDTO;
    }

    @Override
    public BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO) {
        BankAccount bankAccount = BankAccount.builder()
                        .id(id)
                                .createdAt(new Date())
                                        .balance(bankAccountDTO.getBalance())
                                                .type(bankAccountDTO.getType())
                                                        .currency(bankAccountDTO.getCurrency())
                                                                .build();

        BankAccount saveBankAccount = bankAccountRepository.save(bankAccount);
        BankAccountResponseDTO bankAccountResponseDTO =accountMapper.fromBankAccount(saveBankAccount);

        return bankAccountResponseDTO;
    }

    public Boolean deleteAccount(String id){
        bankAccountRepository.deleteById(id);
        return true;
    }

}
