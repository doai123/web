package backend.sercurity;

import backend.service.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SercurityConfig {
    private final KhachHangService khachHangService;

    @Bean
    public UserDetailsService userDetailsService() {
        return khachHangService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(khachHangService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests()
                .requestMatchers("/req/login", "/req/signup", "/css/**", "/js/**").permitAll()  // Cho phép đăng ký, CSS, JS không cần xác thực
                .requestMatchers("/SanPham/**", "/SanPham/image/**").permitAll()  // Các trang sản phẩm không cần xác thực
                .requestMatchers(HttpMethod.DELETE, "/SanPham/**").hasRole("ADMIN").
                 requestMatchers(HttpMethod.PUT, "/SanPham/**").hasRole("ADMIN").
                 requestMatchers(HttpMethod.POST, "/SanPham/**").hasRole("ADMIN").
                 requestMatchers(HttpMethod.DELETE, "/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated()  // Yêu cầu xác thực cho tất cả các trang khác
                .and()
                .formLogin(form -> form
                        .loginPage("/endpoints/req/login")  // Trang login tùy chỉnh
                        .defaultSuccessUrl("/", true)  // Chuyển hướng khi đăng nhập thành công
                        .failureUrl("/endpoints/req/login?error=true")  // Chuyển hướng khi đăng nhập thất bại
                )
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Tạo session nếu cần
                .invalidSessionUrl("/endpoints/req/login")  // URL khi session hết hạn
                .maximumSessions(1).maxSessionsPreventsLogin(true)  // Giới hạn số lần đăng nhập
        )
                .build();
    }
}
