package az.unitech.service.impl

import az.unitech.client.CurrencyApiClient
import az.unitech.mock.MockDataGroovy
import spock.lang.Specification

class CurrencyServiceImplTest extends Specification {


    private CurrencyServiceImpl currencyService;
    private CurrencyApiClient currencyApiClient;

    void setup() {
        currencyApiClient = Mock()
        currencyService = new CurrencyServiceImpl(currencyApiClient)
    }

    def "GetCurrency"() {


        when:
        currencyService.getCurrency("USD", "AZN", 10);

        then:
        1 * currencyApiClient.getCurrency(_, _, _) >> MockDataGroovy.currencyResponse();

    }
}
