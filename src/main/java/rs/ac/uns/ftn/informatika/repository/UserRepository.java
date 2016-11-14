package rs.ac.uns.ftn.informatika.repository;

import org.springframework.data.repository.CrudRepository;
import rs.ac.uns.ftn.informatika.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
