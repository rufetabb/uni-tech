package az.unitech.mock

import az.unitech.client.CurrencyResponse
import az.unitech.dao.entity.Account
import az.unitech.dao.entity.UserDetailsEntity
import az.unitech.dto.request.TransferRequest
import az.unitech.dto.request.UserRegisterDto
import az.unitech.dto.response.AccountDto
import az.unitech.enums.AccountStatus

import java.time.LocalDateTime

class MockDataGroovy {


    static Account accountDetails() {
        return Account.builder()
                .userId("12345")
                .amount(BigDecimal.valueOf(100))
                .status(AccountStatus.ACTIVE.name())
                .accountNumber("123456")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

    }

    static Account accountDetailsForPassiveStatus() {
        return Account.builder()
                .userId("12345")
                .amount(BigDecimal.valueOf(100))
                .status(AccountStatus.PASSIVE.name())
                .accountNumber("123456")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

    }

    static Account accountDetailsForTransfer() {
        return Account.builder()
                .userId("12347")
                .amount(BigDecimal.valueOf(120))
                .status(AccountStatus.ACTIVE.name())
                .accountNumber("123457")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

    }

    static Account accountDetailsForTransferForNoEnoughMoney() {
        return Account.builder()
                .userId("12347")
                .amount(BigDecimal.valueOf(90))
                .status(AccountStatus.ACTIVE.name())
                .accountNumber("123457")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

    }

    static Account accountDetailsForTransferForSameAccount() {
        return Account.builder()
                .userId("12347")
                .amount(BigDecimal.valueOf(120))
                .status(AccountStatus.ACTIVE.name())
                .accountNumber("123457")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();

    }


    static AccountDto accountDetailsDto() {
        return AccountDto.builder()
                .userId("12345")
                .amount(BigDecimal.valueOf(100))
                .accountNumber("123456").build();

    }


    static TransferRequest transferRequest() {
        return TransferRequest.builder()
                .fromAccountNumber("12345")
                .toAccountNumber("123456")
                .amount(BigDecimal.valueOf(100)).build();

    }

    static TransferRequest transferRequestForSameAccount() {
        return TransferRequest.builder()
                .fromAccountNumber("12345")
                .toAccountNumber("12345")
                .amount(BigDecimal.valueOf(100)).build();

    }


    static UserRegisterDto userRegisterDto() {
        return UserRegisterDto.builder()
                .name("Test")
                .surname("Testov")
                .userId("12345")
                .password("Test123456")
                .pin("TestPin")
                .build();

    }

    static UserDetailsEntity userDetailsEntity() {
        return UserDetailsEntity.builder()
                .name("Test")
                .surname("Testov")
                .userId("12345")
                .password("Test123456")
                .pin("TestPin")
                .build();

    }

    static CurrencyResponse currencyResponse() {
        return CurrencyResponse.builder()
                .base("USD")
                .result(Map.of("AZN", BigDecimal.valueOf(1.7)))
                .build();

    }


}
