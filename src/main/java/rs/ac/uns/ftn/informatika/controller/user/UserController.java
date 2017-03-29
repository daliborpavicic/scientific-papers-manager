package rs.ac.uns.ftn.informatika.controller.user;

import static rs.ac.uns.ftn.informatika.controller.HomeController.BASE_API_URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.informatika.model.security.JwtUser;
import rs.ac.uns.ftn.informatika.utils.JwtTokenUtil;

@RestController
@RequestMapping(BASE_API_URL + "/user")
public class UserController {
	
	private UserDetailsService userDetailsService;
	
	@Autowired
	public UserController(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public JwtUser getAuthenticatedUser(HttpServletRequest request) {
		String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
		String token = JwtTokenUtil.getTokenFromHttpHeader(tokenHeader);
		String usernameFromToken = JwtTokenUtil.getUsernameFromToken(token);
		
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(usernameFromToken);
		
		return user;
	}
}
