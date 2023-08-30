package az.unitech.controller;

import az.unitech.dto.response.CurrencyResp;
import az.unitech.service.CurrencyService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class CurrencyController {


    private final CurrencyService currencyService;

    @GetMapping("/currency")
    public ResponseEntity<CurrencyResp> getCurrency(@RequestParam("from") String fromCurrency,
                                                    @RequestParam("to") String toCurrency,
                                                    @RequestParam BigDecimal ammount) {

        return ResponseEntity.ok(
                currencyService.getCurrency(fromCurrency,toCurrency,ammount));
    }
}
