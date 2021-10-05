package com.spring.security.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
	private JwtAuthenticationTokenFilter jwtauthFilter;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
    public PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
        http
        	.csrf().disable()
        	.antMatcher("/**").authorizeRequests()
		    .antMatchers("/resources/**").permitAll()
		    .antMatchers("/api/authenticate").permitAll()
        	.antMatchers("/api/**").authenticated()
		    .antMatchers("/**").authenticated()
	    .and()
	    	.httpBasic()
	    .and()
    		.addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);
        
        http.sessionManagement().maximumSessions(1).expiredUrl("/login?expired=true");
		System.out.println("Test");
		System.out.println("Test4");
    }
	
	@Autowired
	public void configureInMemoryAuthentication(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
		auth.inMemoryAuthentication().withUser("apiuser").password(passwordEncoder.encode("apiuser")).roles("APIUSER");
	}
}
