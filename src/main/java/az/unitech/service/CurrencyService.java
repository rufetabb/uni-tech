package az.unitech.service;

import az.unitech.client.CurrencyResponse;
import az.unitech.dto.request.CurrencyRequest;
import az.unitech.dto.response.CurrencyResp;
import java.math.BigDecimal;


public interface CurrencyService {


    CurrencyResp getCurrency(String fromCurrency,String toCurrency, BigDecimal amount);

}
