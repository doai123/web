 package appsuckhoe.nghiemtuc.sercurity;

import appsuckhoe.nghiemtuc.service.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SercurityConfig {
    @Autowired
    private KhachHangService khachHangService;

@Bean
    public UserDetailsService userDetailsService(){
    return khachHangService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(khachHangService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpForm ->{
                    httpForm.loginPage("/req/login").permitAll();
                    httpForm.defaultSuccessUrl("/http://localhost:5173");

                })


                .authorizeRequests()
                .requestMatchers("/req/signup", "/css/**", "/js/**").permitAll() // Cho phép đăng ký, CSS, JS không cần xác thực
                .requestMatchers("/SanPham/**").permitAll()
                .requestMatchers("/SanPham/image/**").permitAll()
                .anyRequest().authenticated()  // Yêu cầu xác thực cho các yêu cầu khác
                .and()
                .httpBasic(Customizer.withDefaults())  // Bật Basic Authentication cho API
                .build();
    }
}
