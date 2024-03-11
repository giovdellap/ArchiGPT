package it.yourstore.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/ordine/**", "/order-item/**","/product/**","/utente/**", "/error", "/user/**", "/logout/**", "/h2-console/**").permitAll()
         .anyRequest().authenticated()
         .and()
         .oauth2Login()
         .defaultSuccessUrl("/loginSuccess")
         .failureUrl("/loginFailure")
         .and()
         .logout(l -> l.logoutSuccessUrl("/").permitAll())
         ;
//        .anyRequest().permitAll();
        return http.build();
    }
}
