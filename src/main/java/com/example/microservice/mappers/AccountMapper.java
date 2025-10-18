package com.example.microservice.mappers;

import com.example.microservice.DTO.BankAccountRequestDTO;
import com.example.microservice.DTO.BankAccountResponseDTO;
import com.example.microservice.JPA.BankAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public BankAccountResponseDTO fromBankAccount(BankAccount bankAccount) {
            BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
            BeanUtils.copyProperties(bankAccount, bankAccountResponseDTO);
            return bankAccountResponseDTO;
    }



}
