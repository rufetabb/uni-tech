package az.unitech.service.impl

import az.unitech.dao.repository.UserRepository
import az.unitech.exception.AccountAlreadyExistsException
import az.unitech.mapper.UserMapper
import az.unitech.mock.MockDataGroovy
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class UserServiceImplTest extends Specification {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserServiceImpl userService;

    void setup() {
        userRepository = Mock()
        userMapper = Mock()
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, userMapper, bCryptPasswordEncoder)
    }

    def "UserRegister"() {
        when:
        userService.userRegister(MockDataGroovy.userRegisterDto())

        then:
        1 * userRepository.findByPin(_) >> Optional.of(MockDataGroovy.userDetailsEntity())
        0 * userMapper.mapRequestToEntity(_) >> MockDataGroovy.userDetailsEntity()

        def exception = thrown(AccountAlreadyExistsException)
        exception.message == "account already exists"
    }

    def "UserRegister2"() {
        when:
        userService.userRegister(MockDataGroovy.userRegisterDto())

        then:
        1 * userRepository.findByPin(_) >> Optional.empty()
        1 * userMapper.mapRequestToEntity(_) >> MockDataGroovy.userDetailsEntity()
    }

    def "LoadUserByUsername"() {


        when:
        def response = userService.loadUserByUsername(MockDataGroovy.userRegisterDto().getPin())

        then:
        1 * userRepository.findByPin(_) >> Optional.of(MockDataGroovy.userDetailsEntity())
        response.getUsername() == MockDataGroovy.userDetailsEntity().getPin()
        response.getPassword() == MockDataGroovy.userDetailsEntity().getPassword()

    }

    def "LoadUserByUsernameForException"() {


        when:
        userService.loadUserByUsername(MockDataGroovy.userRegisterDto().getPin())

        then:
        1 * userRepository.findByPin(_) >> Optional.empty()

        def exception = thrown(UsernameNotFoundException)
        exception.message == "user not found"
    }
}
