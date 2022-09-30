package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import tacos.data.UserRepository;
import tacos.entity.User;

@Configuration
public class SecurityConfig{
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Autowired
    public UserDetailsService userDetailsService(UserRepository userRepository){
        
        return username -> {
            User user = userRepository.findByUsername(username);

            if(user != null) return user;

            throw new UsernameNotFoundException("User with this username not found!!!");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        
        return http
                .authorizeRequests()
                .antMatchers("/design").hasRole("USER")
                .antMatchers("/orders/**").hasRole("USER")
                .antMatchers("/**").permitAll()
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design")
            .and()
                .logout()
                .logoutSuccessUrl("/")
            // .and()
            //     .authorizeRequests()
            //     .antMatchers(HttpMethod.POST, "/api/ingredients")
            //     .hasAuthority("SCOPE_writeIngredients")
            //     .antMatchers(HttpMethod.DELETE, "/api/ingredients")
            //     .hasAuthority("SCOPE_deleteIngredients")
            //     .antMatchers("/**").access("permitAll")
            // .and()
            //     .oauth2ResourceServer(oauth2 -> oauth2.jwt())
            //     .logout()
            //     .logoutSuccessUrl("/")
            .and()
                .csrf()
                    .ignoringAntMatchers("/h2-console/**", "/api/**")
            .and()
                .headers()
                .frameOptions()
                .sameOrigin()
            .and()
                .build();
    }
}
