package rs.ac.uns.ftn.informatika.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.model.security.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findByUsername(String username);
}
