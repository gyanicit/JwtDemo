package in.icitinstitute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.icitinstitute.config.JwtTokenUtils;
import in.icitinstitute.to.JwtRequest;
import in.icitinstitute.to.JwtResponse;

@RestController
public class JwtController {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping("/token")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("User Disabled",e);
		}catch (BadCredentialsException e) {
			throw new Exception("Invalid Credential",e);
		}
		
		final UserDetails userDetails=userDetailsService.loadUserByUsername(jwtRequest.getUserName());
		final String token=jwtTokenUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
}
