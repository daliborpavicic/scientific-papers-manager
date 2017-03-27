package rs.ac.uns.ftn.informatika.model.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Account account) {
    	// TODO: Extend account properties
        return new JwtUser(
                account.getId(),
                account.getUsername(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getPassword(),
                mapToGrantedAuthorities(account.getAuthorities()),
                account.isEnabled(),
                account.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
