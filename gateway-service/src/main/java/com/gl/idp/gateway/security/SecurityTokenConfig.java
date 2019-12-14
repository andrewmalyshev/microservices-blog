package com.gl.idp.gateway.security;

import com.gl.idp.gateway.common.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity   // Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtConfig jwtConfig;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				// make sure we use stateless session; session won't be used to store user's state.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				// handle an authorized attempts
				.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and()
				// Add a filter to validate the tokens with every request
				.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
				// authorization requests config
				.authorizeRequests()
				// allow all who are accessing "auth" service
				.antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
				// must be an admin if trying to access admin area (authentication is also required here)
				.antMatchers("/authenticate", "/users/add", "/users/is-unique-email").permitAll()
				.antMatchers("/blog/add", "/comment/add", "/likesunlikes/add").hasRole("USER")
				.antMatchers("/change-password").hasRole("USER")
				.antMatchers("/users/admin/add", "/users/list",
						"/users/change-approval", "/blog/change-approval").hasRole("ADMIN")
				.antMatchers("/blog/delete", "/blog/list_all").authenticated()
				// Any other request must be authenticated
				.anyRequest().authenticated();
	}

	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}

}
