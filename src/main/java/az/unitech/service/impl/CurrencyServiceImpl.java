package az.unitech.service.impl;

import az.unitech.client.CurrencyApiClient;
import az.unitech.dto.response.CurrencyResp;
import az.unitech.service.CurrencyService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {


    private final CurrencyApiClient currencyApiClient;

    @Value("${feign.client.config.currency-api.apiKey}")
    private String apiKey;


    @Override
    public CurrencyResp getCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
        log.info("key: {}", apiKey);
        log.info("fromCurrency : {} and toCurrency : {}", fromCurrency,
                toCurrency);
        var currencyResponse = currencyApiClient.getCurrency(apiKey, fromCurrency,
                toCurrency);
        return CurrencyResp.builder().amount(currencyResponse.getResult().get(toCurrency).multiply(amount))
                .build();

    }

}
