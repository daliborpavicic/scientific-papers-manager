package rs.ac.uns.ftn.informatika.controller;

import static rs.ac.uns.ftn.informatika.controller.HomeController.BASE_API_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.dto.AuthenticationRequest;
import rs.ac.uns.ftn.informatika.dto.JwtAuthenticationResponse;
import rs.ac.uns.ftn.informatika.utils.JwtTokenUtil;

@RestController
@RequestMapping(BASE_API_URL + "/authentication")
public class AuthenticationController {
	
	private UserDetailsService userDetailsService;
	
	private AuthenticationManager authenticationManager;

	@Autowired
	public AuthenticationController(UserDetailsService userDetailsService,
			AuthenticationManager authenticationManager) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.username,
						authenticationRequest.password
						)
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username);
		String token = JwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
	
}
