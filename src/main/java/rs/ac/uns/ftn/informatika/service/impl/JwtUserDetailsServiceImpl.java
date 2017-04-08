package rs.ac.uns.ftn.informatika.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.informatika.model.security.Account;
import rs.ac.uns.ftn.informatika.model.security.JwtUserFactory;
import rs.ac.uns.ftn.informatika.repository.AccountRepository;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	private AccountRepository accountRepository;
	
	@Autowired
	public JwtUserDetailsServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		
		if (account == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return JwtUserFactory.create(account);
		}
	}

}
