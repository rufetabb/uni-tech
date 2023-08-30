package az.unitech.service.impl;

import az.unitech.checker.AccountChecker;
import az.unitech.dao.repository.AccountRepository;
import az.unitech.dto.response.AccountDto;
import az.unitech.dto.response.TransferResponse;
import az.unitech.dto.request.TransferRequest;
import az.unitech.enums.AccountStatus;
import az.unitech.exception.AccountNotFoundException;
import az.unitech.mapper.AccountMapper;
import az.unitech.service.AccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccountChecker accountChecker;


    @Override
    public List<AccountDto> getActiveAccountByUserId(String userId) {
        var accountResponseList =
                accountMapper.mapEntityToRequest(
                        accountRepository.findByUserIdAndStatus(userId, AccountStatus.ACTIVE.name()).get());
        return accountResponseList;
    }


    @Override
    public TransferResponse amountTransfer(TransferRequest transferRequest) {
        var senderAccount = accountRepository.findByAccountNumber(transferRequest.getFromAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("sender account not found"));
        var receiverAccount = accountRepository.findByAccountNumber(transferRequest.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("receiver account not found"));

        accountChecker.checkerAccount(senderAccount, receiverAccount, transferRequest);

        senderAccount.setAmount(senderAccount.getAmount().subtract(transferRequest.getAmount()));
        accountRepository.save(senderAccount);
        receiverAccount.setAmount(senderAccount.getAmount().add(transferRequest.getAmount()));
        accountRepository.save(receiverAccount);

        return TransferResponse.builder().toAccountNumber(transferRequest.getToAccountNumber())
                .fromAccountNumber(transferRequest.getFromAccountNumber()).message("Successfully transfer").build();

    }
}
