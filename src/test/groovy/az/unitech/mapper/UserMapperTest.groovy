package az.unitech.mapper

import az.unitech.mock.MockDataGroovy
import spock.lang.Specification

class UserMapperTest extends Specification {

    private UserMapper userMapper

    void setup() {

        userMapper = UserMapper.INSTANCE
    }

    def "MapRequestToEntity"() {

        given:
        def userRegisterDto = MockDataGroovy.userRegisterDto();

        when:
        def response = userMapper.mapRequestToEntity(userRegisterDto)

        then:
        response.getUserId() == userRegisterDto.getUserId()
        response.getName() == userRegisterDto.getName()
        response.getSurname() == userRegisterDto.getSurname()
        response.getPassword() == userRegisterDto.getPassword()
        response.getPin() == userRegisterDto.getPin()
    }
}
