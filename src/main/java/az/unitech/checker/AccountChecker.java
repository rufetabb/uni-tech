package az.unitech.checker;

import az.unitech.dao.entity.Account;
import az.unitech.dto.request.TransferRequest;
import az.unitech.enums.AccountStatus;
import az.unitech.exception.AccountPassiveException;
import az.unitech.exception.AccountSameException;
import az.unitech.exception.NoEnoughAmountException;
import org.springframework.stereotype.Component;

@Component
public class AccountChecker {


    public void checkerAccount(Account senderAccount, Account receiverAccount,
                               TransferRequest transferRequest) {

        if (transferRequest.getAmount().compareTo(senderAccount.getAmount()) > 0) {
            throw new NoEnoughAmountException("There isn't enough amount in the balance");
        }
        if (transferRequest.getFromAccountNumber().equals(transferRequest.getToAccountNumber())) {
            throw new AccountSameException("Accounts are the same, cannot be transferred");
        }
        if (senderAccount.getStatus().equals(AccountStatus.PASSIVE.name()) ||
                receiverAccount.getStatus().equals(AccountStatus.PASSIVE.name())) {
            throw new AccountPassiveException("One or both accounts are passive");
        }
    }
}
