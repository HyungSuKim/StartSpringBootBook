package org.zerock.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	DataSource dataSource;
	 
	
	@Autowired
	ZerockUsersService zerockUsersService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		log.info("security config....");

		http.authorizeRequests().antMatchers("/guest/**").permitAll();

		http.authorizeRequests().antMatchers("/manager/**").hasRole("Manager");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("Admin");

		http.formLogin().loginPage("/login");

		http.exceptionHandling().accessDeniedPage("/accessDenied");

		http.logout().logoutUrl("/logout").invalidateHttpSession(true);
		
		//http.userDetailsService(zerockUsersService);
		
		http.rememberMe()
		.key("zerock")
		.userDetailsService(zerockUsersService)
		.tokenRepository(getJDBCRepository())
		.tokenValiditySeconds(60*60*24);
	}
	
	private PersistentTokenRepository getJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		log.info("build Auth global.......");
		
		auth.userDetailsService(zerockUsersService).passwordEncoder(passwordEncoder());
	}

	/*
	 * @Autowired public void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * 
	 * log.info("build Auth global.......");
	 * 
	 * 
	 * auth.inMemoryAuthentication() .withUser("manager") .password("{noop}1111")
	 * .roles("Manager");
	 * 
	 * 
	 * String query1 =
	 * "SELECT uid username, CONCAT('{noop}', upw) password, true enabled" +
	 * " FROM tbl_members WHERE uid=?";
	 * 
	 * String query2 = "SELECT member uid, role_name role" +
	 * " FROM tbl_member_roles WHERE member=?";
	 * 
	 * auth.jdbcAuthentication() .dataSource(dataSource)
	 * .usersByUsernameQuery(query1) .rolePrefix("ROLE_")
	 * .authoritiesByUsernameQuery(query2);
	 * 
	 * }
	 */
}
