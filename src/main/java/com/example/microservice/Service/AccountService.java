package com.example.microservice.Service;

import com.example.microservice.DTO.BankAccountRequestDTO;
import com.example.microservice.DTO.BankAccountResponseDTO;
import com.example.microservice.JPA.BankAccount;

public interface AccountService {
     BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);
     BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO);
    Boolean deleteAccount(String id);
}
