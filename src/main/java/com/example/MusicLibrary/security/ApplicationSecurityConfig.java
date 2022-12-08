package com.example.MusicLibrary.security;

import com.example.MusicLibrary.jwt.JwtConfig;
import com.example.MusicLibrary.jwt.JwtTokenVerifier;
import com.example.MusicLibrary.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, SecretKey secretKey, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImp();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Configuration for SQL Database
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Config for Http Security
                .cors().and().csrf().disable()// Only enable this when the application is used in web browsers
                //Stateless functionality of Jason Web Token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //Filters for Jason Web Token
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey)).addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class).authorizeRequests()
                //Role based authentication for User
                .antMatchers(HttpMethod.GET, "/api/v1/music/user").hasAnyRole("ADMIN").antMatchers(HttpMethod.POST, "/api/v1/music/user").hasAnyRole("ADMIN").antMatchers(HttpMethod.PUT, "/api/v1/music/user").hasAnyRole("ADMIN").antMatchers(HttpMethod.DELETE, "/api/v1/music/user").hasAnyRole("ADMIN")
                // Only user that is logged in can see its username and password
                .antMatchers(HttpMethod.GET, "/api/v1/music/user/{user_id}").access("@userSecurity.hasUserId(authentication,#user_id)")
                //Role based authentication for Music
                .antMatchers(HttpMethod.GET, "/api/v1/music/all").hasAnyRole("ADMIN", "USER").antMatchers(HttpMethod.GET, "/api/v1/music/filename/**").hasAnyRole("ADMIN", "USER").antMatchers(HttpMethod.GET, "/api/v1/music/id/**").hasAnyRole("ADMIN", "USER").antMatchers(HttpMethod.POST, "/api/v1/music/upload").hasAnyRole("ADMIN")
                //Role based authentication for Favourite Song
                .antMatchers(HttpMethod.GET, "/api/v1/music/favourite/all").hasAnyRole("ADMIN", "USER").antMatchers(HttpMethod.GET, "/api/v1/music/favourite/filename/**").hasAnyRole("ADMIN", "USER").antMatchers(HttpMethod.DELETE, "/api/v1/music/favourite/id/**").hasAnyRole("ADMIN", "USER").antMatchers(HttpMethod.POST, "/api/v1/music/favourite/add/**").hasAnyRole("ADMIN", "USER");
        //super.configure(http);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}