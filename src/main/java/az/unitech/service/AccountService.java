package az.unitech.service;

import az.unitech.dto.response.AccountDto;
import az.unitech.dto.response.TransferResponse;
import az.unitech.dto.request.TransferRequest;
import java.util.List;

public interface AccountService {


    List<AccountDto> getActiveAccountByUserId(String userId);

    TransferResponse amountTransfer(TransferRequest transferRequest);
}
