package rs.ac.uns.ftn.informatika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.model.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
