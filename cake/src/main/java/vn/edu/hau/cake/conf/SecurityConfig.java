package vn.edu.hau.cake.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf->csrf.disable())
        .authorizeRequests(auth ->
            auth
                .requestMatchers(new AntPathRequestMatcher("/*")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority("ADMIN")
                .anyRequest().authenticated())
                .formLogin(login->login.loginPage("/login").loginProcessingUrl("/login").
                usernameParameter("username").passwordParameter("password").
                defaultSuccessUrl("/admin/productlist",true));
    return http.build();
  }
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(new AntPathRequestMatcher("/static/**"))
        .requestMatchers(new AntPathRequestMatcher("/css/**"))
        .requestMatchers(new AntPathRequestMatcher("/img/**"))
        .requestMatchers(new AntPathRequestMatcher("/js/**"))
        .requestMatchers(new AntPathRequestMatcher("/uploads/**"))
        .requestMatchers(new AntPathRequestMatcher("/cart/**"));
  }

}


