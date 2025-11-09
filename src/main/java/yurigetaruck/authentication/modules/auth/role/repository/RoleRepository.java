package yurigetaruck.authentication.modules.auth.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yurigetaruck.authentication.modules.auth.role.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
    Optional<Role> findByCode(String code);

    Set<Role> findByCodeIn(List<String> names);
}
