package az.unitech.controller;


import az.unitech.dto.response.AccountDto;
import az.unitech.dto.response.TransferResponse;
import az.unitech.dto.request.TransferRequest;
import az.unitech.service.AccountService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;

    @GetMapping("/all/user")
    public ResponseEntity<List<AccountDto>> getAllActiveAccounts(@RequestHeader("X-USER-ID") String userId) {
        return ResponseEntity.ok(accountService.getActiveAccountByUserId(userId));
    }

    @PostMapping("/transfer/amount")
    public ResponseEntity<TransferResponse> amountTransfer(@Valid @RequestBody TransferRequest transferRequest) {
        return ResponseEntity.ok(accountService.amountTransfer(transferRequest));
    }
}
