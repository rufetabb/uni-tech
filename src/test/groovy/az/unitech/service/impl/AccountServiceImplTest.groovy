package az.unitech.service.impl

import az.unitech.checker.AccountChecker
import az.unitech.dao.repository.AccountRepository
import az.unitech.enums.AccountStatus
import az.unitech.exception.AccountNotFoundException
import az.unitech.exception.AccountPassiveException
import az.unitech.exception.AccountSameException
import az.unitech.exception.NoEnoughAmountException
import az.unitech.mapper.AccountMapper
import az.unitech.mock.MockDataGroovy
import spock.lang.Specification

class AccountServiceImplTest extends Specification {

    private AccountMapper accountMapper

    private AccountChecker accountChecker
    private AccountRepository accountRepository


    private AccountServiceImpl accountService

    void setup() {
        accountMapper = Mock()
        accountChecker = new AccountChecker();
        accountRepository = Mock()
        accountService = new AccountServiceImpl(accountRepository, accountMapper, accountChecker)


    }

    def "GetActiveAccountByUserId"() {
        given:
        def userId = "12345"

        when:
        def response = accountService.getActiveAccountByUserId(userId)

        then:
        1 * accountRepository.findByUserIdAndStatus(userId, AccountStatus.ACTIVE.name()) >>
                Optional.of(List.of(MockDataGroovy.accountDetails()))
        1 * accountMapper.mapEntityToRequest(_) >> List.of(MockDataGroovy.accountDetailsDto())
        response.get(0).getAmount() == MockDataGroovy.accountDetailsDto().getAmount()
        response.get(0).getAccountNumber() == MockDataGroovy.accountDetailsDto().getAccountNumber()
        response.get(0).getUserId() == MockDataGroovy.accountDetailsDto().getUserId()

    }

    def "AmountTransfer"() {


        when:
        accountService.amountTransfer(MockDataGroovy.transferRequest())


        then:
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().fromAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForTransfer())
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().toAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetails())


    }

    def "AmountTransferForException"() {


        when:
        accountService.amountTransfer(MockDataGroovy.transferRequest())


        then:
        0 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().toAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForTransfer())
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().fromAccountNumber)
                >> Optional.empty()
        def exception = thrown(AccountNotFoundException)
        exception.message == "sender account not found"


    }

    def "AmountTransferForException2"() {


        when:
        accountService.amountTransfer(MockDataGroovy.transferRequest())


        then:
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().toAccountNumber)
                >> Optional.empty()
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().fromAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForTransfer())
        def exception = thrown(AccountNotFoundException)
        exception.message == "receiver account not found"
    }

    def "AmountTransferForException3"() {

        when:
        accountService.amountTransfer(MockDataGroovy.transferRequest())

        then:
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().toAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForTransfer())
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().fromAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForTransferForNoEnoughMoney())
        def exception = thrown(NoEnoughAmountException)
        exception.message == "There isn't enough amount in the balance"
    }

    def "AmountTransferForException4"() {

        when:
        accountService.amountTransfer(MockDataGroovy.transferRequestForSameAccount())

        then:
        accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().toAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForTransfer())
        accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().fromAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForTransferForSameAccount())
        def exception = thrown(AccountSameException)
        exception.message == "Accounts are the same, cannot be transferred"
    }

    def "AmountTransferForException5"() {

        when:
        accountService.amountTransfer(MockDataGroovy.transferRequest())

        then:
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().toAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForPassiveStatus())
        1 * accountRepository.findByAccountNumber(MockDataGroovy.transferRequest().fromAccountNumber)
                >> Optional.of(MockDataGroovy.accountDetailsForPassiveStatus())
        def exception = thrown(AccountPassiveException)
        exception.message == "One or both accounts are passive"
    }
}
