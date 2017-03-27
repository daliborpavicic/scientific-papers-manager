package rs.ac.uns.ftn.informatika;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import rs.ac.uns.ftn.informatika.model.security.Account;
import rs.ac.uns.ftn.informatika.model.security.JwtUser;
import rs.ac.uns.ftn.informatika.model.security.JwtUserFactory;
import rs.ac.uns.ftn.informatika.utils.JwtTokenUtil;

public class JwtTokenUtilTests {
	
	private static final String TEST_USER_NAME = "test";
	
	// TODO: Fix date so test are not depend on time
	private static final Date TEST_DATE_CREATED = DateTime.now().toDate();

	@Test
	public void generateToken_givenClaims_shouldGenerateToken() {
		Map<String, Object> testClaims = generateTestClaims();
		
		String token = JwtTokenUtil.generateToken(testClaims);
		
		Claims claims = JwtTokenUtil.getClaimsFromToken(token);

		assertThat(claims.get(JwtTokenUtil.CLAIM_KEY_USERNAME), is(TEST_USER_NAME));
		assertThat(claims.get(JwtTokenUtil.CLAIM_KEY_CREATED), is(TEST_DATE_CREATED.getTime()));
	}
	
	@Test
	public void generateToken_givenUserDetails_shouldGenerateToken() {
		Account mockAccount = createMockAccount();
		
		JwtUser jwtUser = JwtUserFactory.create(mockAccount);
		
		String token = JwtTokenUtil.generateToken(jwtUser);
		
		assertThat(JwtTokenUtil.getUsernameFromToken(token), is(TEST_USER_NAME));
	}

	
	@Test
	public void getUsernameFromToken_givenToken_shouldReturnUsername() {
		Map<String, Object> testClaims = generateTestClaims();
		String token = JwtTokenUtil.generateToken(testClaims);
		
		assertThat(JwtTokenUtil.getUsernameFromToken(token), is(TEST_USER_NAME));
	}
	
	@Test
	public void isValidToken_givenValidToken_shouldValidate() {
		UserDetails mockUserDetails = createMockUserDetails();
		String token = JwtTokenUtil.generateToken(mockUserDetails);
		
		assertThat(JwtTokenUtil.isValidToken(token, mockUserDetails), is(true));
	}
	
	private Map<String, Object> generateTestClaims() {
		Map<String, Object> testClaims = new HashMap<>();
		
		testClaims.put(JwtTokenUtil.CLAIM_KEY_USERNAME, TEST_USER_NAME);
		testClaims.put(JwtTokenUtil.CLAIM_KEY_CREATED, TEST_DATE_CREATED);
		
		return testClaims;
	}

	private UserDetails createMockUserDetails() {
		JwtUser jwtUser = JwtUserFactory.create(createMockAccount());
		
		return jwtUser;
	}

	private Account createMockAccount() {
		Account mockAccount = new Account();
		mockAccount.setUsername(TEST_USER_NAME);
		mockAccount.setAuthorities(new ArrayList<>());
		
		return mockAccount;
	}
	
}
