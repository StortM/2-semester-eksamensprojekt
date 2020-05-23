package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // instans af datasource fra database kan wires ind i denne, hvorefter den kan anvendes til konfiguration (auth.jdbcAuthentication();)
    @Autowired
    DataSource dataSource;

    //extended fra WebSecurityConfigurerAdapter
    //Lader os konfigurere authentication fra JDBC
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username,password,enabled " +
                        "FROM users " +
                        "WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username,authority " +
                        "FROM authorities " +
                        "WHERE username = ?");
    }

    //autoriserer alle requests ved antMatchers, admin page til ADMIN rolle osv. osv.
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests()
                //Selvom authorities er defineret some ROLE_**** og det ikke er nedenfor, så er dette stadigvæk korrekt, da Spring tilføjer
                //"ROLE_" som et prefix, når man specificerer roller i configure
                // se https://stackoverflow.com/questions/33205236/spring-security-added-prefix-role-to-all-roles-name
                .antMatchers("/**").permitAll()
                .antMatchers("/booking/**").permitAll().and()
                /*
                .antMatchers("/").hasAnyRole("SALG, ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/autocamper/**").hasAnyRole("SALG, ADMIN")
                .antMatchers("/booking/**").hasAnyRole("SALG, ADMIN")
                .antMatchers("/kunder/**").hasAnyRole("SALG, ADMIN")
                .antMatchers("/login").permitAll().and()

                 */
            .formLogin()
                .loginPage("/login")
                .permitAll();
    }

    //Siden Spring 5, er det nødvendigt at anvende en passwordencoder.
    // Denne er kun til testing og gør ingenting, men får blot programmet til at køre
    // Så kommer vu udenom hashing og sådan :)
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}