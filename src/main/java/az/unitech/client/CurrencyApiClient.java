package az.unitech.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "currency-api", url = "${feign.client.config.currency-api.url}")
public interface CurrencyApiClient {

    @GetMapping(value = "/fetch-one")
    CurrencyResponse getCurrency(@RequestParam("api_key") String apiKey,
                                 @RequestParam("from") String fromCurrency,
                                 @RequestParam("to") String toCurrency);

}
