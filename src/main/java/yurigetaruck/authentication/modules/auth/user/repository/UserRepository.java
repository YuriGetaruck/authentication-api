package yurigetaruck.authentication.modules.auth.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yurigetaruck.authentication.modules.auth.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
