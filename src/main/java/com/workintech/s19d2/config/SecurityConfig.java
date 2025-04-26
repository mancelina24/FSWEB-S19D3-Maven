package com.workintech.s19d2.config;





import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



//Authentication(Sisteme Login olmak, Giriş yapma) ve Authorization(Yetkilendirme-Rol based demek)

@Configuration
public class SecurityConfig {

    //Passwordun nasıl encoder olacağını belirliyoruz.
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //Bu şekilde artık Authenticationı database üzerinden yönetiyorum demek.
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService)
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    //Nasıl security yönettiğimiz yer oluyor burası.
    //Hangi kullanıcının hangi requesti yapabileceğinin iznini ayarlıyoruz.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/account").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET,"/account").hasAuthority("USER");
                    auth.requestMatchers(HttpMethod.GET,"/account/{id}").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.GET,"/account/{id}").hasAuthority("USER");
                    auth.requestMatchers(HttpMethod.POST,"/account").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT,"/account/{id}").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE,"/account/{id}").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                }) //Formlogin default ayarlarda bırakıyoruz.
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
