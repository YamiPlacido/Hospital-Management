package com.hospital.config;

import com.hospital.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
    UserDetailsServiceImpl userDetailsService;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
 
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
 
        http.csrf().disable()
 
        // The pages does not require login
        .authorizeRequests()
        .antMatchers("/admin/login", "/admin/logout").permitAll()
        .antMatchers("/admin/**").hasAnyAuthority("USER","RECEPTIONIST")
        // /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
     // If no login, it will redirect to /login page.
//        http.authorizeRequests().antMatchers("/admin/userInfo").access("hasAnyRole('USER', 'NONE')");
        .antMatchers("/admin/userInfo*").hasAnyAuthority("USER")
        // For ADMIN only.
//        http.authorizeRequests().antMatchers("/admin/adminPage").access("hasRole('ADMIN')");
        .antMatchers("/admin/IllnessType*").hasAuthority("ADMIN")
        .antMatchers("/admin/patient/*").hasAnyAuthority("RECEPTIONIST")
        .antMatchers("/admin/appointment/*").hasAuthority("RECEPTIONIST")
        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        .and().exceptionHandling().accessDeniedPage("/admin/403");
 
        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/admin/login")//
                .defaultSuccessUrl("/admin/index")//
                .failureUrl("/admin/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and()
                .logout().permitAll()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login?logout")
                .clearAuthentication(true).invalidateHttpSession(true);
    }	
}
