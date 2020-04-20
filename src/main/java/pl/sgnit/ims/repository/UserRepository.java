package pl.sgnit.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sgnit.ims.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    User findByUserNameAndEnabledIsTrue(String userName);

    User findByUserNameAndIdNot(String userName, Long id);

    User findByEmail(String email);

    User findByEmailAndIdNot(String email, Long id);
}
