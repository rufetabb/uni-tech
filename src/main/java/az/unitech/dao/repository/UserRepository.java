package az.unitech.dao.repository;

import az.unitech.dao.entity.UserDetailsEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDetailsEntity, Long> {


    Optional<UserDetailsEntity> findByPin(String pin);


}
