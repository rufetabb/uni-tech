package az.unitech.mapper

import az.unitech.mock.MockDataGroovy
import spock.lang.Specification

class AccountMapperTest extends Specification {

    private AccountMapper accountMapper

    void setup() {
        accountMapper = AccountMapper.INSTANCE
    }


    def "MapEntityToRequest"() {
        given:
        def account = MockDataGroovy.accountDetails();

        when:
        def response = accountMapper.mapEntityToRequest(List.of(account))

        then:
        response.get(0).getUserId() == account.getUserId()
        response.get(0).getAccountNumber() == account.getAccountNumber()
        response.get(0).getAmount() == account.getAmount()

    }
}
