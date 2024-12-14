package backend.security;

import backend.domain.KhachHang;
import backend.repository.KhachHangRepository;
import backend.util.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private Jwt jwt;
    @Autowired
    private KhachHangRepository khachHangRepository;
    private UserDetails userDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtString = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtString = authorizationHeader.substring(7);
            try {
                username = jwt.extractUsername(jwtString);
                role = jwt.extractRole(jwtString); // Lấy role từ JWT
            } catch (Exception e) {
                // Xử lý lỗi khi JWT không hợp lệ hoặc lỗi trong quá trình giải mã
                System.out.println("Invalid JWT: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<KhachHang> khachHang = khachHangRepository.findByTen(username);
            if (khachHang.isPresent()) {
                // Kiểm tra tính hợp lệ của token
                if (jwt.validateToken(jwtString, khachHang.get().getTen(), khachHang.get().getRoles())) {
                    // Tạo đối tượng Authentication từ người dùng và quyền của họ
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    // Đặt Authentication vào SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
