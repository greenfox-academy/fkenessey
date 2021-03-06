package com.greenfoxacademy.todowithwebsecurity.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

 /*@Autowired
  public void configureInMemory(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
            .withUser("admin").password("admin").roles("USER");
  }*/

  @Autowired
  private DataSource dataSource;

  /*@Value("${spring.queries.users-query}")
  private String usersQuery;

  @Value("${spring.queries.roles-query}")
  private String rolesQuery;*/

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          //.antMatchers("/**").permitAll()
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/success")
          .failureUrl("/google.com")
          //.usernameParameter("username").passwordParameter("password")
          .permitAll()
          .and()
        .logout()
          .deleteCookies()
          .logoutSuccessUrl("/");
  }

  @Autowired
  public void configureMySql(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
            /*.usersByUsernameQuery(usersQuery)
            .authoritiesByUsernameQuery(rolesQuery);*/
            .usersByUsernameQuery("select username, password, role from users where username=?");
  }
}
