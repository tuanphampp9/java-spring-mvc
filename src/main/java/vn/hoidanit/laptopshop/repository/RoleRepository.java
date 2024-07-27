package vn.hoidanit.laptopshop.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.hoidanit.laptopshop.domain.Role;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
